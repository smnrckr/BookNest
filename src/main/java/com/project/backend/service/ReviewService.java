package com.project.backend.service;

import com.project.backend.dto.ReviewDTO;
import com.project.backend.entity.Library;
import com.project.backend.entity.Review;
import com.project.backend.entity.User;
import com.project.backend.repository.LibraryRepository;
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
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> newReview(Long userId, String content, String googleBookId) {
        try{
            Optional<Library> bookOptional = libraryRepository.findByGoogleBookId(googleBookId);
            Optional<User> userOptional = UserRepository.findById(userId);
            if(userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            if(bookOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }

            User user = userOptional.get();
            Library book = bookOptional.get();
            Review review = new Review();
            review.setUser(user);
            review.setLibrary(book);
            review.setContent(content);
            reviewRepository.save(review);
            return ResponseEntity.status(HttpStatus.CREATED).body("Review Created: "+review);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }

    }

    public ResponseEntity<?> deleteReview(Long reviewId) {
        try{
            Optional<Review> reviewOptional=reviewRepository.findById(reviewId);
            if(reviewOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
            }
            Review review = reviewOptional.get();
            reviewRepository.delete(review);
            return ResponseEntity.status(HttpStatus.OK).body("Review Deleted");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }

    }

    public ResponseEntity<?> updateReview(Long reviewId, String content) {
        try {
            Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
            if (reviewOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
            }

            Review review = reviewOptional.get();

            review.setContent(content);

            reviewRepository.save(review);

            return ResponseEntity.status(HttpStatus.OK).body("Review updated: " + review);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }

    }


    public List<ReviewDTO> getBooksReviews(String googleBookId) {
        if (googleBookId == null) {
            throw new IllegalArgumentException("Book doesn't exist");
        }

        List<Review> reviews = reviewRepository.findByLibraryGoogleBookId(googleBookId);
        return reviews.stream()
                .map(review -> new ReviewDTO(review.getId(), review.getUser().getUsername(),review.getContent()))
                .collect(Collectors.toList());
    }



}
