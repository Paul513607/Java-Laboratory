package model;

import lombok.Data;

import java.util.Objects;

/** The model for a city sister relation in the database. */
@Data
public class CitySisterRelation {
    private int cityId1;
    private int cityId2;

    public CitySisterRelation(int cityId1, int cityId2) {
        this.cityId1 = cityId1;
        this.cityId2 = cityId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitySisterRelation relation = (CitySisterRelation) o;
        return cityId1 == relation.cityId1 && cityId2 == relation.cityId2 ||
                cityId1 == relation.cityId2 && cityId2 == relation.cityId1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId1, cityId2);
    }

    public boolean containsNode(City node) {
        return cityId1 == node.getId() || cityId2 == node.getId();
    }
}
