package city;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class City {
    Set<Intersection> intersectionSet;
    List<Street> streetList;

    public City() {
        this.intersectionSet = new HashSet<>();
        this.streetList = new LinkedList<>();
    }

    public Set<Intersection> getIntersectionSet() {
        return intersectionSet;
    }

    public List<Street> getStreetList() {
        return streetList;
    }

    public Intersection getIntersectionByID(String intersectionID) {
        return intersectionSet.stream()
                .filter(intersection -> intersection.getName().equals(intersectionID))
                .findFirst()
                .get();
    }

    @Override
    public String toString() {
        return "City{" +
                "intersectionSet=" + intersectionSet +
                ", streetList=" + streetList +
                '}';
    }

    public void addIntersectionByID(String intersectionID) {
        Intersection newIntersection = new Intersection(intersectionID);
        intersectionSet.add(newIntersection);
    }

    public void addIntersection(Intersection intersection) {
        intersectionSet.add(intersection);
    }

    public void addStreet(Street street) throws IllegalArgumentException {
        if (street.getLength() <= 0)
            throw new IllegalArgumentException("Street length must be positive!");
        if (street.getInterName1().equals(street.getInterName2()))
            throw new IllegalArgumentException("Can't build street from and to the same intersection!");

        List<Street> tempStreetList = streetList.stream()
                .filter(street1 -> street1.getName().equals(street.getName()) ||
                        (street1.getInterName1().equals(street.getInterName1()) && street1.getInterName2().equals(street.getInterName2())) ||
                        (street1.getInterName1().equals(street.getInterName2()) && street1.getInterName2().equals(street.getInterName1())))
                .collect(Collectors.toList());
        if (!tempStreetList.isEmpty())
            throw new IllegalArgumentException("This street already exists!");

        if (!intersectionSet.contains(this.getIntersectionByID(street.getInterName1())) ||
                !intersectionSet.contains(this.getIntersectionByID(street.getInterName2())))
            throw new IllegalArgumentException("One of the intersections does not exist!");

        streetList.add(street);
    }
}
