package com.note.NoteRecommender.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.note.NoteRecommender.entities.Note;
import com.note.NoteRecommender.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CommentDto {
    private Long commentId;
    private String comment;

}
