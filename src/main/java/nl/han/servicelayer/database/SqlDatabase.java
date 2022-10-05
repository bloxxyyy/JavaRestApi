package nl.han.servicelayer.database;

import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlDatabase {

    private DatabaseProperties _databaseProperties;

    @Inject
    public SqlDatabase(DatabaseProperties databaseProperties) {
        _databaseProperties = databaseProperties;
    }

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        _databaseProperties = databaseProperties;
    }

    public Connection GetConnection() throws SQLException {
        try {
            return DriverManager.getConnection(_databaseProperties.connectionString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
