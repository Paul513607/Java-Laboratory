package com.example.Compulsory11.service;

import com.example.Compulsory11.customexception.UserAlreadyExists;
import com.example.Compulsory11.customexception.UserNotFoundException;
import com.example.Compulsory11.dto.UserDto;
import com.example.Compulsory11.model.UserEntity;
import com.example.Compulsory11.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDto> findAll() {
        List<UserDto> userDtoList = userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
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

        UserEntity newUser = modelMapper.map(userDto, UserEntity.class);
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
}
