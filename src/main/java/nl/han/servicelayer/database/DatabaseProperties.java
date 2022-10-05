package nl.han.servicelayer.database;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {
    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String connectionString()
    {
        return properties.getProperty("connectionString");
    }

}