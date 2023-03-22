package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.dto.CommentDto;
import com.note.NoteRecommender.entities.User;
import com.note.NoteRecommender.helper.QueryHelper;
import com.note.NoteRecommender.entities.Comments;
import com.note.NoteRecommender.entities.Note;
import com.note.NoteRecommender.repositories.CommentRepo;
import com.note.NoteRecommender.repositories.NoteRepositories;
import com.note.NoteRecommender.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private QueryHelper queryHelper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(Long userId, Long noteId,CommentDto commentDto) {
        Note note=this.queryHelper.getNoteMethod(noteId);
        User user=this.queryHelper.getUserMethod(userId);
        Comments comments=this.modelMapper.map(commentDto, Comments.class);
        comments.setNote(note);
        comments.setUser(user);
        comments.setParentComment(null);
        Comments savedComment=this.commentRepo.save(comments);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public String deleteComment(Long commentId) {
            Comments comments=this.queryHelper.getCommentsMethod(commentId);
            this.commentRepo.delete(comments);
            return "Comment deleted Successfully";
    }

    @Override
    public CommentDto updateComment(Long userId, Long noteId,Long commentId, String updateCommentText) throws Exception {
        Comments existingComment=this.queryHelper.getCommentsMethod(commentId);
        if(existingComment.getNote().getNoteId().equals(noteId)){
            if(existingComment.getUser().getUserId().equals(userId)){
                existingComment.setComment(updateCommentText);
            }else {
                throw new Exception("You are not the owner of the comment. You cannot update the comment.");
            }
        }else {
            throw new Exception("You have not commented in this note!!");
        }
        Comments updatedComment=this.commentRepo.save(existingComment);
        return this.modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public CommentDto replyToComment(Long userId, Long noteId, Long commentId,CommentDto commentDto) {
        User user=this.queryHelper.getUserMethod(userId);
        Note note=this.queryHelper.getNoteMethod(noteId);
        Comments comment=this.queryHelper.getCommentsMethod(commentId);
        Comments reply=this.modelMapper.map(commentDto, Comments.class);
        reply.setNote(note);
        reply.setUser(user);
        reply.setParentComment(comment);
        reply.setCreatedAt(new Date());
        Comments savedReply=this.commentRepo.save(reply);
        return this.modelMapper.map(savedReply, CommentDto.class);
    }
}
