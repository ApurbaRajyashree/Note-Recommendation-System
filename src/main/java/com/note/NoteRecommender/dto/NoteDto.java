package com.note.NoteRecommender.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.note.NoteRecommender.entities.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NoteDto {


    private Long noteId;
    @NotEmpty(message = "Enter the Course Description ")
    private String noteDescription;

    @NotEmpty(message = "Enter the Note Title ")
    private String noteTitle;
    private String fileName;

    private Date dateOfNoteCreation;
    private File file;
    private NoteStatus noteStatus;

    private Boolean deleted = Boolean.FALSE;

    private Course course;

    private List<Comments> commentsList;

    private float averageRating;

    private int totalRatingCount;

    private User user;

    private List<Rating> ratings;


}
