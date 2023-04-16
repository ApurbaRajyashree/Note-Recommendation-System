package com.note.NoteRecommender.entities;


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

@Entity
@Table(name="department_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE department_table f set f.deleted=true where f.department_id=?")
@Where(clause = "deleted=false")
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="department_id")
     private Long departmentId;


    @NotEmpty(message = "Enter the departmentName ")
    @Column(name = "department_name",nullable = false, unique = true)
    private String departmentName;


    @NotNull(message = "Department description cannot be empty")
    @Column(name = "description",nullable = false)
    private String departmentDescription;

    private boolean deleted=Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "department")
    @JsonManagedReference(value = "department_table")
    private List<Semester> semesterList;

    @ManyToMany(mappedBy = "departments")
    private Set<User> users;
}
