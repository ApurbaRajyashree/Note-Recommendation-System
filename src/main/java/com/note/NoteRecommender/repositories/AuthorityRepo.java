package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepo extends JpaRepository<Authority,Long> {
    Authority findByAuthorityName(String name);
}
