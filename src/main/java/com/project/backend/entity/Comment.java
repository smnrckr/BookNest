package com.project.backend.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comment_entity")
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name= "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date reviewAdded;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime reviewUpdatedAt;

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

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getReviewAdded() {
        return reviewAdded;
    }

    public void setReviewAdded(Date reviewAdded) {
        this.reviewAdded = reviewAdded;
    }

    public LocalDateTime getReviewUpdatedAt() {
        return reviewUpdatedAt;
    }

    public void setReviewUpdatedAt(LocalDateTime reviewUpdatedAt) {
        this.reviewUpdatedAt = reviewUpdatedAt;
    }
}
