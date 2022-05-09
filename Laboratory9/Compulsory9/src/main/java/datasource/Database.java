package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** A singleton class for managing database connections, using a regular Connection object. */
public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/cities2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private static Connection connection = null;

    private Database() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }

        return connection;
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
