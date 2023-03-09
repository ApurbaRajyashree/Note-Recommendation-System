package com.note.NoteRecommender.controller;


import com.note.NoteRecommender.dto.CourseDto;
import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.entities.Course;
import com.note.NoteRecommender.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/user/{uId}/dep/{depId}/sem/{semId}/course/create")
    @PreAuthorize("hasAuthority('manage_course')")
    ResponseEntity<?> createCourseController(@Valid @PathVariable Long uId, @PathVariable Long depId, @PathVariable Long semId, @RequestBody CourseDto course) throws Exception {
        CourseDto course1 = this.courseService.createCourse(uId, depId, semId, course);
        if (course1 == null) {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(course1, HttpStatusCode.valueOf(200));
    }

    @PutMapping("/user/{uId}/dep/{depId}/sem/{semId}/cou/{couId}/update")
    @PreAuthorize("hasAuthority('manage_course')")
    public ResponseEntity<?> updateCourse(@Valid @PathVariable("uId") Long uId, @PathVariable("depId") Long depId, @PathVariable("semId") Long semId, @PathVariable("couId") Long couId, @RequestBody CourseDto course) throws Exception {
        CourseDto updatedCourse = this.courseService.updateCourse(uId, depId, semId, couId, course);

        if (updatedCourse != null) {
            return new ResponseEntity<>(updatedCourse, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));

        }
    }

    @GetMapping("/user/{uId}/dep/{depId}/sem/{semId}/courses")
    @PreAuthorize("hasAuthority('manage_course')")
    public ResponseEntity<?> getCoursesBySemester(@PathVariable("uId") Long uId, @PathVariable("depId") Long depId, @PathVariable("semId") Long semId) throws Exception {
        List<CourseDto> courses = this.courseService.getCoursesBySemester(uId, depId, semId);
        if (courses != null) {
            return new ResponseEntity<>(courses, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping("/user/{uId}/dep/{depId}/sem/{semId}/course/{couId}")
    @PreAuthorize("hasAuthority('manage_course')")
    public ResponseEntity<?> getCourseById(@PathVariable("uId") Long uId, @PathVariable("depId") Long depId, @PathVariable("semId") Long semId, @PathVariable("couId") Long couId) throws Exception {
        CourseDto course = this.courseService.getCourseById(uId, depId, semId, couId);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping("/user/{userId}/allCourses")
    @PreAuthorize("hasAuthority('manage_course')")
    ResponseEntity<?> getAllCourse(@PathVariable("userId") Long userId) {
        List<CourseDto> courseDtos = this.courseService.getAllCourse(userId);
        if (courseDtos.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("There is no courses", false), HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(courseDtos, HttpStatusCode.valueOf(200));
        }
    }

    @DeleteMapping("/user/{uId}/dep/{depId}/sem/{semId}/course/{couId}")
    @PreAuthorize("hasAuthority('manage_course')")
    ResponseEntity<ApiResponse> deleteCourse(@PathVariable("uId") Long uId, @PathVariable("depId") Long depId, @PathVariable("semId") Long semId, @PathVariable("couId") Long couId) throws Exception {
        String message = this.courseService.deleteCourse(uId, depId, semId, couId);
        if(message.contains("successfully")){
            return new ResponseEntity<>(new ApiResponse(message,true),HttpStatusCode.valueOf(200));
        }else {
            return new ResponseEntity<>(new ApiResponse(message,false),HttpStatusCode.valueOf(200));
        }
    }
}
