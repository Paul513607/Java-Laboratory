package com.example.Compulsory11.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** The model for a user in the database. */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    private Long id;
    private String name;
    @ManyToMany(cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
    @JoinTable(
            name = "user_friends",
            joinColumns = { @JoinColumn( name = "id1" ) },
            inverseJoinColumns = { @JoinColumn( name = "id2" ) }
    )
    private List<UserEntity> userFriends = new ArrayList<>();
}