package com.note.NoteRecommender.services;

import com.note.NoteRecommender.entities.Rating;

import java.util.List;

public interface RatingService {

    Rating createRating(Long userId, Long noteId, Rating rating) throws Exception;
    Rating deleteRating(Long userId,Long noteId,Long ratingId);
    List<Rating> getRatingByMovie(Long movieId);
    Rating getRatingById(Long ratingId);
}
