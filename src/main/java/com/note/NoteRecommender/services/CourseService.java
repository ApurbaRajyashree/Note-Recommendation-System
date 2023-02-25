package com.note.NoteRecommender.services;


import com.note.NoteRecommender.dto.CourseDto;
import com.note.NoteRecommender.entities.Course;
import org.springframework.stereotype.Service;
import java.util.List;


public interface CourseService {
    CourseDto createCourse(Long userId, Long departmentId, Long semesterId, CourseDto courseDto) throws Exception;
    String deleteCourse(Long userId,Long departmentId,Long semesterId,Long courseId) throws Exception;
    CourseDto updateCourse(Long userId,Long departmentId,Long semesterId,Long courseId,CourseDto courseDto) throws Exception;
    List<CourseDto> getCoursesBySemester(Long userId, Long departmentId, Long semesterId) throws Exception;
    CourseDto getCourseById(Long userId,Long departmentId,Long semesterId,Long courseId) throws Exception;
    List<CourseDto> getAllCourse(Long userId);

}
