package nl.han.servicelayer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SqlDatabase {

    public Connection GetConnection() {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "pass");

        String db = "spotitube";
        String host = "jdbc:mysql://localhost:3306" + "/" + db;

        Connection conn;

        try {
            conn = DriverManager.getConnection(host, connectionProps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

}
