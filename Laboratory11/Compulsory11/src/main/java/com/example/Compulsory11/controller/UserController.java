package com.example.Compulsory11.controller;

import com.example.Compulsory11.dto.UserDto;
import com.example.Compulsory11.model.UserEntity;
import com.example.Compulsory11.service.UserService;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @NotNull @RequestBody UserDto userDto) {
        userService.addUser(userDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @NotNull @RequestBody UserEntity user) {
        userService.updateUser(user);

        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @DeleteMapping ResponseEntity<?> deleteUser(@Valid @NotNull @RequestBody UserEntity user) {
        userService.deleteUser(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
