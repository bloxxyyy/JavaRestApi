package nl.han.servicelayer.daos;

import jakarta.inject.Inject;
import nl.han.servicelayer.Entities.PlaylistTrack;
import nl.han.servicelayer.Entities.Track;
import nl.han.servicelayer.Entities.User;
import nl.han.servicelayer.database.SqlDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlaylistTrackDaoImpl implements Dao<PlaylistTrack> {

    @Inject
    private SqlDatabase _database;

    @Override
    public PlaylistTrack get(PlaylistTrack entity) {
        return null;
    }

    @Override
    public void add(PlaylistTrack entity) {
        String sql = "INSERT INTO playlisttrack (`trackId`, `playlistId`) VALUES (?, ?)";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, entity.getTrackId());
            pstmt.setInt(2, entity.getPlaylistId());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PlaylistTrack playlistTrack) {

    }

    @Override
    public void delete(PlaylistTrack playlistTrack) {
        String sql = "DELETE FROM playlisttrack WHERE playlistId = ? and trackid = ?";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playlistTrack.getPlaylistId());
            pstmt.setInt(2, playlistTrack.getTrackId());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PlaylistTrack> getAll() {
        return null;
    }

    @Override
    public List<PlaylistTrack> getAllBy(PlaylistTrack object) {
        String sql = "SELECT trackId FROM playlisttrack WHERE playlistId = ?";

        try {
            PreparedStatement pstmt;
            try (var conn = _database.GetConnection()) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,object.getPlaylistId());

                ResultSet rs = pstmt.executeQuery();

                ArrayList<PlaylistTrack> playlistTracks = new ArrayList<>();
                while (rs.next()) {
                    playlistTracks.add(new PlaylistTrack(rs.getInt("trackId"), object.getPlaylistId()));
                }

                return playlistTracks;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
