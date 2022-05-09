package abstractrepos;

import model.City;

/** A generic repo for both the JPA and the JDBC implementations of a city repo. */
public interface CityRepo extends GenericRepo<City> {
}
