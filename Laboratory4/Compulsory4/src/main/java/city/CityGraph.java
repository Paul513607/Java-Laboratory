package city;

import com.github.javafaker.Faker;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// The support class for a city, it has the city's intersectionSet and streetSet, and it implements the WeightedGraph interface
public class CityGraph<V, E> implements WeightedGraph<V, E> {
    Set<Intersection> intersectionSet;
    Set<Street> streetSet;

    public CityGraph(City city) {
        this.intersectionSet = new HashSet<>(city.getIntersectionSet());
        this.streetSet = new HashSet<>(city.getStreetList());
    }

    @Override
    public Set getAllEdges(Object o1, Object o2) {
        if (!(o1 instanceof Intersection intersection1) || !(o2 instanceof Intersection intersection2))
            return null;
        return streetSet.stream()
                .filter(street -> street.getInterId1().equals(intersection1.getId()) && street.getInterId2().equals(intersection2.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Object getEdge(Object o1, Object o2) {
        if (!(o1 instanceof Intersection intersection1) || !(o2 instanceof Intersection intersection2))
            return null;
        return streetSet.stream()
                .filter(street -> street.getInterId1().equals(intersection1.getId()) && street.getInterId2().equals(intersection2.getId()))
                .findFirst()
                .get();
    }

    @Override
    public EdgeFactory getEdgeFactory() {
        return (EdgeFactory) streetSet;
    }

    @Override
    public Object addEdge(Object o1, Object o2) {
        if (!(o1 instanceof Intersection intersection1) || !(o2 instanceof Intersection intersection2))
            return null;
        Faker faker = new Faker();
        Street street = new Street(faker.address().streetName(), 0.0, intersection1.getId(), intersection2.getId());
        streetSet.add(street);
        return street;
    }

    @Override
    public boolean addEdge(Object o1, Object o2, Object s1) {
        if (!(o1 instanceof Intersection intersection1) || !(o2 instanceof Intersection intersection2))
            return false;
        if (!(s1 instanceof Street street))
            return false;
        if (!intersectionSet.contains(intersection1) || !intersectionSet.contains(intersection2) || streetSet.contains(street))
            return false;
        if (!street.getInterId1().equals(intersection1.getId()) || !street.getInterId2().equals(intersection2.getId()))
            return false;
        streetSet.add(street);
        return true;
    }

    @Override
    public boolean addVertex(Object o) {
        if (!(o instanceof Intersection intersection))
            return false;
        if (intersectionSet.contains(intersection))
            return false;
        intersectionSet.add(intersection);
        return true;
    }

    @Override
    public boolean containsEdge(Object o1, Object o2) {
        if (!(o1 instanceof Intersection) || !(o2 instanceof Intersection))
            return false;
        String interId1 = ((Intersection) o1).getId();
        String interId2 = ((Intersection) o2).getId();

        for (Street street : streetSet)
            if (street.getInterId1().equals(interId1) && street.getInterId2().equals(interId2))
                return true;
        return false;
    }

    @Override
    public boolean containsEdge(Object street) {
        if (!(street instanceof Street))
            return false;
        return streetSet.contains((Street) street);
    }

    @Override
    public boolean containsVertex(Object intersection) {
        if (!(intersection instanceof Intersection))
            return false;
        return intersectionSet.contains((Intersection) intersection);
    }

    @Override
    public Set edgeSet() {
        return streetSet;
    }

    @Override
    public Set edgesOf(Object o) {
        if (!(o instanceof Intersection intersection))
            return null;
        return streetSet.stream()
                .filter(street -> street.getInterId1().equals(intersection.getId()) || street.getInterId2().equals(intersection.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean removeAllEdges(Collection collection) {
        if (!streetSet.containsAll(collection))
            return false;
        streetSet.removeAll(collection);
        return true;
    }

    @Override
    public Set removeAllEdges(Object o1, Object o2) {
        if (!(o1 instanceof Intersection intersection1) || !(o2 instanceof Intersection intersection2))
            return null;
        Set toRemoveEdges = streetSet.stream()
                .filter(street -> street.getInterId1().equals(intersection1.getId()) && street.getInterId2().equals(intersection2.getId()))
                .collect(Collectors.toSet());
        streetSet.removeAll(toRemoveEdges);
        return toRemoveEdges;
    }

    @Override
    public boolean removeAllVertices(Collection collection) {
        if (!intersectionSet.containsAll(collection))
            intersectionSet.removeAll(collection);
        return true;
    }

    @Override
    public Object removeEdge(Object o1, Object o2) {
        if (!(o1 instanceof Intersection intersection1) || !(o2 instanceof Intersection intersection2))
            return null;
        Street toRemoveEdge = streetSet.stream()
                .filter(street -> street.getInterId1().equals(intersection1.getId()) && street.getInterId2().equals(intersection2.getId()))
                .findFirst()
                .get();
        streetSet.remove(toRemoveEdge);
        return toRemoveEdge;
    }

    @Override
    public boolean removeEdge(Object o) {
        if (!(o instanceof Street street))
            return false;
        if (!streetSet.contains(street))
            return false;
        streetSet.remove(street);
        return true;
    }

    @Override
    public boolean removeVertex(Object o) {
        if (!(o instanceof Intersection intersection))
            return false;
        if (!intersectionSet.contains(intersection))
            return false;
        intersectionSet.remove(intersection);
        return true;
    }

    @Override
    public Set vertexSet() {
        return intersectionSet;
    }

    @Override
    public Object getEdgeSource(Object o) {
        if (!(o instanceof Street street))
            return null;
        if (!streetSet.contains(street))
            return null;
        return getIntersectionByID(street.getInterId1());
    }

    private Intersection getIntersectionByID(String id) {
        return intersectionSet.stream()
                .filter(intersection -> intersection.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public Object getEdgeTarget(Object o) {
        if (!(o instanceof Street street))
            return null;
        if (!streetSet.contains(street))
            return null;
        return getIntersectionByID(street.getInterId2());
    }

    @Override
    public double getEdgeWeight(Object o) {
        if (!(o instanceof Street street))
            return 0.0;
        if (!streetSet.contains(street))
            return 0.0;
        return street.getLength();
    }

    @Override
    public void setEdgeWeight(Object o, double v) {
        if (!(o instanceof Street street))
            return;
        for (Street street1 : streetSet)
            if (street1.equals(street))
                street1.setLength(v);
    }

    private double getMaxLengthOfStreets() {
        return streetSet.stream()
                .max(Comparator.comparing(Street::getLength))
                .get().getLength();
    }

    public double getLengthBetween(Intersection intersection1, Intersection intersection2) {
        for (Street street : streetSet) {
            if (street.getInterId1().equals(intersection1.getId()) && street.getInterId2().equals((intersection2.getId())) ||
                    street.getInterId1().equals(intersection2.getId()) && street.getInterId2().equals(intersection1.getId())) {
                return street.length;
            }
        }
        return getMaxLengthOfStreets() + 1.0;
    }
}
