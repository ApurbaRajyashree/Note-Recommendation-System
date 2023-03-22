package com.note.NoteRecommender.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.note.NoteRecommender.entities.Comments;
import com.note.NoteRecommender.entities.Note;
import com.note.NoteRecommender.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CommentDto {
    private Long commentId;
    private String comment;
    private Date createdAt;
    private Long parentCommentId;
    private Note note;
    private User user;
    private List<CommentDto> replies;


}
