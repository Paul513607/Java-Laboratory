package dao;

import datasource.Database;
import datasource.DatabaseConPool;
import model.Continent;
import model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** The dao for accessing continents from the database. */
public class ContinentDao implements GenericDao<Continent> {
    @Override
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO continents (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Continent> findAll() throws SQLException {
        List<Continent> continents = new ArrayList<>();

        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM continents")) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Continent continentFromTable = new Continent(id, name);
                continents.add(continentFromTable);
            }
        }
        return continents;
    }

    @Override
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        // Connection con = DatabaseConPool.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT id FROM continents WHERE name='" + name + "'")) {
             return rs.next() ? rs.getInt(1) : null;
        }
    }

    @Override
    public String findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM continents WHERE id='" + id + "'")) {
             return rs.next() ? rs.getString(1) : null;
        }
    }
}