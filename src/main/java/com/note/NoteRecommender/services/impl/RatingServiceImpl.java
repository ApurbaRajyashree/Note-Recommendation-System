package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.entities.Rating;
import com.note.NoteRecommender.services.RatingService;

import java.util.List;

public class RatingServiceImpl implements RatingService {
    @Override
    public Rating createRating(Long userId, Long noteId, Rating rating) throws Exception {
        return null;
    }

    @Override
    public Rating deleteRating(Long userId, Long noteId, Long ratingId) {
        return null;
    }

    @Override
    public List<Rating> getRatingByMovie(Long movieId) {
        return null;
    }

    @Override
    public Rating getRatingById(Long ratingId) {
        return null;
    }
}
