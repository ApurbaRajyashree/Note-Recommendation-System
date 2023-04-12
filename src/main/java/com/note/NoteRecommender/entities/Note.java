package com.note.NoteRecommender.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "note_table")
@SQLDelete(sql = "UPDATE exam_table e set e.deleted=true where e.exam_id=?")
@Where(clause = "deleted=false")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_id")
    private Long noteId;

    @Column(name = "note_description", nullable = false, length = 1000)
    private String noteDescription;

    @Column(name = "note_title", nullable = false, unique = true, length = 100)
    private String noteTitle;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "date_of_note_creation")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateOfNoteCreation;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id_fk", referencedColumnName = "file_id")
    private File file;

    @Enumerated(EnumType.STRING)
    private NoteStatus noteStatus;

    private Boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id_fk", referencedColumnName = "course_id")
    @JsonBackReference(value = "course_table")
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "note")
    @JsonManagedReference(value = "note_table")
    private List<Comments> commentsList;

    @Column(name = "average_rating")
    private float averageRating;

    @Column(name = "total_rating")
    private int totalRatingCount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id")
    @JsonBackReference(value = "user_table")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "note")
    @JsonManagedReference(value = "ratings")
    private List<Rating> ratings;
}
