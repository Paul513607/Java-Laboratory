package dao;

import datasource.Database;
import datasource.DatabaseConPool;
import model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** The dao for accessing countries from the database. */
public class CountryDao implements GenericDao<Country> {
    public void create(String name, int continentId) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name, continent) VALUES (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, continentId);
            pstmt.executeUpdate();
        }
    }

    public void create(String name, String code, int continentId) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name, code, continent) VALUES (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, code);
            pstmt.setInt(3, continentId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Country> findAll() throws SQLException {
        List<Country> countries = new ArrayList<>();

        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM countries")) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String code = rs.getString(3);
                int continent = rs.getInt(4);
                Country countryFromTable = new Country(id, name, code, continent);
                countries.add(countryFromTable);
            }
        }
        return countries;
    }

    @Override
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT id FROM countries WHERE name='" + name + "'")) {
             return rs.next() ? rs.getInt(1) : null;
        }
    }

    @Override
    public String findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM countries WHERE id='" + id + "'")) {
             return rs.next() ? rs.getString(1) : null;
        }
    }

    public List<String> findCountriesOnContinent(int continentId) throws SQLException {
        List<String> countriesOfContinent = new ArrayList<>();

        Connection con = Database.getConnection();
        // Connection con = DatabaseConPool.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM countries WHERE continent='" + continentId + "'")) {
            while (rs.next()) {
                countriesOfContinent.add(rs.getString(1));
            }
        }
        return countriesOfContinent;
    }
}