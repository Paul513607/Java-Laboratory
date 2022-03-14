import city.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Compulsory4 {
    public static void main(String[] args) {
        Set<Intersection> intersectionSet = new HashSet<>();
        var intersectionArray = LongStream.range(1, 10)
                .mapToObj(x -> new Intersection(("i" + x)))
                        .toArray(Intersection[]::new);
        intersectionSet = Set.of(intersectionArray);
        intersectionSet.stream()
                .forEach(intersection -> System.out.println(intersection));

        List<Street> streetList = new LinkedList<>();
        streetList.add(new Street("s1", 2.0, "i1", "i2"));
        streetList.add(new Street("s2", 2.0, "i1", "i3"));
        streetList.add(new Street("s3", 2.0, "i1", "i4"));
        streetList.add(new Street("s4", 2.0, "i2", "i3"));
        streetList.add(new Street("s5", 3.0, "i2", "i5"));
        streetList.add(new Street("s6", 2.0, "i3", "i6"));
        streetList.add(new Street("s7", 2.0, "i3", "i7"));
        streetList.add(new Street("s8", 1.0, "i3", "i4"));
        streetList.add(new Street("s9", 3.0, "i4", "i7"));
        streetList.add(new Street("s10", 1.0, "i5", "i8"));
        streetList.add(new Street("s11", 2.0, "i5", "i9"));
        streetList.add(new Street("s12", 1.0, "i5", "i7"));
        streetList.add(new Street("s13", 1.0, "i6", "i8"));
        streetList.add(new Street("s14", 1.0, "i6", "i9"));
        streetList.add(new Street("s15", 3.0, "i7", "i9"));
        streetList.add(new Street("s16", 1.0, "i8", "i9"));

        streetList.sort((o1, o2) -> o1.getLength().compareTo(o2.getLength()));

        System.out.println("");
        streetList.stream()
                .forEach(street -> System.out.println(street));
    }
}
