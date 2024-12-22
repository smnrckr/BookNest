package com.project.backend.repository;

import com.project.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(long id);
    List<Comment> findByReviewId(Long reviewId);

}
