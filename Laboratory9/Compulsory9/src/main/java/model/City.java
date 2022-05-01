package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cities")
@NamedQueries({
        @NamedQuery(name = "City.findAll",
                query = "SELECT e FROM City e ORDER BY e.name"),
        @NamedQuery(name = "City.findById",
                query = "SELECT e FROM City e WHERE e.id = ?1"),
        @NamedQuery(name = "City.findByName",
                query = "SELECT e FROM City e WHERE e.name = ?1"),
        @NamedQuery(name = "City.findByCountry",
                query = "SELECT e FROM City e WHERE e.country = ?1 ORDER BY e.name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Country country;
    private Boolean isCapital;
    private Double latitude;
    private Double longitude;

    public City(String name) {
        this.name = name;
        this.country = null;
        this.isCapital = false;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
        this.isCapital = false;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public City(String name, Country country, Boolean isCapital, Double latitude, Double longitude) {
        this.name = name;
        this.country = country;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country[=" + country.getId() + ", " + country.getName() +
                "], isCapital=" + isCapital +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
