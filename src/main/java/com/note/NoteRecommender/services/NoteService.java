package com.note.NoteRecommender.services;

import com.note.NoteRecommender.entities.Note;
import org.springframework.stereotype.Service;
import com.note.NoteRecommender.helper.NoteResponse;

import java.util.List;


@Service
public interface NoteService {


    //create note
    Note createNote(Long userId, Long departmentId, Long semesterId, Long courseId, Note note) throws Exception;

    //upate note
    Note updateNote(Long userId,Long departmentId,Long semesterId,Long courseId,Long noteId,Note note);

    //get note by name
    Note getNoteByName(Long userId,Long departmentId,Long semesterId,Long courseId,String name);
    Note getNoteById(Long noteId);

    //get note by course
    List<Note> getNoteByCourse(Long userId, Long departmentId, Long semesterId,Long courseId);


    //delete note by id
    void deleteNote(Long userId,Long departmentId,Long semesterId,Long courseId,Long noteId);

    NoteResponse getAllNotes(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);

    //Get all notes
    List<Note> getAllNoteByUser(Long userId);
    List<Note> searchNote(String keyword);
}
