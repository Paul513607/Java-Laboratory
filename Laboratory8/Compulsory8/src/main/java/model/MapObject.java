package model;

import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapObject mapObject = (MapObject) o;
        return id == mapObject.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
