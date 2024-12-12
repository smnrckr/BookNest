package com.project.backend.controller;

import com.project.backend.dto.CommentDTO;
import com.project.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO createCommentRequest) {
        try{
             return commentService.createComment(createCommentRequest.getUserId(),createCommentRequest.getComment(),createCommentRequest.getReviewId());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment cannot be added");
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment (@PathVariable Long commentId) {
        try {
            return commentService.deleteComment(commentId);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment cannot be deleted");
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestParam String newCommentContent) {
        return commentService.updateComment(commentId, newCommentContent);
    }

}
