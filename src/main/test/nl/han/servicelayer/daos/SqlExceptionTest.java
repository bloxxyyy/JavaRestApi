package nl.han.servicelayer.daos;

import nl.han.servicelayer.Entities.*;
import nl.han.servicelayer.database.DatabaseProperties;
import nl.han.servicelayer.database.SqlDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.Mockito.when;

public class SqlExceptionTest {

    private LoginDAOImpl _loginDao;
    private PlaylistDaoImpl _playlistDao;
    private PlaylistTrackDaoImpl _playlistTrackDao;
    private TokenDAOImpl _tokenDao;
    private TrackDaoImpl _trackDao;

    private static DatabaseProperties _databaseProperties = Mockito.mock(DatabaseProperties.class);
    private static SqlDatabase db;

    @BeforeAll
    public static void setup() {
        db = new SqlDatabase(_databaseProperties) {
            @Override
            public void setDatabaseProperties(DatabaseProperties databaseProperties) {
                super.setDatabaseProperties(databaseProperties);
            }
        };

        when(_databaseProperties.connectionString()).thenReturn("empty");
        db.setDatabaseProperties(_databaseProperties);
    }

    @Test
    public void loginMethodsThrowSqlExceptionTest() {
        //Arrange
        String expectedMessage = "java.sql.SQLException: No suitable driver found for empty";
        _loginDao = new LoginDAOImpl();
        _loginDao.SetDatabase(db);

        //Act
        Exception exception1 = Assertions.assertThrows(RuntimeException.class, () -> _loginDao.get(new User("", "")));
        Exception exception2 = Assertions.assertThrows(RuntimeException.class, () -> _loginDao.getAll());
        Exception exception3 = Assertions.assertThrows(RuntimeException.class, () -> _loginDao.getAllBy(new User("", "")));
        Exception exception4 = Assertions.assertThrows(RuntimeException.class, () -> _loginDao.update(new User("", "")));
        Exception exception5 = Assertions.assertThrows(RuntimeException.class, () -> _loginDao.add(new User("", "")));
        Exception exception6 = Assertions.assertThrows(RuntimeException.class, () -> _loginDao.delete(new User("", "")));

        //Assert
        Assert(expectedMessage, exception1, exception2, exception3, exception4, exception5, exception6);
    }

    @Test
    public void playlistMethodsThrowSqlExceptionTest() {
        //Arrange
        String expectedMessage = "java.sql.SQLException: No suitable driver found for empty";
        _playlistDao = new PlaylistDaoImpl();
        _playlistDao.SetDatabase(db);
        var playlist = new Playlist(0, null, null, null);

        //Act
        Exception exception1 = Assertions.assertThrows(RuntimeException.class, () -> _playlistDao.get(playlist));
        Exception exception2 = Assertions.assertThrows(RuntimeException.class, () -> _playlistDao.getAll());
        Exception exception3 = Assertions.assertThrows(RuntimeException.class, () -> _playlistDao.getAllBy(playlist));
        Exception exception4 = Assertions.assertThrows(RuntimeException.class, () -> _playlistDao.update(playlist));
        Exception exception5 = Assertions.assertThrows(RuntimeException.class, () -> _playlistDao.add(playlist));
        Exception exception6 = Assertions.assertThrows(RuntimeException.class, () -> _playlistDao.delete(playlist));

        //Assert
        Assert(expectedMessage, exception1, exception2, exception3, exception4, exception5, exception6);
    }

    @Test
    public void playlistTrackMethodsThrowSqlExceptionTest() {
        //Arrange
        String expectedMessage = "java.sql.SQLException: No suitable driver found for empty";
        _playlistTrackDao = new PlaylistTrackDaoImpl();
        _playlistTrackDao.SetDatabase(db);
        var playlist = new PlaylistTrack(0, 0);

        //Act
        Exception exception1 = Assertions.assertThrows(RuntimeException.class, () -> _playlistTrackDao.get(playlist));
        Exception exception2 = Assertions.assertThrows(RuntimeException.class, () -> _playlistTrackDao.getAll());
        Exception exception3 = Assertions.assertThrows(RuntimeException.class, () -> _playlistTrackDao.getAllBy(playlist));
        Exception exception4 = Assertions.assertThrows(RuntimeException.class, () -> _playlistTrackDao.update(playlist));
        Exception exception5 = Assertions.assertThrows(RuntimeException.class, () -> _playlistTrackDao.add(playlist));
        Exception exception6 = Assertions.assertThrows(RuntimeException.class, () -> _playlistTrackDao.delete(playlist));

        //Assert
        Assert(expectedMessage, exception1, exception2, exception3, exception4, exception5, exception6);
    }

    @Test
    public void trackMethodsThrowSqlExceptionTest() {
        //Arrange
        String expectedMessage = "java.sql.SQLException: No suitable driver found for empty";
        _trackDao = new TrackDaoImpl();
        _trackDao.SetDatabase(db);
        var track = new Track(0, null, null, 0, null, 0, null, null, false);

        //Act
        Exception exception1 = Assertions.assertThrows(RuntimeException.class, () -> _trackDao.get(track));
        Exception exception2 = Assertions.assertThrows(RuntimeException.class, () -> _trackDao.getAll());
        Exception exception3 = Assertions.assertThrows(RuntimeException.class, () -> _trackDao.getAllBy(track));
        Exception exception4 = Assertions.assertThrows(RuntimeException.class, () -> _trackDao.update(track));
        Exception exception5 = Assertions.assertThrows(RuntimeException.class, () -> _trackDao.add(track));
        Exception exception6 = Assertions.assertThrows(RuntimeException.class, () -> _trackDao.delete(track));

        //Assert
        Assert(expectedMessage, exception1, exception2, exception3, exception4, exception5, exception6);
    }

    @Test
    public void tokenMethodsThrowSqlExceptionTest() {
        //Arrange
        String expectedMessage = "java.sql.SQLException: No suitable driver found for empty";
        _tokenDao = new TokenDAOImpl();
        _tokenDao.SetDatabase(db);
        var token = new Token(null, null);

        //Act
        Exception exception1 = Assertions.assertThrows(RuntimeException.class, () -> _tokenDao.get(token));
        Exception exception2 = Assertions.assertThrows(RuntimeException.class, () -> _tokenDao.getAll());
        Exception exception3 = Assertions.assertThrows(RuntimeException.class, () -> _tokenDao.getAllBy(token));
        Exception exception4 = Assertions.assertThrows(RuntimeException.class, () -> _tokenDao.update(token));
        Exception exception5 = Assertions.assertThrows(RuntimeException.class, () -> _tokenDao.add(token));
        Exception exception6 = Assertions.assertThrows(RuntimeException.class, () -> _tokenDao.delete(token));

        //Assert
        Assert(expectedMessage, exception1, exception2, exception3, exception4, exception5, exception6);
    }

    private static void Assert(String expectedMessage, Exception exception1, Exception exception2, Exception exception3, Exception exception4, Exception exception5, Exception exception6) {
        for (Exception e : Arrays.asList(exception1, exception2, exception3, exception4, exception5, exception6)) {
            Assertions.assertTrue(e.getMessage().contains(expectedMessage));
        }
    }
}
