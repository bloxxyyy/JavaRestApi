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

    @Inject
    private SqlDatabase _database;

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
            e.printStackTrace();
        }

        return null;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Token token) {

    }

    @Override
    public List<Token> getAll() {
        return null;
    }

    @Override
    public List<Token> getAllBy(Token token) {
        return null;
    }
}
