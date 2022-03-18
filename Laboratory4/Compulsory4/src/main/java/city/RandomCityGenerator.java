package city;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class RandomCityGenerator {
    private final Integer MAX_NO_INTERSECTIONS = 20;
    private final Integer MAX_NO_STREETS = (int) (Math.random() * (MAX_NO_INTERSECTIONS * (MAX_NO_INTERSECTIONS - 1)  - MAX_NO_INTERSECTIONS * (MAX_NO_INTERSECTIONS - 1) / 2) + MAX_NO_INTERSECTIONS * (MAX_NO_INTERSECTIONS - 1) / 2);
    private final Double MAX_STREET_LENGTH = 10.0;
    private City city;
    private Set<Intersection> intersectionSet;
    private List<Street> streetList;

    public RandomCityGenerator() {
    }

    public City getCity() {
        return city;
    }

    private int generateRandomIndex() {
        return (int) (Math.random() * intersectionSet.size());
    }

    public Intersection getIntersectionByID(String intersectionID) {
        return intersectionSet.stream()
                .filter(intersection -> intersection.getId().equals(intersectionID))
                .findFirst()
                .get();
    }

    private boolean validateStreetTriangulation(Street street) {
        Intersection sourceInter = getIntersectionByID(street.getInterId1());
        Intersection destInter = getIntersectionByID(street.getInterId2());
        double currStreetLength = street.getLength();

        for (Street street1 : streetList) {
            Intersection connectingInter = null;
            double streetLength1 = 0.0;
            // Find a street connected to one of the current street's intersections
            if (street1.getInterId1().equals(sourceInter.getId())) {
                connectingInter = getIntersectionByID(street1.getInterId2());
                if (connectingInter.equals(destInter))
                    return false;
                streetLength1 = street1.getLength();
            } else if (street1.getInterId2().equals(sourceInter.getId())) {
                connectingInter = getIntersectionByID(street1.getInterId1());
                if (connectingInter.equals(destInter))
                    return false;
                streetLength1 = street1.getLength();
            }

            if (connectingInter == null)
                return true;
            // find the street connected to the current street and the street found prior
            for (Street street2 : streetList) {
                if (street2.getInterId1().equals(destInter.getId()) && street2.getInterId2().equals(connectingInter.getId()) ||
                    street2.getInterId2().equals(destInter.getId()) && street2.getInterId1().equals(connectingInter.getId())) {
                    double streetLength2 = street2.getLength();
                    // Verify the triangulation rule
                    if (!(currStreetLength + streetLength1 <= streetLength2 && currStreetLength + streetLength2 <= streetLength1
                            && streetLength1 + streetLength2 <= currStreetLength)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void generateRandomCity() {
        Faker faker = new Faker();

        int noCities = (int) (Math.random() * (MAX_NO_INTERSECTIONS - MAX_NO_INTERSECTIONS / 2) + MAX_NO_INTERSECTIONS / 2);
        intersectionSet = new HashSet<>();
        var intersectionArray = LongStream.range(1, noCities)
                .mapToObj(x -> new Intersection("i" + x, faker.funnyName().name()))
                .toArray(Intersection[]::new);
        intersectionSet = Set.of(intersectionArray);
        intersectionSet.stream()
                .forEach(intersection -> System.out.println(intersection));

        List<Intersection> intersectionList = new ArrayList<>(intersectionSet);

        streetList = new LinkedList<>();
        int noStreets = (int) (Math.random() * (MAX_NO_STREETS - MAX_NO_STREETS / 2) + MAX_NO_STREETS / 2);
        for (int iterate = 0; iterate < noStreets; ++iterate) {
            int index1 = generateRandomIndex();
            int index2 = generateRandomIndex();
            if (index1 == index2) {
                iterate--;
                continue;
            }

            Intersection intersection1 = intersectionList.get(index1);
            Intersection intersection2 = intersectionList.get(index2);

            double streetLength = Math.random() * (MAX_STREET_LENGTH - 1.0) + 1.0;
            Street street = new Street(faker.address().streetName(), streetLength, intersection1.getId(), intersection2.getId());
            if (!validateStreetTriangulation(street)) {
                iterate--;
                continue;
            }

            streetList.add(street);
        }

        city = new City(intersectionSet, streetList);
    }
}
