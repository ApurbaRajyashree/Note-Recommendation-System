package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.Department;
import com.note.NoteRecommender.entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SemesterRepo extends JpaRepository<Semester,Long> {
    List<Semester> findByDepartment(Department department);
    Semester findBySemesterName(String name);
}
