package com.example.Server.controller;

import com.example.Server.dto.UserDto;
import com.example.Server.model.UserEntity;
import com.example.Server.service.UserService;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUserList() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@Valid @NotNull @RequestBody UserDto userDto) {
        userService.addUser(userDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @NotNull @RequestBody UserEntity user) {
        userService.updateUser(user);

        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@Valid @NotNull @RequestBody UserEntity user) {
        userService.deleteUser(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/alg")
    public ResponseEntity<List<UserDto>> getMostImportantUsers() {
        List<UserDto> users = userService.findMostImportantUsers();
        return ResponseEntity.ok().body(users);
    }
}
