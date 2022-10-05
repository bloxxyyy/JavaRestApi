package nl.han.servicelayer.daos;
import jakarta.inject.Inject;
import nl.han.servicelayer.Entities.Token;
import nl.han.servicelayer.Entities.User;
import nl.han.servicelayer.database.SqlDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TokenDAOImpl implements Dao<Token> {

    private SqlDatabase _database;

    @Inject
    public void SetDatabase(SqlDatabase database) {
        _database = database;
    }

    @Override
    public Token get(Token entity) {
        String sql = "SELECT t.idToken, t.username FROM spotitube.token as t WHERE t.idToken = ? LIMIT 1";

        try {
            PreparedStatement pstmt;
            try (var conn = _database.GetConnection()) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, entity.GetToken());

                ResultSet rs = pstmt.executeQuery();

                Token token = null;
                while (rs.next()) {
                    token = new Token(rs.getString("username"), rs.getString("idToken"));
                }

                return token;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void add(Token token) {
        String sql = "INSERT INTO token (`idToken`, `username`) VALUES (?, ?)";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, token.GetToken());
            pstmt.setString(2, token.GetUsername());
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Token token) {
        String sql = "UPDATE token SET `idToken` = ? WHERE `username` = ?";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, token.GetToken());
            pstmt.setString(2, token.GetUsername());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(Token token) {
        try (var conn = _database.GetConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Token> getAll() {
        try (var conn = _database.GetConnection()) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Token> getAllBy(Token token) {
        try (var conn = _database.GetConnection()) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
