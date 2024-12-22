package com.project.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "review_entity")
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-review")
    private User user;

    @ManyToOne
    @JoinColumn(name = "google_book_id")
    @JsonBackReference("library-review")
    private Library library;

    @OneToMany(mappedBy = "review")
    @JsonManagedReference("review-comment")
    private List<Comment> comment;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
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


