package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.entities.Note;
import com.note.NoteRecommender.entities.Rating;
import com.note.NoteRecommender.entities.User;
import com.note.NoteRecommender.repositories.NoteRepositories;
import com.note.NoteRecommender.repositories.RatingRepo;
import com.note.NoteRecommender.repositories.UserRepositories;
import com.note.NoteRecommender.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final UserRepositories userRepositories;
    private final NoteRepositories noteRepositories;

    private final RatingRepo ratingRepo;

    @Override
    public Rating giveRating(Long userId, Long noteId, Rating rating) throws Exception {
        User user = userRepositories.findById(userId).orElseThrow(
                () -> new RuntimeException("Invalid userId")
        );
        Note note = noteRepositories.findById(noteId).orElseThrow(
                () -> new RuntimeException("Invalid note id")
        );
        if(rating.getValue()>rating.getMaxRating()){
            throw new RuntimeException("Rating cannot be more than 5.");
        }
//        rating.setNote(note);
//        rating.setUser(user);
        return ratingRepo.save(rating);
    }

    @Override
    public float averageRating(Long noteId) {
        return 1;
    }


}
