package model;

import lombok.Data;

/** The model for a continent in the database. */
@Data
public class Continent extends MapObject {
    public Continent() {
    }

    public Continent(int id, String name) {
        super(id, name);
    }
}
