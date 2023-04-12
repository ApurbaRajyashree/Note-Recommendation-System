package com.note.NoteRecommender.services;

import com.note.NoteRecommender.entities.Rating;

public interface RatingService {

    Rating giveRating(Long userId, Long noteId, Rating rating) throws Exception;

    float averageRating(Long noteId);
}
