package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.Course;
import com.note.NoteRecommender.entities.Semester;
import com.note.NoteRecommender.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    List<Course> findBySemester(Semester semester);
    List<Course> findByUser(User user);
    Course findByCourseTitle(String title);
}
