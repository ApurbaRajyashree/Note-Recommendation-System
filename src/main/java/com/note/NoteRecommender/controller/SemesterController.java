package com.note.NoteRecommender.controller;


import com.note.NoteRecommender.dto.SemesterDto;
import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.entities.Semester;
import com.note.NoteRecommender.services.SemesterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @PostMapping("/user/{userId}/department/{departmentId}/semester/create")
    @PreAuthorize("hasAuthority('manage_semester')")
    ResponseEntity<?> createSemesterController(@Valid  @PathVariable("userId") Long userId, @PathVariable("departmentId") Long departmentId, @RequestBody SemesterDto semester) throws Exception {
        SemesterDto createdSemester = this.semesterService.createSemester(userId, departmentId, semester);
        if (createdSemester != null) {
            return new ResponseEntity<>(createdSemester, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));
        }
    }


    @GetMapping("/user/{userId}/department/{departmentId}/semester/read")
    @PreAuthorize("hasAuthority('manage_semester')")
    public ResponseEntity<?> readSemesterByDepartment(@PathVariable("userId") Long userId, @PathVariable("departmentId") Long departmentId) throws Exception {
        List<SemesterDto> semesters = this.semesterService.readSemesterByDepartment(userId, departmentId);
        if (semesters != null) {
            return new ResponseEntity<>(semesters, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));

        }

    }

    @GetMapping("/user/{userId}/department/{departmentId}/semester/{semesterId}/readById")
    @PreAuthorize("hasAuthority('manage_semester')")
    ResponseEntity<?> readSemesterById(@PathVariable("userId") Long userId, @PathVariable("departmentId") Long departmentId, @PathVariable("semesterId") Long semesterId) throws Exception {
        SemesterDto resultSemester = this.semesterService.readSemesterById(userId, departmentId, semesterId);
        if (resultSemester != null) {
            return new ResponseEntity<>(resultSemester, HttpStatusCode.valueOf(200));

        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));

        }
    }

    @DeleteMapping("/user/{userId}/department/{departmentId}/semester/{semesterId}/delete")
    @PreAuthorize("hasAuthority('manage_semester')")
    ResponseEntity<ApiResponse> deleteSemester(@PathVariable("userId") Long userId, @PathVariable("departmentId") Long departmentId, @PathVariable("semesterId") Long semesterId) throws Exception {
        String message=this.semesterService.deleteSemester(userId,departmentId,semesterId);
        if(message.contains("successfully")){
            return new ResponseEntity<>(new ApiResponse(message,true),HttpStatusCode.valueOf(200));
        }else {
            return new ResponseEntity<>(new ApiResponse(message,false),HttpStatusCode.valueOf(200));
        }

    }

    @PutMapping("/user/{userId}/department/{departmentId}/semester/{semesterId}/update")
    @PreAuthorize("hasAuthority('manage_semester')")
    ResponseEntity<?> updateSemester (@PathVariable("userId") Long userId, @PathVariable("departmentId") Long departmentId, @PathVariable("semesterId") Long semesterId,@RequestBody SemesterDto semesterDto) throws Exception {
        SemesterDto semesterDto1=this.semesterService.updateSemester(userId,departmentId,semesterId,semesterDto);
        if(semesterDto1!=null){
            return new ResponseEntity<>(semesterDto1,HttpStatusCode.valueOf(200));

        }else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!",false),HttpStatusCode.valueOf(500));
        }

    }

}
