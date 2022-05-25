package com.example.Server.service;

import com.example.Server.algorithms.CutpointsAlgorithm;
import com.example.Server.customexception.UserAlreadyExists;
import com.example.Server.customexception.UserNotFoundException;
import com.example.Server.dto.UserDto;
import com.example.Server.model.UserEntity;
import com.example.Server.model.UserFriendship;
import com.example.Server.model.UserGraph;
import com.example.Server.repository.UserRepository;
import com.example.Server.util.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User was not found in the database.");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        return new User(user.get().getName(), passwordEncoder.encode("123456"), authorities);
    }

    public List<UserDto> findAll() {
        List<UserDto> userDtoList = userRepository.findAll().stream()
                .map(user -> userMapper.mapToDto(user))
                .toList();
        if (userDtoList.isEmpty()) {
            throw new UserNotFoundException(UserEntity.class, "no params");
        }

        return userDtoList;
    }

    public void addUser(UserDto userDto) {
        Optional<UserEntity> userOpt = userRepository.findByName(userDto.getName());
        if (userOpt.isPresent()) {
            throw new UserAlreadyExists(UserEntity.class, "no params");
        }

        UserEntity newUser = userMapper.mapToEntity(userDto);
        userRepository.save(newUser);
    }

    public void updateUser(UserEntity user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException(UserEntity.class, "id");
        }

        userRepository.save(user);
    }

    public void deleteUser(UserEntity user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException(UserEntity.class, "id");
        }

        userRepository.delete(user);
    }

    public List<UserDto> findMostImportantUsers() {
        Set<UserEntity> users = new HashSet<>(userRepository.findAll());
        Set<UserFriendship> friendships = new HashSet<>();

        users.forEach(user -> {
            user.getUserFriends().forEach(friend -> {
                friendships.add(new UserFriendship(user, friend));
            });
        });

        UserGraph graph = new UserGraph(users, friendships);
        CutpointsAlgorithm cutpointsAlgorithm = new CutpointsAlgorithm(graph);
        cutpointsAlgorithm.runAlgorithm();
        cutpointsAlgorithm.printSolution();

        return cutpointsAlgorithm.getResultSet().stream()
                .map(user -> userMapper.mapToDto(user))
                .toList();
    }
}
