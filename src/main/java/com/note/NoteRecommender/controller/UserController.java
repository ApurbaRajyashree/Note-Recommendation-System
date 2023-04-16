package com.note.NoteRecommender.controller;

import com.note.NoteRecommender.dto.UserDto;
import com.note.NoteRecommender.entities.UserStatus;
import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.entities.User;
import com.note.NoteRecommender.helper.JwtHelper;
import com.note.NoteRecommender.security.CustomUserDetailService;
import com.note.NoteRecommender.security.UserPrincipal;
import com.note.NoteRecommender.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private UserService userService;

    //POST -create User
    @PostMapping(value = "/department/{departmentId}/create", consumes = {"application/json"})
    public ResponseEntity<UserDto> createUser(@Valid @PathVariable("departmentId") Long departmentId, @RequestBody UserDto user) throws Exception {
        UserDto createuser = this.userService.createUser(user, departmentId);
        return new ResponseEntity<>(createuser, HttpStatus.CREATED);
    }

    //PUT -update user
    @PutMapping("/{userId}/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, @PathVariable("userId") Long userId) {
        User updatedUser = this.userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);

    }

    //DELETE
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('delete_user')")
    ResponseEntity<ApiResponse> deleteUser(@RequestParam("userId") Long userId) {
        String message = this.userService.deleteUser(userId);
        if (message.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("something went wrong", false), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(new ApiResponse(message, true), HttpStatusCode.valueOf(200));
    }

    //GET all
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUSer());
    }


    @GetMapping("/{userId}/")
    public ResponseEntity<User> getSingleUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    @GetMapping("/readByEmail")
    public ResponseEntity<?> getUserByUserEmail(@RequestParam("userEmail") String email) {
        UserDto user = this.userService.getUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("user doesnot exist with the given email", false), HttpStatusCode.valueOf(200));
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) throws Exception {
        String userName = userDto.getEmail();
        String password = userDto.getPassword();
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(userName);

        this.authenticate(userName, password, userDetails.getAuthorities());
        UserDto user = this.userService.getUserByEmail(userDto.getEmail());
        if (user.getUserStatus().equals(UserStatus.approved)) {
            user.setPassword(null);
            String jwtToken = this.jwtHelper.generateToken(new UserPrincipal(user));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtToken);
            return ResponseEntity.ok().headers(headers).body(user);
        } else {
            throw new Exception("Invalid userststatus to generate token");
        }
    }

    private void authenticate(String userName, String password, Collection<? extends GrantedAuthority> authorities) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password, authorities);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (DisabledException e) {
            throw new Exception("user is disabled!!!");
        }
    }

    @GetMapping("/approveUser/{userId}")
    @PreAuthorize("hasAuthority('approve_user')")
    public ResponseEntity<ApiResponse> approveUserController(@PathVariable Long userId) {
        String message = this.userService.approveUser(userId);
        return new ResponseEntity<>(new ApiResponse(message, true), HttpStatusCode.valueOf(200));

    }

    @GetMapping("/approveAll")
    @PreAuthorize("hasAuthority('approve_user')")
    public ResponseEntity<ApiResponse> approveAllUserController() {
        String message = this.userService.approveAllUser();
        return new ResponseEntity<>(new ApiResponse(message, true), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/rejectUser/{userId}")
    @PreAuthorize("hasAuthority('reject_user')")
    public ResponseEntity<ApiResponse> rejectUserController(@PathVariable Long userId) {
        String message = this.userService.rejectUser(userId);
        return new ResponseEntity<>(new ApiResponse(message, true), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/rejectAll")
    @PreAuthorize("hasAuthority('reject_user')")
    public ResponseEntity<ApiResponse> rejectAllUserController() {
        String message = this.userService.rejectAll();
        return new ResponseEntity<>(new ApiResponse(message, true), HttpStatusCode.valueOf(200));

    }

    @GetMapping("/view-all-approved-student")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> viewAllApprovedStudent() {
        List<UserDto> allApprovedStudent = this.userService.viewAllApprovedStudent();
        return new ResponseEntity<>(allApprovedStudent, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/view-all-approved-teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> viewAllApprovedTeacher() {
        List<UserDto> allApprovedTeacher = this.userService.viewAllApprovedTeacher();
        return new ResponseEntity<>(allApprovedTeacher, HttpStatusCode.valueOf(200));
    }


}
