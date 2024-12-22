package com.project.backend.controller;

import com.project.backend.dto.ReviewDTO;
import com.project.backend.entity.Review;
import com.project.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/createReview")
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO createReviewRequest) {
        try {
            return reviewService.newReview(createReviewRequest.getUserId(), createReviewRequest.getContent(),
                    createReviewRequest.getGoogleBookId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Review cannot be created");
        }
    }


    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview (@PathVariable Long reviewId) {
        try {
            return reviewService.deleteReview(reviewId);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Review cannot be deleted");
        }
    }


    @GetMapping("/reviews/{googleBookId}")
    public ResponseEntity<?> getReviewsOfBook(@PathVariable String googleBookId) {
        try {
            List<ReviewDTO> reviews = reviewService.getBooksReviews(googleBookId);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reviews cannot be retrieved");
        }
    }

}
