package util;

import com.github.javafaker.Faker;
import dao.CityDao;
import dao.CitySisterRelationDao;
import dao.ContinentDao;
import dao.CountryDao;
import datasource.Database;
import model.City;
import model.CitySisterRelation;
import model.Continent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** A class that generates a number of fake cities, and random sister relations between them, adding them to the database. */
public class FakeCityGenerator {
    public static final double SISTERHOOD_PROB = 0.1;
    List<Integer> fakeCountryIds = new ArrayList<>();

    public FakeCityGenerator() {
    }

    public void generate() throws SQLException {
        ContinentDao continentDao = new ContinentDao();
        continentDao.create("Imagination Continent");
        Database.getConnection().commit();

        int continentId = continentDao.findByName("Imagination Continent");

        CountryDao countryDao = new CountryDao();
        countryDao.create("Imagination Land");
        Database.getConnection().commit();

        int countryId = countryDao.findByName("Imagination Land");
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1000);

        CityDao cityDao = new CityDao();
        Faker faker = new Faker();
        for (int generateCount = 0; generateCount < 1000; ++generateCount) {
            final String name = "FakeCity_" + (generateCount + 1);
            executor.submit(() -> {
                try {
                    addNewCity(name, cityDao);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        Random random = new Random();
        CitySisterRelationDao relationDao = new CitySisterRelationDao();
        for (int cityIndex1 = 0; cityIndex1 < 1000; ++cityIndex1) {
            for (int cityIndex2 = 0; cityIndex2 < 1000; ++cityIndex2) {
                int finalCityIndex = cityIndex1;
                int finalCityIndex1 = cityIndex2;
                executor.submit(() -> {
                    try {
                        addRelation(random, relationDao, finalCityIndex, finalCityIndex1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        executor.shutdown();
    }

    private synchronized void addNewCity(String name, CityDao cityDao) throws SQLException {
        cityDao.create(name);
        Database.getConnection().commit();

        fakeCountryIds.add(cityDao.findByName(name));
    }

    private synchronized void addRelation(Random random, CitySisterRelationDao relationDao, int cityIndex1, int cityIndex2) throws SQLException {
        double prob = random.nextDouble(0, 1);
        if (prob < SISTERHOOD_PROB) {
            relationDao.create(fakeCountryIds.get(cityIndex1), fakeCountryIds.get(cityIndex2));
            Database.getConnection().commit();
        }
    }
}
