package com.example.Server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendship {
    private UserEntity user1;
    private UserEntity user2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFriendship that = (UserFriendship) o;
        return Objects.equals(user1, that.user1) && Objects.equals(user2, that.user2) ||
                Objects.equals(user1, that.user2) && Objects.equals(user2, that.user1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }

    public boolean containsUser(UserEntity user) {
        return user1.equals(user) || user2.equals(user);
    }
}
