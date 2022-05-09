package daos;

import abstractrepos.CityRepo;
import datasource.Database;
import model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** The dao for accessing cities from the database. */
public class CityDao implements GenericDao<City>, CityRepo {
    public void save(String name, int countryId) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name, country) VALUES (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, countryId);
            pstmt.executeUpdate();
        }
    }

    public void save(String name, int countryId, boolean capital, double latitude, double longitude) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name, country, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, countryId);
            pstmt.setBoolean(3, capital);
            pstmt.setDouble(4, latitude);
            pstmt.setDouble(5, longitude);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void save(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void save(City object) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name) VALUES (?)")) {
            pstmt.setString(1, object.getName());
            pstmt.setLong(2, object.getCountryId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<City> findAll() throws SQLException {
        List<City> cities = new ArrayList<>();

        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM cities")) {
            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                long country = rs.getLong(3);
                boolean capital = rs.getBoolean(4);
                double latitude = rs.getDouble(5);
                double longitude = rs.getDouble(6);
                long population = rs.getLong(7);
                City cityFromTable = new City(id, name, country, capital, latitude, longitude, population);
                cities.add(cityFromTable);
            }
        }
        return cities;
    }

    @Override
    public Optional<City> findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM cities WHERE name='" + name + "'")) {
            rs.next();

            long idRet = rs.getLong(1);
            String name1 = rs.getString(2);
            long country = rs.getLong(3);
            boolean capital = rs.getBoolean(4);
            double latitude = rs.getDouble(5);
            double longitude = rs.getDouble(6);
            long population = rs.getLong(7);
            return Optional.of(new City(idRet, name1, country, capital, latitude, longitude, population));
        }
    }

    @Override
    public Optional<City> findById(Long id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM cities WHERE id='" + id + "'")) {
            rs.next();

            long idRet = rs.getLong(1);
            String name = rs.getString(2);
            long country = rs.getLong(3);
            boolean capital = rs.getBoolean(4);
            double latitude = rs.getDouble(5);
            double longitude = rs.getDouble(6);
            long population = rs.getLong(7);
            return Optional.of(new City(idRet, name, country, capital, latitude, longitude, population));
        }
    }

    public City findCityById(Long id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM cities WHERE id='" + id + "'")) {
            if (!rs.next())
                return null;

            long idRet = rs.getLong(1);
            String name = rs.getString(2);
            long country = rs.getLong(3);
            boolean capital = rs.getBoolean(4);
            double latitude = rs.getDouble(5);
            double longitude = rs.getDouble(6);
            long population = rs.getLong(7);
            return new City(idRet, name, country, capital, latitude, longitude, population);
        }
    }

    public double findDistanceBetweenCountries(String countryName1, String countryName2) throws SQLException {
        Long countryId1 = findByName(countryName1).get().getId();
        Long countryId2 = findByName(countryName2).get().getId();

        return findDistanceBetweenCountries(countryId1, countryId2);
    }

    public double findDistanceBetweenCountries(Long countryId1, Long countryId2) throws SQLException {
        double lat1, lat2;
        double long1, long2;

        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT latitude, longitude FROM cities WHERE id='" + countryId1 + "'")) {
            lat1 = rs.next() ? rs.getInt(1) : 0;
            long1 = rs.next() ? rs.getInt(2) : 0;
        }

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT latitude, longitude FROM cities WHERE id='" + countryId2 + "'")) {
            lat2 = rs.next() ? rs.getInt(1) : 0;
            long2 = rs.next() ? rs.getInt(2) : 0;
        }

        lat1 = Math.toRadians(lat1);
        long1 = Math.toRadians(long1);
        lat2 = Math.toRadians(lat2);
        long2 = Math.toRadians(long2);

        double dLon = long2 - long1;
        double dLat = lat2 - lat1;
        double firstOpRes = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLon / 2),2);
        double secondOpRes = 2 * Math.asin(Math.sqrt(firstOpRes));

        // Radius of earth in kilometers.
        double radius = 6371;

        return secondOpRes * radius;
    }
}