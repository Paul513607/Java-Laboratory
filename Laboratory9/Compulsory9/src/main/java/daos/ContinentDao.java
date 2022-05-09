package daos;

import abstractrepos.ContinentRepo;
import datasource.Database;
import model.Continent;
import model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** The dao for accessing continents from the database. */
public class ContinentDao implements GenericDao<Continent>, ContinentRepo {
    public void save(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO continents (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void save(Continent object) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO continents (name) VALUES (?)")) {
            pstmt.setString(1, object.getName());
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
                Long id = rs.getLong(1);
                String name = rs.getString(2);
                Continent continentFromTable = new Continent(id, name);
                continents.add(continentFromTable);
            }
        }
        return continents;
    }

    @Override
    public Optional<Continent> findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM continents WHERE name='" + name + "'")) {
            rs.next();
             long idRet = rs.getLong(1);
             String name1 = rs.getString(2);
             return Optional.of(new Continent(idRet, name1));
        }
    }

    @Override
    public Optional<Continent> findById(Long id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM continents WHERE id='" + id + "'")) {
            rs.next();

            long idRet = rs.getLong(1);
            String name1 = rs.getString(2);
            return Optional.of(new Continent(idRet, name1));
        }
    }
}