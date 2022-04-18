import dao.ContinentDao;
import dao.CountryDao;
import datasource.Database;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    /** A test that adds a continent and two contries to the database, extracts them, checking if they're the expected values, and prints them. */
    @Test
    public void mainTest() {
        try {
            var continents = new ContinentDao();
            continents.create("Europe");
            Database.getConnection().commit();

            var countries = new CountryDao();
            int europeId = continents.findByName("Europe");
            countries.create("Romania", europeId);
            countries.create("Ukraine", europeId);
            Database.getConnection().commit();

            List<String> requiredCountries = List.of("Romania", "Ukraine");

            assertEquals(requiredCountries, countries.findCountriesOnContinent(europeId));
            System.out.println("Countries in \"Europe\" are: " + countries.findCountriesOnContinent(europeId));  // prints all the countries in "Europe"

            // Database.getConnection().close();
            Database.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.rollback();
        }
    }
}