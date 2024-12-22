package com.project.backend.service;

import com.project.backend.dto.CommentDTO;
import com.project.backend.entity.Comment;
import com.project.backend.entity.Review;
import com.project.backend.entity.User;
import com.project.backend.repository.CommentRepository;
import com.project.backend.repository.ReviewRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createComment(Long userId, String commentContent, Long reviewId) {
        try{
            Optional<Review> reviewOptional=reviewRepository.findById(reviewId);
            Optional<User> userOptional=userRepository.findById(userId);
            if(reviewOptional.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
            }
            User user= userOptional.get();
            Review review= reviewOptional.get();
            Comment comment=new Comment();
            comment.setUser(user);
            comment.setReview(review);
            comment.setComment(commentContent);
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment added" + comment);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public List<CommentDTO> getCommentsByReviewId(Long reviewId) {
        List<Comment> comments = commentRepository.findByReviewId(reviewId);
        return comments.stream()
                .map(comment -> new CommentDTO(comment.getUser().getUsername(), comment.getComment()))
                .collect(Collectors.toList());
    }


    public ResponseEntity<?> deleteComment(Long commentId) {
        try{
            Optional<Comment> commentOptional=commentRepository.findById(commentId);
            if(commentOptional.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
            }
            commentRepository.delete(commentOptional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> updateComment(Long commentId, String newCommentContent) {
        try {
            Optional<Comment> commentOptional = commentRepository.findById(commentId);
            if (commentOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
            }

            Comment comment = commentOptional.get();
            comment.setComment(newCommentContent);

            commentRepository.save(comment);

            return ResponseEntity.status(HttpStatus.OK).body("Comment updated: " + comment);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }





}
