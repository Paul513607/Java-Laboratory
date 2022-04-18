package model;

import lombok.Data;

/** The model for a country in the database. */
@Data
public class Country extends MapObject {
    String code;
    int continent;

    public Country() {
    }

    public Country(int id, String name) {
        super(id, name);
    }

    public Country(int id, String name, int continent) {
        super(id, name);
        this.code = "";
        this.continent = continent;
    }

    public Country(int id, String name, String code, int continent) {
        super(id, name);
        this.code = code;
        this.continent = continent;
    }
}
