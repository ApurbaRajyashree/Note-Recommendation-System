package com.note.NoteRecommender.services;


import com.note.NoteRecommender.dto.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Long noteId);
    void deleteComment(Long commentId);

}
