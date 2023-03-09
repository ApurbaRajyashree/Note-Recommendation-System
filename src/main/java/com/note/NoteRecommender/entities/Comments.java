package com.note.NoteRecommender.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "comment_table")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="comment_id")
    private Long commentId;

    @Column(name = "comment",nullable = false)
    private String comment;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="note_id_fk",referencedColumnName = "note_id")
    @JsonBackReference(value = "note_table")
    private Note note;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="user_id_fk",referencedColumnName = "user_id")
    @JsonBackReference(value = "user_table")
    private User user;



}
