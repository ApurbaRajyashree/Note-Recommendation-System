package com.note.NoteRecommender.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.note.NoteRecommender.entities.Semester;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DepartmentDto {


    private Long departmentId;

    private String departmentName;

    private String departmentDescription;

    private List<SemesterDto> semesterDtoList;
}
