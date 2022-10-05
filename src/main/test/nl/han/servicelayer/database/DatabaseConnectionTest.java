package nl.han.servicelayer.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class DatabaseConnectionTest {

    private DatabaseProperties _databaseProperties = Mockito.mock(DatabaseProperties.class);
    private SqlDatabase _db;

    @Test
    public void GetConnectionTest() {
        //Arrange
        when(_databaseProperties.connectionString()).thenReturn(null);
        _db = new SqlDatabase(_databaseProperties);

        //Act
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
           _db.GetConnection();
        });

        //Assert
        String expectedMessage = "java.sql.SQLException: The url cannot be null";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
