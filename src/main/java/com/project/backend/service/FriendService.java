package com.project.backend.service;

import com.project.backend.entity.Friend;
import com.project.backend.entity.User;
import com.project.backend.repository.FriendRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  FriendRepository friendRepository;

    public ResponseEntity<?> addFriend(String username, String friendUsername) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<User> friendOptional = userRepository.findByUsername(friendUsername);

        if(friendOptional.isEmpty() && userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or friend not found");
        }
        User user = userOptional.get();
        User friend = friendOptional.get();

        Optional<Friend> friendRelation =friendRepository.findByUserAndFriend(user, friend);

        if(friendRelation.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Friend already exists");
        }

        Friend friendship = new Friend();
        friendship.setUser(user);
        friendship.setFriend(friend);


        Friend reverseFriendship = new Friend();
        reverseFriendship.setUser(friend);
        reverseFriendship.setFriend(user);

        friendRepository.saveAll(List.of(friendship, reverseFriendship));
        return ResponseEntity.status(HttpStatus.CREATED).body("Friend added successfully");
    }

    public ResponseEntity<?> removeFriend(String username, String friendUsername) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<User> friendOptional = userRepository.findByUsername(friendUsername);

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
