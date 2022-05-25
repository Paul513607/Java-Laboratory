package com.example.Server.controller;

import com.example.Server.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendshipController {

    @Autowired
    private FriendsService friendsService;

    @GetMapping
    public ResponseEntity<List<String>> getFriendshipsOf(@RequestParam String username) {
        List<String> friends = friendsService.getFriendshipsOf(username);

        return ResponseEntity.ok().body(friends);
    }

    @PostMapping
    public ResponseEntity<?> addFriendship(@RequestParam String username1, @RequestParam String username2) {
        friendsService.addFriendship(username1, username2);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> removeFriendship(@RequestParam String username1, @RequestParam String username2) {
        friendsService.removeFriendship(username1, username2);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getTheMostPopularUsers(@RequestParam Integer howMany) {
        return ResponseEntity.ok().body(friendsService.getTheMostPopularUsers(howMany));
    }
}
