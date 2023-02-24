package com.note.NoteRecommender.controller;


import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.entities.Semester;
import com.note.NoteRecommender.services.SemesterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @PostMapping("/user/{userId}/department/{departmentId}/semester/create")
    ResponseEntity<?> createSemesterController(@Valid  @PathVariable("userId") Long userId, @PathVariable("departmentId") Long departmentId, @RequestBody Semester semester) throws Exception {
        Semester createdSemester = this.semesterService.createSemester(userId, departmentId, semester);

        return new ResponseEntity<>(createdSemester, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/department/{departmentId}/semester/read")
    public ResponseEntity<?> readSemesterByDepartment(@PathVariable("userId") Long userId, @PathVariable("departmentId") Long departmentId) throws Exception {
        List<Semester> semesters = this.semesterService.readSemesterByDepartment(userId, departmentId);
        if (semesters != null) {
            return new ResponseEntity<>(semesters, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));

        }

    }

    @GetMapping("/user/{userId}/department/{departmentId}/semester/{semesterName}/readByName")
    ResponseEntity<?> readSemesterByName(@PathVariable("userId") Long userId, @PathVariable("departmentId") Long departmentId, @PathVariable("semesterName") String semesterName) throws Exception {
        Semester resultSemester = this.semesterService.readSemesterByName(userId, departmentId, semesterName);
        if (resultSemester != null) {
            return new ResponseEntity<>(resultSemester, HttpStatusCode.valueOf(200));

        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));

        }
    }


}
