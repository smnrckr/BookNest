package com.project.backend.controller;

import com.project.backend.dto.FriendRelationsDTO;
import com.project.backend.dto.UpdateFriendRelationsDTO;
import com.project.backend.entity.Friend;
import com.project.backend.entity.User;
import com.project.backend.repository.FriendRepository;
import com.project.backend.repository.UserRepository;
import com.project.backend.service.FriendService;
import com.project.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @PostMapping("/sendFriendRequest")
    public ResponseEntity<?> sendFriendRequest(@RequestBody FriendRelationsDTO request){
        return friendService.sendFriendRequest(request.getUsername(),request.getFriendUsername());
    }

    @PutMapping("/updateFriendRequest")
    public ResponseEntity<?> updateFriendRequest(@RequestBody UpdateFriendRelationsDTO request) {
        return friendService.updateFriendshipStatus(request.getUsername(), request.getFriendUsername(), request.getNewStatus());
    }

    @GetMapping("/friendshipStatus")
    public ResponseEntity<?> checkFriendshipStatus(@RequestParam("username") String username, @RequestParam("friendUsername") String friendUsername) {
        return friendService.checkFriendshipStatus(username, friendUsername);
    }

    @GetMapping("/pendingFriendRequests")
    public ResponseEntity<?> getPendingFriendRequests(@RequestParam("username") String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        List<Friend> pendingRequests = friendRepository.findByFriendAndStatus(user, "PENDING");

        List<UpdateFriendRelationsDTO> requestDTOs = pendingRequests.stream()
                .map(request -> new UpdateFriendRelationsDTO(request.getUser().getUsername(), request.getFriend().getUsername(), request.getStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(requestDTOs);
    }


    @DeleteMapping("/deleteFriend")
    public ResponseEntity<?> deleteFriend(@RequestBody FriendRelationsDTO deleteFriendRequest){
        try{
            return friendService.removeFriend(deleteFriendRequest.getUsername(), deleteFriendRequest.getFriendUsername());

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friend could not be deleted");
        }

    }




}
