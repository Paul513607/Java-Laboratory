package util;

import datasource.EntityManagerFactoryCreator;
import model.City;
import model.Continent;
import model.Country;
import repositories.CityRepository;
import repositories.ContinentRepository;
import repositories.CountryRepository;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** A class that imports datasets about continents, countries and cities and adds the data to the database. */
public class DatasetImporter {
    File continentDatasetFile = new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Laboratory-resources/resources-Lab8/continents.csv");
    File countriesDatasetFile = new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Laboratory-resources/resources-Lab8/countryContinent.csv");
    File citiesDatasetFile = new File("/home/paul/Facultate/An2/Semestru2/PA-Java/Laboratory/Laboratory-resources/resources-Lab8/worldcities.csv");

    public DatasetImporter() {
    }

    private void importContinents(EntityManager em) throws FileNotFoundException {
        ContinentRepository continentRepo = new ContinentRepository(em);
        Scanner scanner = new Scanner(continentDatasetFile);

        while(scanner.hasNextLine()) {
            String currLine = scanner.nextLine();
            Continent continent = new Continent(currLine);

            continentRepo.save(continent);
        }
    }

    private void importCountries(EntityManager em) throws FileNotFoundException {
        ContinentRepository continentRepo = new ContinentRepository(em);
        CountryRepository countryRepo = new CountryRepository(em);
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
            Continent continent = continentRepo.findByName(continentName).get();

            Country country = new Country(name, code);
            continent.getCountries().add(country);
        }
    }

    private void importCities(EntityManager em) throws FileNotFoundException {
        CountryRepository countryRepo = new CountryRepository(em);
        CityRepository cityRepo = new CityRepository(em);
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
            Country country = countryRepo.findByName(countryName).get();
            currLine = currLine.substring(indexOfComma + 1);

            // ignore the unnecessary values
            for (int iterate = 1; iterate <= 3; ++iterate) {
                indexOfComma = currLine.indexOf(',');
                currLine = currLine.substring(indexOfComma + 1);
            }

            indexOfComma = currLine.indexOf(',');
            String capitalString = currLine.substring(0, indexOfComma);
            boolean isCapital = capitalString.equals("primary");
            currLine = currLine.substring(indexOfComma + 1);

            indexOfComma = currLine.indexOf(',');
            String populationString = currLine.substring(0, indexOfComma);
            int indexOfDot = populationString.indexOf('.');
            if (indexOfDot != -1) {
                populationString = populationString.substring(0, indexOfDot);
            }
            Long population = populationString.equals("") ? 0L : Long.parseLong(populationString);

            City city = new City(name, country.getId(), isCapital, latitude, longitude, population);
            country.getCities().add(city);
        }
    }

    public void importDatasets() {
        EntityManager em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        try {
            System.out.println("Started importing datasets...");

            importContinents(em);
            importCountries(em);
            importCities(em);

            System.out.println("Done importing datasets.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        em.getTransaction().commit();
        em.close();
        EntityManagerFactoryCreator.closeEntityManagerFactory();
    }
}
