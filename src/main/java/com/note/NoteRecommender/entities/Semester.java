package com.note.NoteRecommender.entities;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="semester_table")
@SQLDelete(sql = "UPDATE category_table c set c.deleted=true where c.cat_id=?")
@Where(clause = "deleted=false")

public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="semester_id")
    private Long semesterId;

    @NotEmpty(message = "Enter the semester: ")
    @Column(name="semester_name",unique = true,nullable = false)
    private String semesterName;

    private boolean deleted=Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "semester")
    @JsonManagedReference(value = "semester_table")
    private List<Course> courseList;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="department_id_fk",referencedColumnName = "department_id")
    @JsonBackReference(value = "department_table")
    private Department department;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "semesters")
    private Set<User> users;


}
