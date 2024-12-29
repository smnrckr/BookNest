package com.project.backend.service;

import com.project.backend.dto.FriendshipStatusDTO;
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

    public ResponseEntity<?> sendFriendRequest(String username, String friendUsername){
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

        Friend friendRequest = new Friend();
        friendRequest.setUser(user);
        friendRequest.setFriend(friend);
        friendRequest.setStatus("PENDING");

        Friend reverseFriendRequest = new Friend();
        reverseFriendRequest.setUser(friend);
        reverseFriendRequest.setFriend(user);
        reverseFriendRequest.setStatus("PENDING");

        friendRepository.saveAll(List.of(friendRequest, reverseFriendRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body("Friend request sended");

    }

    public ResponseEntity<?> updateFriendshipStatus(String username, String friendUsername, String newStatus) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<User> friendOptional = userRepository.findByUsername(friendUsername);

        if (userOptional.isEmpty() || friendOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or friend not found");
        }

        User user = userOptional.get();
        User friend = friendOptional.get();

        Optional<Friend> existingRelation = friendRepository.findByUserAndFriend(user, friend);

        Friend friendRelation;
        if (existingRelation.isPresent()) {
            friendRelation = existingRelation.get();
        } else {
            friendRelation = new Friend();
            friendRelation.setUser(user);
            friendRelation.setFriend(friend);
            friendRelation.setStatus(newStatus);
            friendRepository.save(friendRelation);
        }

        if (!newStatus.equals("ACCEPTED") && !newStatus.equals("REJECTED")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status");
        }

        friendRelation.setStatus(newStatus);
        friendRepository.save(friendRelation);

        if (newStatus.equals("ACCEPTED")) {
            Optional<Friend> reverseRelation = friendRepository.findByUserAndFriend(friend, user);
            if (reverseRelation.isEmpty()) {
                Friend reverseFriendRelation = new Friend();
                reverseFriendRelation.setUser(friend);
                reverseFriendRelation.setFriend(user);
                reverseFriendRelation.setStatus("ACCEPTED");
                friendRepository.save(reverseFriendRelation);
            } else {
                Friend reverseFriendRelation = reverseRelation.get();
                reverseFriendRelation.setStatus("ACCEPTED");
                friendRepository.save(reverseFriendRelation);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("Friendship status updated successfully");
    }


    public ResponseEntity<?> checkFriendshipStatus(String username, String friendUsername) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<User> friendOptional = userRepository.findByUsername(friendUsername);
        if (userOptional.isEmpty() || friendOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or friend not found");
        }

        User user = userOptional.get();
        User friend = friendOptional.get();

        Optional<Friend> existingRelation = friendRepository.findByUserAndFriend(user, friend);

        if (existingRelation.isPresent()) {
            return ResponseEntity.ok(new FriendshipStatusDTO("ACCEPTED"));
        }

        Optional<Friend> reverseRelation = friendRepository.findByUserAndFriend(friend, user);
        if (reverseRelation.isPresent() && reverseRelation.get().getStatus().equals("PENDING")) {
            return ResponseEntity.ok(new FriendshipStatusDTO("PENDING"));
        }
        return ResponseEntity.ok(new FriendshipStatusDTO("not_friends"));
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
