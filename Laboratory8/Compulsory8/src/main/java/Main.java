import algorithms.BronKerboschCliqueFinderAlgorithm;
import dao.CityDao;
import dao.ContinentDao;
import dao.CountryDao;
import datasource.Database;
import datasource.DatabaseConPool;
import mapdisplay.MapDisplayTool;
import model.City;
import model.CityRelationsGraph;
import model.CitySisterRelation;
import util.DatasetImporter;
import util.FakeCityGenerator;

import java.sql.SQLException;

public class Main {
    public void printTest() {
        try {
            var continents = new ContinentDao();
            continents.create("Europe");
            Database.getConnection().commit();

            var countries = new CountryDao();
            int europeId = continents.findByName("Europe");
            countries.create("Romania", europeId);
            countries.create("Ukraine", europeId);
            Database.getConnection().commit();

            System.out.println(countries.findCountriesOnContinent(europeId));  // prints all the countries in "Europe"

            Database.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.rollback();
        }
    }

    public void conPoolTest() {
        try {
            var continents = new ContinentDao();
            var countries = new CountryDao();
            int europeId = continents.findByName("Europe");

            System.out.println(countries.findCountriesOnContinent(europeId));  // prints all the countries in "Europe"

            DatabaseConPool.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void importData() {
        DatasetImporter datasetImporter = new DatasetImporter();
        try {
            datasetImporter.importDatasets();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayDistances() {
        CityDao cityDao = new CityDao();

        try {
            System.out.println("Distance from: " + cityDao.findById(1) + " and " + cityDao.findById(2) + " is: " + cityDao.findDistanceBetweenCountries(1, 2) + " kilometers");
            System.out.println("Distance from Bucharest to Baghdad is: " + cityDao.findDistanceBetweenCountries("Bucharest", "Baghdad") + " kilometers");
            System.out.println("Distance from: " + cityDao.findById(100) + " and " + cityDao.findById(1200) + " is: " + cityDao.findDistanceBetweenCountries(100, 1200) + " kilometers");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayMap(String[] args) {
        MapDisplayTool mapDisplay = new MapDisplayTool();
        mapDisplay.startDisplay(args);
    }

    public void createFakeCities() {
        FakeCityGenerator fakeCityGenerator = new FakeCityGenerator();
        try {
            fakeCityGenerator.generate(1000);
        } catch (SQLException e) {
            e.printStackTrace();
            Database.rollback();
        }
        System.out.println("Done creating");
    }

    public void findSisterhoodCliques() {
        CityRelationsGraph<City, CitySisterRelation> citiesGraph = new CityRelationsGraph<>();
        try {
            citiesGraph.initializeGraph();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.rollback();
        }

        BronKerboschCliqueFinderAlgorithm algorithm = new BronKerboschCliqueFinderAlgorithm(citiesGraph);
        algorithm.runAlgorithm();

        algorithm.printSolution();
    }

    public static void main(String[] args) {
        Main app = new Main();

        // app.printTest();
        // app.importData();
        app.displayDistances();
        app.displayMap(args);
        // app.conPoolTest();
        // app.createFakeCities();
        app.findSisterhoodCliques();
    }
}