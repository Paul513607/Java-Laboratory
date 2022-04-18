package model;

import lombok.Data;

/** The model for a generic map object in the database. */
@Data
public abstract class MapObject {
    int id;
    String name;

    public MapObject() {
    }

    public MapObject(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
