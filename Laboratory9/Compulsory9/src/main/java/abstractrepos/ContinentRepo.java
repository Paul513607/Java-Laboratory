package abstractrepos;

import model.Continent;

/** A generic repo for both the JPA and the JDBC implementations of a continent repo. */
public interface ContinentRepo extends GenericRepo<Continent> {
}
