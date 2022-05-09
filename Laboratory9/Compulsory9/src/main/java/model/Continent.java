package model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** The model of a city in the database. */
@Entity
@Table(name = "continents")
@NamedQueries({
        @NamedQuery(name = "Continent.findAll",
                query = "SELECT e FROM Continent e ORDER BY e.name"),
        @NamedQuery(name = "Continent.findByName",
                query = "SELECT e FROM Continent e WHERE e.name = ?1"),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Continent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    private Long id;
    private String name;
    @OneToMany (
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "continentId", insertable = false)
    private List<Country> countries = new ArrayList<>();

    public Continent(String name) {
        this.name = name;
    }

    public Continent(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Continent continent = (Continent) o;
        return Objects.equals(id, continent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
