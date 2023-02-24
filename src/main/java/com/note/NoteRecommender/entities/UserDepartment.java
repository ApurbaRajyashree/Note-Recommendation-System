//package com.note.NoteRecommender.entities;
//
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//
//@Entity
//@Table(name="user_department_table")
//
//public class UserDepartment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "user_department_id")
//    private Long userDepartmentId;
//
//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id_fk",referencedColumnName = "user_id")
//    @JsonBackReference(value = "user_table")
//    private User user;
//
//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name="department_id_fk",referencedColumnName = "department_id")
//    @JsonBackReference(value = "department_table")
//    private Department department;
//
//}
