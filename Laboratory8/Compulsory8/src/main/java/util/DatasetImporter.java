package util;

import dao.CityDao;
import dao.ContinentDao;
import dao.CountryDao;
import datasource.Database;
import model.Country;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/** A class that imports datasets about continents, countries and cities and adds the data to the database. */
public class DatasetImporter {
    File continentDatasetFile = new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Laboratory-resources/resources-Lab8/continents.csv");
    File countriesDatasetFile = new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Laboratory-resources/resources-Lab8/countryContinent.csv");
    File citiesDatasetFile = new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Laboratory-resources/resources-Lab8/worldcities.csv");

    public DatasetImporter() {
    }

    private void importContinents() throws FileNotFoundException, SQLException {
        ContinentDao continents = new ContinentDao();
        Scanner scanner = new Scanner(continentDatasetFile);

        while(scanner.hasNextLine()) {
            String currLine = scanner.nextLine();

            continents.create(currLine);
            Database.getConnection().commit();
        }
    }

    private void importCountries() throws FileNotFoundException, SQLException {
        ContinentDao continents = new ContinentDao();
        CountryDao countries = new CountryDao();
        Scanner scanner = new Scanner(countriesDatasetFile);

        // Ignore the first line
        if (scanner.hasNextLine())
            scanner.nextLine();

        while (scanner.hasNextLine()) {
            String currLine = scanner.nextLine();

            int indexOfComma = currLine.indexOf(',');
            String name = currLine.substring(0, indexOfComma);
            currLine = currLine.substring(indexOfComma + 1);

            indexOfComma = currLine.indexOf(',');
            String code = currLine.substring(0, indexOfComma);
            currLine = currLine.substring(indexOfComma + 1);

            // ignore the unnecessary values
            for (int iterate = 1; iterate <= 3; ++iterate) {
                indexOfComma = currLine.indexOf(',');
                currLine = currLine.substring(indexOfComma + 1);
            }

            indexOfComma = currLine.indexOf(',');
            String continentName = currLine.substring(0, indexOfComma);
            int continentId = continents.findByName(continentName);

            countries.create(name, code, continentId);
            Database.getConnection().commit();
        }
    }

    private void importCities() throws FileNotFoundException, SQLException {
        CountryDao countries = new CountryDao();
        CityDao cities = new CityDao();
        Scanner scanner = new Scanner(citiesDatasetFile);

        // Ignore the first line
        if (scanner.hasNextLine())
            scanner.nextLine();

        while (scanner.hasNextLine()) {
            String currLine = scanner.nextLine();

            int indexOfComma = currLine.indexOf(',');
            currLine = currLine.substring(indexOfComma + 1);

            indexOfComma = currLine.indexOf(',');
            String name = currLine.substring(0, indexOfComma);
            currLine = currLine.substring(indexOfComma + 1);

            indexOfComma = currLine.indexOf(',');
            String latitudeString = currLine.substring(0, indexOfComma);
            double latitude = Double.parseDouble(latitudeString);
            currLine = currLine.substring(indexOfComma + 1);

            indexOfComma = currLine.indexOf(',');
            String longitudeString = currLine.substring(0, indexOfComma);
            double longitude = Double.parseDouble(longitudeString);
            currLine = currLine.substring(indexOfComma + 1);

            indexOfComma = currLine.indexOf(',');
            String countryName = currLine.substring(0, indexOfComma);
            int countryId = countries.findByName(countryName);
            currLine = currLine.substring(indexOfComma + 1);

            // ignore the unnecessary values
            for (int iterate = 1; iterate <= 3; ++iterate) {
                indexOfComma = currLine.indexOf(',');
                currLine = currLine.substring(indexOfComma + 1);
            }

            indexOfComma = currLine.indexOf(',');
            String capitalString = currLine.substring(0, indexOfComma);
            boolean isCapital = capitalString.equals("primary");

            cities.create(name, countryId, isCapital, latitude, longitude);
            Database.getConnection().commit();
        }
    }

    public void importDatasets() throws SQLException {
        try {
            importContinents();
            importCountries();
            importCities();
        } catch (FileNotFoundException e)  {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.rollback();
        }
    }
}
