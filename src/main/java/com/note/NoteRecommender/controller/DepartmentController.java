package com.note.NoteRecommender.controller;


import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.entities.Department;
import com.note.NoteRecommender.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatusCode;

@RestController

public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;
    //create
    @PostMapping("/user/{userId}/department/create")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department, @PathVariable Long userId) throws Exception {
        Department createDepartment=this.departmentService.createDepartment(userId,department);
        return new ResponseEntity<>(createDepartment, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/user/{userId}/department/update/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@Valid @PathVariable("userId")Long userId,@PathVariable("departmentId")Long departmentId,@RequestBody Department department){
        Department updateDepartment=this.departmentService.updateDepartment(userId,departmentId,department);
        return new ResponseEntity<>(updateDepartment, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/user/{userId}/department/delete")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Long departmentId){
        this.departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(new ApiResponse("Department is deleted successfully",true), HttpStatus.OK);
    }

    //get
    @GetMapping("/user/{userId}/department/{departmentName}/read")
    ResponseEntity<?> getDepartmentByName(@PathVariable("userId")Long userId,@PathVariable("departmentName")String departmentName){
        Department departmentByName=this.departmentService.getDepartmentByName(userId,departmentName);
        if(departmentByName!=null){
            return new ResponseEntity<>(departmentByName,HttpStatusCode.valueOf(200));
        }
        else {
            return new ResponseEntity<>(new ApiResponse("there is no department with the given name",false),HttpStatusCode.valueOf(500));

        }

    }

}
