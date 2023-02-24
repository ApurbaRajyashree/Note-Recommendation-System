package com.note.NoteRecommender.dto;

import com.note.NoteRecommender.entities.Note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CourseDto {
    private Long courseId;
    private String courseTitle;
    private String courseDesc;
    private List<Note>  notes;
    private SemesterDto semesterDto;
}
