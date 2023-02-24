package com.note.NoteRecommender.controller;


import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.entities.Course;
import com.note.NoteRecommender.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/user/{uId}/dep/{depId}/sem/{semId}/course/create")
    ResponseEntity<?> createCourseController(@Valid @PathVariable Long uId, @PathVariable Long depId, @PathVariable Long semId, @RequestBody Course course) throws Exception {
        Course course1=this.courseService.createCourse(uId,depId,semId,course);
        if(course1==null){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!",false), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(course1, HttpStatusCode.valueOf(200));
    }

    @PutMapping("/user/{uId}/dep/{depId}/sem/{semId}/cou/{couId}/update")
    public ResponseEntity<?> updateCourse(@Valid @PathVariable("uId")Long uId, @PathVariable("depId")Long depId,@PathVariable("semId")Long semId,@PathVariable("couId")Long couId, @RequestBody Course course) throws Exception {
        Course updatedCourse=this.courseService.updateCourse(uId,depId,semId,couId,course);

        if (updatedCourse != null) {
            return new ResponseEntity<>(updatedCourse, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));

        }
    }

    @GetMapping("/user/{uId}/dep/{depId}/sem/{semId}/courses")
    public ResponseEntity<?> getCoursesBySemester(@PathVariable("uId") Long uId, @PathVariable("depId") Long depId,@PathVariable("semId") Long semId) throws Exception {
        List<Course> courses = this.courseService.getCoursesBySemester(uId, depId,semId);
        if (courses != null) {
            return new ResponseEntity<>(courses, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping("/user/{uId}/dep/{depId}/sem/{semId}/course/{couName}")
    public ResponseEntity<?>  getCourseByName(@PathVariable("uId") Long uId, @PathVariable("depId") Long depId,@PathVariable("semId") Long semId,@PathVariable("couName") String couName) throws Exception {
        Course course = this.courseService.getCourseById(uId, depId,semId,couName);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));
        }
    }

}
