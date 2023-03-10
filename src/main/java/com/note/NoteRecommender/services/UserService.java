package com.note.NoteRecommender.services;

import com.note.NoteRecommender.dto.UserDto;
import com.note.NoteRecommender.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    UserDto  createUser(UserDto  userDto,Long departmentId) throws Exception;

    UserDto getUserByEmail(String email);

    List<User> getAllUSer();

    UserDto getUserDto(User user);

    String deleteUser(Long userId);

    public User getUserById(Long userId);

    User updateUser(Long userId,User updatedUser);

    String approveUser(Long userId);

    String approveAllUser();


    String rejectUser(Long userId);

    String rejectAll();


    List<UserDto> viewAllApprovedStudent();

    List<UserDto> viewAllApprovedTeacher();
}
