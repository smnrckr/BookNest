package com.project.backend.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="library_entity")
@EntityListeners(AuditingEntityListener.class)
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    private String author;

    private String genre;

    @Lob
    private String description;

    private Float rating;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("library-review")
    private List<Review> review;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date bookAddedAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime bookUpdatedAt;

    @ManyToMany(mappedBy = "books")
    @JsonBackReference("user-library")
    private List<User> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public Date getBookAddedAt() {
        return bookAddedAt;
    }

    public void setBookAddedAt(Date bookAddedAt) {
        this.bookAddedAt = bookAddedAt;
    }

    public LocalDateTime getBookUpdatedAt() {
        return bookUpdatedAt;
    }

    public void setBookUpdatedAt(LocalDateTime bookUpdatedAt) {
        this.bookUpdatedAt = bookUpdatedAt;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}