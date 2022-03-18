package city;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import com.github.javafaker.Faker;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// A class for the city with a set of intersections and a list of streets
public class City {
    Set<Intersection> intersectionSet;
    List<Street> streetList;

    public City() {
        this.intersectionSet = new HashSet<>();
        this.streetList = new LinkedList<>();
    }

    public City(Set<Intersection> intersectionSet, List<Street> streetList) throws IllegalArgumentException {
        this.intersectionSet = new HashSet<>(intersectionSet);
        this.streetList = new LinkedList<>();

        streetList.forEach(street -> validateStreet(street));

        this.streetList = streetList.stream()
                .collect(Collectors.toList());
    }

    public Set<Intersection> getIntersectionSet() {
        return intersectionSet;
    }

    public List<Street> getStreetList() {
        return streetList;
    }

    public void setIntersectionSet(Set<Intersection> intersectionSet) {
        this.intersectionSet = intersectionSet;
    }

    public void setStreetList(List<Street> streetList) throws IllegalArgumentException {
        streetList.forEach(street -> validateStreet(street));

        this.streetList = streetList;
    }

    public Intersection getIntersectionByID(String intersectionID) {
        return intersectionSet.stream()
                .filter(intersection -> intersection.getId().equals(intersectionID))
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
        Faker faker = new Faker();
        Intersection newIntersection = new Intersection(intersectionID, faker.address().streetName());
        intersectionSet.add(newIntersection);
    }

    public void addIntersection(Intersection intersection) {
        intersectionSet.add(intersection);
    }

    private void validateStreet(Street street) throws IllegalArgumentException {
        if (street.getLength() <= 0)
            throw new IllegalArgumentException("Street length must be positive!");
        if (street.getInterId1().equals(street.getInterId2()))
            throw new IllegalArgumentException("Can't build street from and to the same intersection!");

        if (!streetList.isEmpty()) {
            List<Street> tempStreetList = streetList.stream()
                    .filter(street1 -> street1.getName().equals(street.getName()) ||
                            (street1.getInterId1().equals(street.getInterId1()) && street1.getInterId2().equals(street.getInterId2())) ||
                            (street1.getInterId1().equals(street.getInterId2()) && street1.getInterId2().equals(street.getInterId1())))
                    .collect(Collectors.toList());
            if (!tempStreetList.isEmpty())
                throw new IllegalArgumentException("This street already exists!");
        }

        if (!intersectionSet.contains(this.getIntersectionByID(street.getInterId1())) ||
                !intersectionSet.contains(this.getIntersectionByID(street.getInterId2())))
            throw new IllegalArgumentException("One of the intersections does not exist!");
    }

    public void addStreet(Street street) throws IllegalArgumentException {
        validateStreet(street);

        streetList.add(street);
    }

    private void printConnectionStreetsToIntersection(Intersection intersection) {
        System.out.print("\tStreets: ");
        streetList.stream()
                .forEach(street -> {
                    if (street.getInterId1().equals(intersection.getId()) || street.getInterId2().equals(intersection.getId()))
                        System.out.print("'" + street.getName() + "' ");
                });
        System.out.println("");
    }

    public void printCitySchema() {
        System.out.println("The intersections are (along with the streets that have them as a node):");
        intersectionSet.stream()
                .forEach(intersection -> {
                    System.out.println(intersection);
                    printConnectionStreetsToIntersection(intersection);
                });
    }

    private void sortStreetsLengths() {
        // lambda sorting
        // streetList.sort((o1, o2) -> o1.getLength().compareTo(o2.getLength()));

        // method reference sorting
        streetList = streetList.stream()
                .sorted(Comparator.comparing(Street::getLength))
                .toList();
    }

    // For a given intersection, returns the number of streets connected to it
    private int numberOfConnectedStreets(Intersection intersection) {
        AtomicInteger streetCount = new AtomicInteger();
        streetList.stream()
                .forEach(street -> {
                    if (street.getInterId1().equals(intersection.getId()) || street.getInterId2().equals(intersection.getId()))
                        streetCount.getAndIncrement();
                });

        return streetCount.intValue();
    }

    // displays the streets with a length>=<hitLength> and that have at least 3 other streets with which it connects
    public void printImportantStreets(int hitLength) {
        streetList.stream()
                .filter(street -> street.getLength() >= hitLength)
                .filter(street -> {
                    int connectedStreetsCount = numberOfConnectedStreets(getIntersectionByID(street.getInterId1())) - 1 +
                            numberOfConnectedStreets(getIntersectionByID(street.getInterId2())) - 1;
                    return connectedStreetsCount >= 3;
                })
                .forEach(street -> System.out.println(street));
    }

    // This method applies Kruskal's MST Algorithm on the city graph of the city, finding the optimal way of putting data cables on the streets (edges)
    public Set getOptimalDataCablesStreets() {
        CityGraph<Intersection, Street> cityGraph = new CityGraph(this);

        KruskalMinimumSpanningTree kruskalMinimumSpanningTree = new KruskalMinimumSpanningTree(cityGraph);
        double minimumCostTotalLength = kruskalMinimumSpanningTree.getMinimumSpanningTreeTotalWeight();
        Set<Street> optimalCablesStreets = kruskalMinimumSpanningTree.getMinimumSpanningTreeEdgeSet();

        System.out.println("The optimal streets to put data cables on (obtained with Kruskal's MST Algorithm) are (with a total cost of: " + minimumCostTotalLength + "):");
        optimalCablesStreets.stream()
                .forEach(street -> System.out.println(street));
        return optimalCablesStreets;
    }
}
