package com.note.NoteRecommender.controller;


import com.note.NoteRecommender.dto.DepartmentDto;
import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.entities.Department;
import com.note.NoteRecommender.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@RestController
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;
    //create
    @PostMapping("/user/{userId}/department/create")
    @PreAuthorize("hasAuthority('manage_department')")
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto department, @PathVariable Long userId) throws Exception {
        DepartmentDto createDepartment=this.departmentService.createDepartment(userId,department);
        return new ResponseEntity<>(createDepartment, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/user/{userId}/department/update/{departmentId}")
    @PreAuthorize("hasAuthority('manage_department')")
    public ResponseEntity<DepartmentDto> updateDepartment(@Valid @PathVariable("userId")Long userId,@PathVariable("departmentId")Long departmentId,@RequestBody DepartmentDto department) throws Exception {
        DepartmentDto updateDepartment=this.departmentService.updateDepartment(userId,departmentId,department);
        return new ResponseEntity<>(updateDepartment, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/department/{departmentName}/delete")
    @PreAuthorize("hasAuthority('manage_department')")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable String departmentName){
        this.departmentService.deleteDepartment(departmentName);
        return new ResponseEntity<>(new ApiResponse("Department is deleted successfully",true), HttpStatus.OK);
    }

    //get
    @GetMapping("/department/{departmentName}/read")
    @PreAuthorize("hasAuthority('manage_department')")
    ResponseEntity<?> getDepartmentByName(@PathVariable("departmentName")String departmentName){
        DepartmentDto departmentByName=this.departmentService.getDepartmentByName(departmentName);
        if(departmentByName!=null){
            return new ResponseEntity<>(departmentByName,HttpStatusCode.valueOf(200));
        }
        else {
            return new ResponseEntity<>(new ApiResponse("there is no department with the given name",false),HttpStatusCode.valueOf(500));

        }

    }

    @GetMapping("user/{userId}/department/getAll")
    @PreAuthorize("hasAuthority('manage_department')")
    ResponseEntity<?> getAllDepartment(@PathVariable("userId") Long userId){
        List<DepartmentDto> departmentDtos=this.departmentService.getAllDepartments(userId);
        if(departmentDtos!=null){
            return new ResponseEntity<>(departmentDtos,HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(new ApiResponse("there is no department",false),HttpStatusCode.valueOf(200));
    }



}
