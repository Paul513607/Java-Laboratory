package dao;

import java.sql.*;
import java.util.List;

/** The interface dao for accessing a generic map object that has a name and an id from the database. */
public interface GenericDao<T> {
    void create(String name) throws SQLException;
    List<T> findAll() throws SQLException;
    Integer findByName(String name) throws SQLException;
    String findById(int id) throws SQLException;
}
