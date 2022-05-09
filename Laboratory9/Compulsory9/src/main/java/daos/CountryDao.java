package daos;

import abstractrepos.CountryRepo;
import datasource.Database;
import model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** The dao for accessing countries from the database. */
public class CountryDao implements GenericDao<Country>, CountryRepo {
    public void create(String name, Long continentId) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name, continent) VALUES (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setLong(2, continentId);
            pstmt.executeUpdate();
        }
    }

    public void create(String name, String code, Long continentId) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name, code, continent) VALUES (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, code);
            pstmt.setLong(3, continentId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void save(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void save(Country object) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name) VALUES (?)")) {
            pstmt.setString(1, object.getName());
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
                Long id = rs.getLong(1);
                String name = rs.getString(2);
                String code = rs.getString(3);
                Long continent = rs.getLong(4);
                Country countryFromTable = new Country(id, name, code, continent);
                countries.add(countryFromTable);
            }
        }
        return countries;
    }

    @Override
    public Optional<Country> findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM countries WHERE name='" + name + "'")) {
            rs.next();

            Long idRet = rs.getLong(1);
             String name1 = rs.getString(2);
             String code = rs.getString(3);
             Long cId = rs.getLong(4);
             return Optional.of(new Country(idRet, name1, code, cId));
        }
    }

    @Override
    public Optional<Country> findById(Long id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM countries WHERE id='" + id + "'")) {
            rs.next();

            Long idRet = rs.getLong(1);
            String name1 = rs.getString(2);
            String code = rs.getString(3);
            Long cId = rs.getLong(4);
            return Optional.of(new Country(idRet, name1, code, cId));
        }
    }

    public List<String> findCountriesOnContinent(Long continentId) throws SQLException {
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