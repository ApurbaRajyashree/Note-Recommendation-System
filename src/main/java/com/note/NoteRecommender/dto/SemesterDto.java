package com.note.NoteRecommender.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.note.NoteRecommender.entities.Course;
import com.note.NoteRecommender.entities.Department;
import com.note.NoteRecommender.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SemesterDto {

    private Long semesterId;

    private String semesterName;
    private List<CourseDto> courseDtoList;

    private DepartmentDto departmentDto;


}
