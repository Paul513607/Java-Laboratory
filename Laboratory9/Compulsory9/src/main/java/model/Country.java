package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "countries")
@NamedQueries({
        @NamedQuery(name = "Country.findAll",
                query = "SELECT e FROM Country e ORDER BY e.name"),
        @NamedQuery(name = "Country.findByName",
                query = "SELECT e FROM Country e WHERE e.name = ?1"),
        @NamedQuery(name = "Country.findCountryOfCity",
                query = "SELECT cc.name FROM Country cc " +
                        "JOIN City c ON cc.id=c.countryId WHERE c.id = ?1"),
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
    private Long continentId;
    @OneToMany (
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "countryId", insertable = false)
    private List<City> cities = new ArrayList<>();

    public Country(String name) {
        this.name = name;
        this.code = null;
    }

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Country(String name, Continent continent) {
        this.name = name;
        this.code = null;
    }

    public Country(Long id, String name, String code, Long continent) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.continentId = continent;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
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
