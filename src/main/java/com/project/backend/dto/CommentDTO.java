package com.project.backend.dto;

import jakarta.persistence.Lob;

public class CommentDTO {

    private Long commentId;
    @Lob
    private String comment;
    private Long userId;
    private Long reviewId;
    private String username;

    public Long getCommentId() {
        return commentId;
    }

    public CommentDTO(String username, String comment) {
        this.comment = comment;
        this.username = username;

    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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
