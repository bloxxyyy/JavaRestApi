package nl.han.servicelayer.daos;
import jakarta.inject.Inject;
import nl.han.servicelayer.Entities.User;
import nl.han.servicelayer.database.SqlDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImpl implements Dao<User> {

    @Inject
    private SqlDatabase _database;

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM user";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            var users = new ArrayList<User>();
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("password")));
            }
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllBy(User object) {
        String sql = "SELECT u.username, u.password FROM spotitube.user as u WHERE u.username = ? AND u.password = ? LIMIT 1";

        try {
            PreparedStatement pstmt;
            try (var conn = _database.GetConnection()) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,object.GetUsername());
                pstmt.setString(2,object.GetPassword());

                ResultSet rs = pstmt.executeQuery();

                ArrayList<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(new User(rs.getString("username"), rs.getString("password")));
                }

                return users;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User get(User object) {
        String sql = "SELECT u.username, u.password FROM spotitube.user as u WHERE u.username = ? AND u.password = ? LIMIT 1";

        try {
            PreparedStatement pstmt;
            try (var conn = _database.GetConnection()) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,object.GetUsername());
                pstmt.setString(2,object.GetPassword());

                ResultSet rs = pstmt.executeQuery();

                User user = null;
                while (rs.next()) {
                    user = new User(rs.getString("username"), rs.getString("password"));
                }

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(User object) {
        String sql = "UPDATE user SET `username` = ?, `password` = ? WHERE `username` = ?";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, object.GetUsername());
            pstmt.setString(2, object.GetPassword());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User object) {

    }

    @Override
    public void add(User object) {

    }
}
