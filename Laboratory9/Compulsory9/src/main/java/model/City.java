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
@Table(name = "cities")
@NamedQueries({
        @NamedQuery(name = "City.findAll",
                query = "SELECT e FROM City e ORDER BY e.name"),
        @NamedQuery(name = "City.findByName",
                query = "SELECT e FROM City e WHERE e.name = ?1"),
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
    private Long countryId;
    private Boolean isCapital;
    private Double latitude;
    private Double longitude;
    private Long population;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "city_sister_relation",
            joinColumns = { @JoinColumn(name = "id1") },
            inverseJoinColumns = { @JoinColumn(name = "id2") }
    )
    List<City> sisterCities = new ArrayList<>();

    public City(String name) {
        this.name = name;
        this.isCapital = false;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.population = 0L;
        this.countryId = 0L;
    }

    public City(String name, Long countryId, Boolean isCapital, Double latitude, Double longitude, Long population) {
        this.name = name;
        this.countryId = countryId;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
    }

    public City(long id, String name, long countryId, boolean isCapital, double latitude, double longitude, long population) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isCapital=" + isCapital +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", population=" + population +
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
