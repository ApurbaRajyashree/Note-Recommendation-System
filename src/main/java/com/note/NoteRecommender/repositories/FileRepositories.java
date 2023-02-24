package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepositories extends JpaRepository<File,Long> {
}
