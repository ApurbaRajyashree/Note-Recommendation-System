package com.note.NoteRecommender.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="role_table")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long roleId;
    @Column(name="role_name",unique = true,nullable = false)
    private String roleName;
    @ManyToMany(mappedBy = "userRoles")
    private Set<User> userSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="role_authority",
            joinColumns = @JoinColumn(name="role_id_fk",referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name="authority_id_fk",referencedColumnName = "authority_id")

    )
    private Set<Authority> authorities;
}
