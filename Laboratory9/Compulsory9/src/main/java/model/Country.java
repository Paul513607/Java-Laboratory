package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "countries")
@NamedQueries({
        @NamedQuery(name = "Country.findAll",
                query = "SELECT e FROM Country e ORDER BY e.name"),
        @NamedQuery(name = "Country.findById",
                query = "SELECT e FROM Country e WHERE e.id = ?1"),
        @NamedQuery(name = "Country.findByName",
                query = "SELECT e FROM Country e WHERE e.name = ?1"),
        @NamedQuery(name = "Country.findByContinent",
                query = "SELECT e FROM Country e WHERE e.continent = ?1 ORDER BY e.name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    private Long id;
    private String name;
    private String code;
    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Continent continent;

    public Country(String name) {
        this.name = name;
        this.code = null;
        this.continent = null;
    }

    public Country(String name, String code, Continent continent) {
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public Country(String name, Continent continent) {
        this.name = name;
        this.code = null;
        this.continent = continent;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", continent=[" + continent.getId() + ", " + continent.getName() +
                "]}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
