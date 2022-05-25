package com.example.Server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomepageController {

    @GetMapping
    public ResponseEntity<String> getHomeMessage() {
        return ResponseEntity.ok().body("Hello world!");
    }
}
