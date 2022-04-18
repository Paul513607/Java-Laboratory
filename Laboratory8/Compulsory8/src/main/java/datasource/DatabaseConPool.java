package datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** A singleton class for managing database connections, using a regular ComboPooledDataSource to manage the connections to the database. */
public class DatabaseConPool {
    private static ComboPooledDataSource cpds = null;

    private DatabaseConPool() {
    }

    public static Connection getConnection() throws SQLException {
        if (cpds == null) {
            createDatasource();
        }

        return cpds.getConnection();
    }

    private static void createDatasource() throws SQLException {
        cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.postgresql.Driver"); // loads the jdbc driver
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl( "jdbc:postgresql://localhost:5432/cities" );
        cpds.setUser("postgres");
        cpds.setPassword("root");

        cpds.getConnection().setAutoCommit(false);
    }

    public static void closeConnection() throws SQLException {
        cpds.getConnection().close();
        cpds.close();
    }

    public static void rollback() {
        try {
            cpds.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
