package model;

import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "User.findByName",
            query = "SELECT e FROM User e WHERE name = ?1"),
        @NamedQuery(name = "User.findAllFriends",
            query = "SELECT u FROM User e JOIN e.userFriends u WHERE e.id = ?1")
})
public class User implements Serializable {
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
    private List<User> userFriends = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
}
