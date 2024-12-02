package com.project.backend.service;

import com.project.backend.entity.Friend;
import com.project.backend.entity.User;
import com.project.backend.repository.FriendRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  FriendRepository friendRepository;


    public ResponseEntity<?> addFriend(Long userId, Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> friendOptional = userRepository.findById(friendId);

        if(friendOptional.isEmpty() && userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or friend not found");
        }
        User user = userOptional.get();
        User friend = friendOptional.get();

        Optional<Friend> friendRelation =friendRepository.findByUserAndFriend(user, friend);

        if(friendRelation.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Friend already exists");
        }

        Friend friendship1 = new Friend();
        friendship1.setUser(user);
        friendship1.setFriend(friend);
        friendRepository.save(friendship1); // user -> friend

        Friend friendship2 = new Friend();
        friendship2.setUser(friend);
        friendship2.setFriend(user);
        friendRepository.save(friendship2);

        return ResponseEntity.status(HttpStatus.CREATED).body("Friend added successfully");
    }

    public ResponseEntity<?> removeFriend(Long userId, Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> friendOptional = userRepository.findById(friendId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (friendOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Friend not found");
        }

        User user = userOptional.get();
        User friend = friendOptional.get();
        Optional<Friend> friendRelation =friendRepository.findByUserAndFriend(user, friend);
        if(friendRelation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friend doesn't exist");
        }

        friendRepository.delete(friendRelation.get());
        return ResponseEntity.status(HttpStatus.OK).body("Friend removed successfully");
    }
}
