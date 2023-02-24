package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepo extends JpaRepository<Comments, Long> {
}
