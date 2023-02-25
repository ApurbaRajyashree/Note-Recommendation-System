package com.note.NoteRecommender.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="course_table")
@SQLDelete(sql = "UPDATE course_table c set c.deleted=true where c.course_id=?")
@Where(clause = "deleted=false")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="course_id")
    private Long courseId;

    @NotEmpty(message = "Enter the Course Title ")
    @Column(name="course_title",unique = true,nullable = false)
    private String courseTitle;


    @NotNull(message = "Course description cannot be empty")
    @Column(name="course_desc")
    private String courseDesc;

    @Column(name = "deleted")
    private Boolean deleted=Boolean.FALSE;

    @ManyToMany(mappedBy = "courses")
    private Set<User> users;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "course")
    @JsonManagedReference(value = "course_table")
    private List<Note> noteList;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "semester_id_fk",referencedColumnName = "semester_id")
    @JsonBackReference(value = "semester_table")
    private Semester semester;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="user_id_fk",referencedColumnName = "user_id")
    @JsonBackReference(value = "user_table")
    private User user;

}
