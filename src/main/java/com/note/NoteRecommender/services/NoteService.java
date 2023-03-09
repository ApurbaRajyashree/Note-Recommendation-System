package com.note.NoteRecommender.services;

import com.note.NoteRecommender.entities.Note;
import org.springframework.stereotype.Service;
import com.note.NoteRecommender.helper.NoteResponse;

import java.util.List;


@Service
public interface NoteService {
    Note createNote(Long userId, Long departmentId, Long semesterId, Long courseId, Note note) throws Exception;

    Note updateNote(Long userId, Long departmentId, Long semesterId, Long courseId, Long noteId, Note note);

    Note getNoteByName(Long userId, Long departmentId, Long semesterId, Long courseId, String name);

    Note getNoteById(Long noteId);

    List<Note> getNoteByCourse(Long userId, Long departmentId, Long semesterId, Long courseId);

    String deleteNote(Long userId, Long departmentId, Long semesterId, Long courseId, Long noteId) throws Exception;

    NoteResponse getAllNotes(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<Note> getAllNoteByUser(Long userId);

    List<Note> searchNote(String keyword);

    String approveNote(Long noteId);

    String rejectNote(Long noteId);
}
