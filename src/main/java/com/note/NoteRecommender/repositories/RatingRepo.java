package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Long> {

//        @Query("select avg(r.value) from Rating r inner join Note n on r.note_id_fk=n.noteId where r.note_id_fk=? group by note_id_fk")
//        float averageRating(Long noteId);

}
