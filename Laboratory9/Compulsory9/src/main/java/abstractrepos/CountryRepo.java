package abstractrepos;

import model.Country;

/** A generic repo for both the JPA and the JDBC implementations of a country repo. */
public interface CountryRepo extends GenericRepo<Country> {
}
