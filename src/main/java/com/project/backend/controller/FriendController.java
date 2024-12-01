package com.project.backend.controller;

import com.project.backend.dto.AddFriendDTO;
import com.project.backend.entity.Friend;
import com.project.backend.repository.FriendRepository;
import com.project.backend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/addFriend")
    public ResponseEntity<?> addFriend(@RequestBody AddFriendDTO addFriendRequest) {
        try{
            return friendService.addFriend(addFriendRequest.getUserId(), addFriendRequest.getFriendId());

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friend could not be added");
        }
    }

    @DeleteMapping("/deleteFriend")
    public ResponseEntity<?> deleteFriend(@RequestBody AddFriendDTO deleteFriendRequest){
        try{
            return friendService.removeFriend(deleteFriendRequest.getUserId(), deleteFriendRequest.getFriendId());

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friend could not be deleted");
        }

    }




}
