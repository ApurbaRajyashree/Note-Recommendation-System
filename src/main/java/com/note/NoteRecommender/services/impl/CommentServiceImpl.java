package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.dto.CommentDto;
import com.note.NoteRecommender.helper.QueryHelper;
import com.note.NoteRecommender.entities.Comments;
import com.note.NoteRecommender.entities.Note;
import com.note.NoteRecommender.repositories.CommentRepo;
import com.note.NoteRecommender.repositories.NoteRepositories;
import com.note.NoteRecommender.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private NoteRepositories noteRepositories;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private QueryHelper queryHelper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long noteId) {
        Note note=this.queryHelper.getNoteMethod(noteId);
        Comments comments=this.modelMapper.map(commentDto, Comments.class);
        comments.setNote(note);
        Comments savedComment=this.commentRepo.save(comments);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
            Comments comments=this.queryHelper.getCommentsMethod(commentId);
            this.commentRepo.delete(comments);
    }
}
