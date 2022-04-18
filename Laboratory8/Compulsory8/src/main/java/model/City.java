package model;

import lombok.Data;

/** The model for a city in the database. */
@Data
public class City extends MapObject {
    int country;
    boolean capital;
    double latitude;
    double longitude;

    public City() {
    }

    public City(int id, String name) {
        super(id, name);
    }

    public City(int id, String name, int country, boolean capital, double latitude, double longitude) {
        super(id, name);
        this.country = country;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
