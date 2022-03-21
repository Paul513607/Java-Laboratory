import algorithms.PrimAlgorithm;
import algorithms.SimulatedAnnealingTSP;
import city.*;
import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Compulsory4 {
    static City createCity() {
        Faker faker = new Faker();

        Set<Intersection> intersectionSet = new HashSet<>();
        var intersectionArray = LongStream.range(1, 10)
                .mapToObj(x -> new Intersection("i" + x, faker.funnyName().name()))
                .toArray(Intersection[]::new);
        intersectionSet = Set.of(intersectionArray);
        intersectionSet.stream()
                .forEach(intersection -> System.out.println(intersection));
        System.out.println();

        List<Street> streetList = new LinkedList<>();
        streetList.add(new Street(faker.address().streetName(), 2.0, "i1", "i2"));
        streetList.add(new Street(faker.address().streetName(), 2.0, "i1", "i3"));
        streetList.add(new Street(faker.address().streetName(), 2.0, "i1", "i4"));
        streetList.add(new Street(faker.address().streetName(), 2.0, "i2", "i3"));
        streetList.add(new Street(faker.address().streetName(), 3.0, "i2", "i5"));
        streetList.add(new Street(faker.address().streetName(), 2.0, "i3", "i6"));
        streetList.add(new Street(faker.address().streetName(), 2.0, "i3", "i7"));
        streetList.add(new Street(faker.address().streetName(), 1.0, "i3", "i4"));
        streetList.add(new Street(faker.address().streetName(), 3.0, "i4", "i7"));
        streetList.add(new Street(faker.address().streetName(), 1.0, "i5", "i8"));
        streetList.add(new Street(faker.address().streetName(), 2.0, "i5", "i9"));
        streetList.add(new Street(faker.address().streetName(), 1.0, "i5", "i7"));
        streetList.add(new Street(faker.address().streetName(), 1.0, "i6", "i8"));
        streetList.add(new Street(faker.address().streetName(), 1.0, "i6", "i9"));
        streetList.add(new Street(faker.address().streetName(), 3.0, "i7", "i9"));
        streetList.add(new Street(faker.address().streetName(), 1.0, "i8", "i9"));

        // lambda sorting
        // streetList.sort((o1, o2) -> o1.getLength().compareTo(o2.getLength()));

        // method reference sorting
        streetList = streetList.stream()
                .sorted(Comparator.comparing(Street::getLength))
                .toList();

        System.out.println("");
        streetList.stream()
                .forEach(street -> System.out.println(street));
        System.out.println("");

        City city = null;
        try {
            city = new City(intersectionSet, streetList);
        }
        catch (IllegalArgumentException err) {
            err.printStackTrace();
        }

        return city;
    }

    public static void main(String[] args) {
        RandomCityGenerator randomCityGenerator = new RandomCityGenerator();
        randomCityGenerator.generateRandomCity();
        City city = randomCityGenerator.getCity();
        // City city = createCity();

        city.printCitySchema();
        System.out.println("");

        city.sortStreetsLengths();
        System.out.println("The streets sorted by length:");
        city.getStreetList().forEach(System.out::println);
        System.out.println();

        city.printImportantStreets(2);
        System.out.println("");

        city.getOptimalDataCablesStreets();
        System.out.println("");

        PrimAlgorithm primAlgorithm = new PrimAlgorithm(new CityGraph<>(city));
        primAlgorithm.primMSTAlgorithm(city.getIntersectionByID("i1"));
        primAlgorithm.printSolution();
        System.out.println("");

        SimulatedAnnealingTSP simulatedAnnealingTSP = new SimulatedAnnealingTSP(new CityGraph<>(city));
        simulatedAnnealingTSP.simulatedAnnealing(1000, 1000.0, 0.99);
        simulatedAnnealingTSP.printSolution();
        System.out.println("");
    }
}
