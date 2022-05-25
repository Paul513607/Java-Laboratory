package com.example.Server.service;

import com.example.Server.customexception.UserNotFoundException;
import com.example.Server.dto.UserDto;
import com.example.Server.model.UserEntity;
import com.example.Server.repository.UserRepository;
import com.example.Server.util.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserMapper userMapper;

    public List<String> getFriendshipsOf(String username) {
        Optional<UserEntity> user = userRepo.findByName(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException(UserEntity.class, "name");
        }

        return user.get().getUserFriends().stream()
                .map(UserEntity::getName).toList();
    }

    public void addFriendship(String username1, String username2) {
        Optional<UserEntity> user1 = userRepo.findByName(username1);
        if (user1.isEmpty()) {
            throw new UserNotFoundException(UserEntity.class, "name");
        }

        Optional<UserEntity> user2 = userRepo.findByName(username2);
        if (user2.isEmpty()) {
            throw new UserNotFoundException(UserEntity.class, "name");
        }

        user1.get().getUserFriends().add(user2.get());
        user2.get().getUserFriends().add(user1.get());

        userRepo.save(user1.get());
        userRepo.save(user2.get());
    }

    public void removeFriendship(String username1, String username2) {
        Optional<UserEntity> user1 = userRepo.findByName(username1);
        if (user1.isEmpty()) {
            throw new UserNotFoundException(UserEntity.class, "name");
        }

        Optional<UserEntity> user2 = userRepo.findByName(username2);
        if (user2.isEmpty()) {
            throw new UserNotFoundException(UserEntity.class, "name");
        }

        user1.get().getUserFriends().remove(user2.get());
        user2.get().getUserFriends().remove(user1.get());

        userRepo.save(user1.get());
        userRepo.save(user2.get());
    }

    public List<UserDto> getTheMostPopularUsers(Integer howMany) {
        List<UserEntity> users = userRepo.findAll();
        users.sort((u1, u2) -> (-1) * Integer.compare(u1.getUserFriends().size(), u2.getUserFriends().size()));

        howMany = howMany > users.size() ? users.size() : howMany;

        List<UserDto> selectedUsers = new ArrayList<>();
        for (int count = 0; count < howMany; count++) {
            selectedUsers.add(userMapper.mapToDto(users.get(count)));
        }

        return selectedUsers;
    }
}
