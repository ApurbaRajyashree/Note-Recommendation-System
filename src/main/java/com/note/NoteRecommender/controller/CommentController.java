package com.note.NoteRecommender.controller;


import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.dto.CommentDto;
import com.note.NoteRecommender.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;


    @PostMapping("/note/{noteId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comments, @PathVariable Long noteId){
           CommentDto createComment= this.commentService.createComment(comments,noteId);
            return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully",true), HttpStatus.OK);
    }


}
