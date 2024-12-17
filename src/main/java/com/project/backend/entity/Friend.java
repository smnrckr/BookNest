package com.project.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "friend_entity")
@EntityListeners(AuditingEntityListener.class)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference("user-friend")
    private User user;

    @ManyToOne
    @JoinColumn(name="friend_id")
    @JsonBackReference("friend-user")
    private User friend;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date friendshipAdded;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime friendshipUpdatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public Date getFriendshipAdded() {
        return friendshipAdded;
    }

    public void setFriendshipAdded(Date friendshipAdded) {
        this.friendshipAdded = friendshipAdded;
    }

    public LocalDateTime getFriendshipUpdatedAt() {
        return friendshipUpdatedAt;
    }

    public void setFriendshipUpdatedAt(LocalDateTime friendshipUpdatedAt) {
        this.friendshipUpdatedAt = friendshipUpdatedAt;
    }
}
