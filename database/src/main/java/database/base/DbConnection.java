package database.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import database.properties.DbProperties;

/**
 * Singleton class for PostgreSQL JDBC connection.
 */
public class DbConnection {

    private static volatile DbConnection instance;
    private static final DbProperties dbProperties = new DbProperties();
    private static final String URL = "jdbc:postgresql://%s:%s/%s"
            .formatted(dbProperties.getDbHost(), dbProperties.getDbPort(), dbProperties.getDbName());

    private DbConnection() {
    }

    public static DbConnection getInstance() {
        if (instance == null) {
            synchronized (DbConnection.class) {
                if (instance == null) {
                    instance = new DbConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, dbProperties.getDbUser(), dbProperties.getDbPassword());
    }
}