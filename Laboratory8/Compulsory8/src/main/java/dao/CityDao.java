package dao;

import datasource.Database;
import model.City;
import model.Continent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** The dao for accessing cities from the database. */
public class CityDao implements GenericDao<City> {
    public void create(String name, int countryId) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name, country) VALUES (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, countryId);
            pstmt.executeUpdate();
        }
    }

    public void create(String name, int countryId, boolean capital, double latitude, double longitude) throws SQLException {
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
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name) VALUES (?)")) {
            pstmt.setString(1, name);
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
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int country = rs.getInt(3);
                boolean capital = rs.getBoolean(4);
                double latitude = rs.getDouble(5);
                double longitude = rs.getDouble(6);
                City cityFromTable = new City(id, name, country, capital, latitude, longitude);
                cities.add(cityFromTable);
            }
        }
        return cities;
    }

    @Override
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT id FROM cities WHERE name='" + name + "'")) {
             return rs.next() ? rs.getInt(1) : null;
        }
    }

    @Override
    public String findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM cities WHERE id='" + id + "'")) {
             return rs.next() ? rs.getString(1) : null;
        }
    }

    public City findCityById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM cities WHERE id='" + id + "'")) {
            if (!rs.next())
                return null;

            int idRet = rs.getInt(1);
            String name = rs.getString(2);
            int country = rs.getInt(3);
            boolean capital = rs.getBoolean(4);
            double latitude = rs.getDouble(5);
            double longitude = rs.getDouble(6);
            return new City(idRet, name, country, capital, latitude, longitude);
        }
    }

    public double findDistanceBetweenCountries(String countryName1, String countryName2) throws SQLException {
        int countryId1 = findByName(countryName1);
        int countryId2 = findByName(countryName2);

        return findDistanceBetweenCountries(countryId1, countryId2);
    }

    public double findDistanceBetweenCountries(int countryId1, int countryId2) throws SQLException {
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