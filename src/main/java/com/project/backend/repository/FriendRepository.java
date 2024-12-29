package com.project.backend.repository;

import com.project.backend.entity.Friend;
import com.project.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByUserAndFriend(User user, User friend);
    List<Friend> findByFriendAndStatus(User friend, String status);
}
