package com.note.NoteRecommender.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE user_table u set u.deleted=true where u.user_id=?")
@Where(clause = "deleted=false")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;


    @NotEmpty(message = "Enter your Username: ")
    @Column(name = "user_name", nullable = false)
    private String userName;


    @NotNull(message = "Email cannot be empty")
    @Email(regexp = "[a-z0-9._%+-]+@texasintl.edu.np", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email address must be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull(message = "Phone Number cannot be empty")
    @Digits(message = "Number should contain 10 digits.", fraction = 0, integer = 10)
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "batch")
    @Digits(message = "You should enter your enroll year.", fraction = 0, integer = 4)
    private String batch;

    @NotNull(message = "Password cannot be empty")
    @Column(name = "password", nullable = false)
    private String password;

    private Boolean deleted = Boolean.FALSE;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private Boolean isEnabled = Boolean.FALSE;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id_fk", referencedColumnName = "role_id")
    )
    private Set<Role> userRoles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_department",
            joinColumns = @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id_fk", referencedColumnName = "department_id")
    )
    private Set<Department> departments;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
//    @JsonManagedReference(value = "user_table")
//    private List<UserDepartment> userDepartmentList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id_fk", referencedColumnName = "course_id")
    )
    private Set<Course> courses;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_semester",
            joinColumns = @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "semester_id_fk", referencedColumnName = "semester_id")
    )
    private Set<Semester> semesters;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_notes",
            joinColumns = @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id_fk", referencedColumnName = "note_id")
    )
    private Set<Note> notes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "user_table")
    private List<Comments> comments;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference(value = "user_table")
    private List<Rating> ratings;



}
