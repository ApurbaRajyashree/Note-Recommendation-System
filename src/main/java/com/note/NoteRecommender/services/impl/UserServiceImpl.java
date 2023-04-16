package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.dto.AuthorityDto;
import com.note.NoteRecommender.dto.RoleDto;
import com.note.NoteRecommender.dto.UserDto;
import com.note.NoteRecommender.entities.*;
import com.note.NoteRecommender.exceptions.ResourceNotFoundException;
import com.note.NoteRecommender.helper.QueryHelper;
import com.note.NoteRecommender.repositories.RoleRepositories;
import com.note.NoteRecommender.repositories.UserRepositories;
import com.note.NoteRecommender.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    public static final String[] ADMIN_ACCESS = {"ROLE_ADMIN", "ROLE_TEACHER", "ROLE_STUDENT"};
   @Autowired
    private UserRepositories userRepo;
    @Autowired
    private RoleRepositories roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QueryHelper queryHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDto createUser(UserDto userDto, Long departmentId) throws Exception {
        if (checkIfUserNameExists(userDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Name already Exists");
        }
        if (checkIfUserEmailExists(userDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User email id already exists");
        }
        Role retrievedRole = null;
        Set<RoleDto> roleDtoSet = userDto.getRoleDtoSet();
        if (!roleDtoSet.isEmpty()) {
            for (RoleDto eachRoleDto : roleDtoSet
            ) {
                retrievedRole = this.roleRepo.findByRoleName(eachRoleDto.getName());
                if (retrievedRole == null) {
                    throw new Exception("Please select the valid role");
                }
            }
        } else {
            throw new Exception("Please select a valid role");
        }

        User user = new User();
        this.modelMapper.map(userDto, user);
        user.setEmail(user.getEmail().toLowerCase());
        user.setUserName(user.getUserName().toLowerCase());

        if (retrievedRole.getRoleName().equals("ROLE_STUDENT")) {
            Department department = this.queryHelper.getDepartmentMethod(departmentId);

            if (departmentId > 0) {
                user.setDepartments(Set.of(department));
                department.getUsers().add(user);
                user.setUserStatus(UserStatus.pending);

            } else {
                throw new Exception("Please select the valid department !!!!");
            }
        } else if (retrievedRole.getRoleName().equals("ROLE_TEACHER")) {
            if (departmentId > 0) {
                Department department = this.queryHelper.getDepartmentMethod(departmentId);
                user.setBatch(null);
                user.setDepartments(Set.of(department));
                department.getUsers().add(user);
                user.setUserStatus(UserStatus.pending);

            } else {
                throw new Exception("Please select the valid department");
            }

        } else if (retrievedRole.getRoleName().equals("ROLE_ADMIN")) {
            if (departmentId <= 0) {
                user.setBatch(null);

                user.setUserStatus(UserStatus.approved);
                user.setIsEnabled(Boolean.TRUE);
            }
        } else {
            throw new Exception("Invalid role!!!!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(retrievedRole);
        user.setUserRoles(roles);
        user = this.userRepo.save(user);
        UserDto userDto1 = this.modelMapper.map(user, UserDto.class);
        userDto1.setUserId(user.getUserId());
        return userDto1;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User retrievedUser = this.userRepo.findByEmail(email);
        if (retrievedUser == null) {
            throw new ResourceNotFoundException("user", "email " + email, null);
        }
        UserDto userDto=this.getUserDto(retrievedUser);
        return userDto;
    }



    @Override
    public List<UserDto> getAllUSer() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos=users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setIsEnabled(user.getIsEnabled());
        userDto.setUserName(user.getUserName());
        userDto.setUserId(user.getUserId());
        userDto.setUserStatus(user.getUserStatus());
        userDto.setBatch(user.getBatch());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());

        Set<RoleDto> rSet = new HashSet<>();
        for (Role r : user.getUserRoles()) {
            RoleDto rDto = new RoleDto();
            rDto.setName(r.getRoleName());
            Set<AuthorityDto> aSet = new HashSet<>();

            for (Authority a : r.getAuthorities()) {
                AuthorityDto aDto = new AuthorityDto(a.getAuthorityName());
                aSet.add(aDto);
            }
            rDto.setAuthoritySet(aSet);
            rSet.add(rDto);
        }
        userDto.setRoleDtoSet(rSet);

        return userDto;
    }


    //deleteUserById
    @Override
    public String deleteUser(Long userId) {
        String message = "";
        User retrievedUser = queryHelper.getUserMethod(userId);
        UserStatus userStatus = retrievedUser.getUserStatus();
        if (userStatus.equals(UserStatus.pending) || userStatus.equals(UserStatus.approved)) {
            retrievedUser.setIsEnabled(Boolean.FALSE);
            Set<Department> departments=retrievedUser.getDepartments();
            if(!departments.isEmpty()){
                for (Department department:departments){
                    department.getUsers().remove(retrievedUser);
                }
            }

            Set<Semester> semesters=retrievedUser.getSemesters();
            if(!semesters.isEmpty()){
                for (Semester semester:semesters){
                    semester.getUsers().remove(retrievedUser);
                }
            }

            Set<Course> courses=retrievedUser.getCourses();
            if(!courses.isEmpty()){
                for (Course course:courses){
                    course.getUsers().remove(retrievedUser);
                }
            }

            this.userRepo.deleteById(retrievedUser.getUserId());
            message = "User deleted successfully";
            return message;
        }
        return message;
    }


    @Override
    public UserDto updateUser(Long userId, User updatedUser) {
        User retrievedUser = queryHelper.getUserMethod(userId);
        UserDto userDto=new UserDto();
        retrievedUser.setPassword(updatedUser.getPassword());
        retrievedUser.setPhoneNumber(updatedUser.getPhoneNumber());
        retrievedUser = this.userRepo.save(retrievedUser);
        userDto.setPassword(retrievedUser.getPassword());
        userDto.setPhoneNumber(retrievedUser.getPhoneNumber());
        return userDto;
    }

    @Override
    public String approveUser(Long userId) {
        User user = this.userRepo.findById(userId).get();
        user.setUserStatus(UserStatus.approved);
        user.setIsEnabled(Boolean.TRUE);
        user = this.userRepo.save(user);
        return "User approved!!!";
    }

    @Override
    public String approveAllUser() {
        this.userRepo.findAll().stream().filter(user -> user.getUserStatus().equals(UserStatus.pending)).forEach(user -> {
            user.setUserStatus(UserStatus.approved);
            user.setIsEnabled(Boolean.TRUE);
            userRepo.save(user);

        });
        return "Approved all user";
    }

    @Override
    public String rejectUser(Long userId) {
        User user = this.userRepo.findById(userId).get();
        user.setUserStatus(UserStatus.rejected);
        userRepo.save(user);
        return "User Rejected!!!";
    }

    @Override
    public String rejectAll() {
        this.userRepo.findAll().stream().filter(user -> user.getUserStatus().equals(UserStatus.pending)).forEach(user -> {
            user.setUserStatus(UserStatus.rejected);
            userRepo.save(user);
        });
        return "Reject All User";
    }

    @Override
    public List<UserDto> viewAllApprovedStudent() {
        List<User> users = userRepo.findAll().stream().filter(user -> {
            user.getUserRoles().stream().filter(role -> role.getRoleName().equals("ROLE_STUDENT"));
            user.getUserStatus().equals(UserStatus.approved);

            List<User> users1 = new ArrayList<>();

            return users1.add(user);

        }).collect(Collectors.toList());

        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public List<UserDto> viewAllApprovedTeacher() {
        List<User> users = userRepo.findAll().stream().filter(user -> {
            user.getUserRoles().stream().filter(role -> role.getRoleName().equals("ROLE_TEACHER"));
            user.getUserStatus().equals(UserStatus.approved);
            List<User> users1 = new ArrayList<>();

            return users1.add(user);

        }).collect(Collectors.toList());

        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        return userDtos;
    }

    public Boolean checkIfUserNameExists(UserDto userDto) {
        return StringUtils.hasText(userDto.getUserName()) && userRepo.findByUserName(userDto.getUserName().toLowerCase()) != null;
    }

    private boolean checkIfUserEmailExists(UserDto userDto) {
        return StringUtils.hasText(userDto.getEmail()) && userRepo.findByEmail(userDto.getEmail().toLowerCase()) != null;
    }
}
