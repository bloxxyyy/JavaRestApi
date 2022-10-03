package nl.han.servicelayer.daos;

import jakarta.inject.Inject;
import nl.han.servicelayer.Entities.Playlist;
import nl.han.servicelayer.database.SqlDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDaoImpl implements Dao<Playlist> {

    @Inject
    private SqlDatabase _database;

    @Override
    public Playlist get(Playlist entity) {
        String sql = "SELECT * FROM playlist WHERE idPlaylist = ? LIMIT 1";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, entity.getId());
            ResultSet rs = pstmt.executeQuery();

            Playlist playlist = null;
            while (rs.next()) {
                playlist = new Playlist(rs.getInt("idPlaylist"), rs.getString("name"), rs.getString("owner"), null);
            }
            return playlist;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void add(Playlist playlist) {
        String sql = "INSERT INTO playlist (`name`, `owner`) VALUES (?, ?)";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playlist.getName());
            pstmt.setString(2, playlist.getOwner());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Playlist playlist) {
        String sql = "UPDATE playlist SET `name` = ?, `owner` = ? WHERE `idPlaylist` = ?";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playlist.getName());
            pstmt.setString(2, playlist.getOwner());
            pstmt.setInt(3, playlist.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Playlist playlist) {
        String sql = "DELETE FROM playlist WHERE idPlaylist = ?";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playlist.getId());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Playlist> getAll() {
        String sql = "SELECT * FROM playlist";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            var playlists = new ArrayList<Playlist>();
            while (rs.next()) {
                playlists.add(new Playlist(rs.getInt("idPlaylist"), rs.getString("name"), rs.getString("owner"), null));
            }
            return playlists;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Playlist> getAllBy(Playlist playlist) {
        return new ArrayList<>();
    }
}
