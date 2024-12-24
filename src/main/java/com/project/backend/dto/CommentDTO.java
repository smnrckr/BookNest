package com.project.backend.dto;

import jakarta.persistence.Lob;
public class CommentDTO {

    private Long id;
    @Lob
    private String comment;

    private Long userId;
    private Long reviewId;
    private String username;

    // Constructor
    public CommentDTO(Long id, String comment, Long userId, Long reviewId, String username) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.reviewId = reviewId;
        this.username = username;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
