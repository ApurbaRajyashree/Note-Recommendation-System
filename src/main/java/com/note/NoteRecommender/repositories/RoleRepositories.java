package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepositories extends JpaRepository<Role,Long> {
    Role findByRoleName(String name);
}
