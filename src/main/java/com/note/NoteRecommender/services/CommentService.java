package com.note.NoteRecommender.services;


import com.note.NoteRecommender.dto.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDto createComment(Long userId,Long noteId,CommentDto commentDto);
    String deleteComment(Long commentId);

    CommentDto updateComment(Long userId,Long noteId,Long commentId, String updateCommentText) throws Exception;

    CommentDto replyToComment(Long userId,Long noteId,Long commentId,CommentDto commentDto);

}
