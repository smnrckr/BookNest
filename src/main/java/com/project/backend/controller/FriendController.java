package com.project.backend.controller;

import com.project.backend.dto.FriendRelationsDTO;
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
    public ResponseEntity<?> addFriend(@RequestBody FriendRelationsDTO addFriendRequest) {
        try{
            return friendService.addFriend(addFriendRequest.getUsername(), addFriendRequest.getFriendUsername());

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friend could not be added");
        }
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
