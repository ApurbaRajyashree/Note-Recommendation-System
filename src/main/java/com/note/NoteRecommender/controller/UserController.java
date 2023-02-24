package com.note.NoteRecommender.controller;


import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.entities.User;
import com.note.NoteRecommender.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //POST -create User
    @PostMapping("/{departmentId}/{roleId}/create")
    public ResponseEntity<User> createUser(@Valid @PathVariable("departmentId")Long departmentId,@PathVariable("roleId")Long roleId,@RequestBody User user) throws Exception {
        User createuser= this.userService.createUser(user,departmentId,roleId);
        return new ResponseEntity<>(createuser, HttpStatus.CREATED);
    }

    //PUT -update user
    @PutMapping("/{userId}/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user,@PathVariable("userId") Long userId ){
        User updatedUser= this.userService.updateUser(userId,user);
        return ResponseEntity.ok(updatedUser);

    }

    //DELETE
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }

    //GET all
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUSer());
    }

    //Get byId
    @GetMapping("/{userId}/")
    public ResponseEntity<User> getSingleUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    //Get by email

    @GetMapping("/readByEmail/{email}")
    public ResponseEntity<?> getUserByUserEmail(/*@RequestParam("email") String email, */@PathVariable String email){
        User user=this.userService.getUserByEmail(email);
        if(user!=null){
            return new ResponseEntity<User>(user, HttpStatusCode.valueOf(200));
        }else {
            return new ResponseEntity<>(new ApiResponse("user doesnot exist with the given email",false),HttpStatusCode.valueOf(200));
        }
    }


}
