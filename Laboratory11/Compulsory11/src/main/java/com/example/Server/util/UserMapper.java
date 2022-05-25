package com.example.Server.util;

import com.example.Server.dto.UserDto;
import com.example.Server.model.UserEntity;
import com.example.Server.repository.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Data
@NoArgsConstructor
public class UserMapper {

    @Autowired
    private UserRepository userRepo;

    public UserDto mapToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setName(userEntity.getName());
        userDto.setUserFriends(userEntity.getUserFriends().stream()
                .map(UserEntity::getName).toList());

        return userDto;
    }

    public UserEntity mapToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userDto.getUserFriends().forEach(friendName -> {
            Optional<UserEntity> userEntity1 = userRepo.findByName(friendName);

            userEntity1.ifPresent(entity -> userEntity.getUserFriends().add(entity));
        });

        return userEntity;
    }
}
