package daos;

import datasource.Database;
import model.CitySisterRelation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** The dao for accessing city sister relations from the database. */
public class CitySisterRelationDao {
    public void create(int id1, int id2) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO city_sister_relation (id1, id2) VALUES (?, ?)")) {
            pstmt.setInt(1, id1);
            pstmt.setInt(2, id2);
            pstmt.executeUpdate();
        }
    }

    public List<CitySisterRelation> findAll() throws SQLException {
        List<CitySisterRelation> citySisterRelations = new ArrayList<>();

        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM city_sister_relation")) {
            while (rs.next()) {
                int id1 = rs.getInt(1);
                int id2 = rs.getInt(2);
                CitySisterRelation relation = new CitySisterRelation(id1, id2);
                citySisterRelations.add(relation);
            }
        }
        return citySisterRelations;
    }

    public List<CitySisterRelation> findAllForId(int id) throws SQLException {
        List<CitySisterRelation> citySisterRelations = new ArrayList<>();

        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM city_sister_relation WHERE id1='" + id + "' OR id2='" + id + "'")) {
            while (rs.next()) {
                int id1 = rs.getInt(1);
                int id2 = rs.getInt(2);
                CitySisterRelation relation = new CitySisterRelation(id1, id2);
                citySisterRelations.add(relation);
            }
        }
        return citySisterRelations;
    }
}
