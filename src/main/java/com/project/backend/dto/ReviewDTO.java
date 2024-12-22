package com.project.backend.dto;

import jakarta.persistence.Lob;

public class ReviewDTO {
    private Long reviewId;
    private String googleBookId;
    private Long userId;
    private String username;

    @Lob
    private String content;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getGoogleBookId() {
        return googleBookId;
    }

    public void setGoogleBookId(String googleBookId) {
        this.googleBookId = googleBookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ReviewDTO(Long reviewId,String username, String content) {
        this.reviewId = reviewId;
        this.username = username;
        this.content = content;

    }
}
