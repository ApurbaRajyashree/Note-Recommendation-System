package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.Course;
import com.note.NoteRecommender.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.note.NoteRecommender.entities.User;

@Repository
public interface NoteRepositories extends JpaRepository<Note,Long> {
    List<Note> findAllByUser(User user);
    List<Note> findAllByCourse(Course course);
    Note findNoteByNoteTitle(String title);
    List<Note> findByNoteTitleContaining(String title);

    Note findNoteByNoteId(Long noteId);
}
