package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Long> {
}
