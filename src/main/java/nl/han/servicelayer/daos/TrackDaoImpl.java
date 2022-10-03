package nl.han.servicelayer.daos;

import jakarta.inject.Inject;
import nl.han.servicelayer.Entities.Playlist;
import nl.han.servicelayer.Entities.Track;
import nl.han.servicelayer.Entities.User;
import nl.han.servicelayer.database.SqlDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrackDaoImpl implements Dao<Track> {

    @Inject
    private SqlDatabase _database;


    @Override
    public Track get(Track object) {
        String sql = "SELECT * FROM track WHERE idTrack = ?";

        try {
            PreparedStatement pstmt;
            try (var conn = _database.GetConnection()) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,object.getId());

                ResultSet rs = pstmt.executeQuery();

                Track track = null;
                while (rs.next()) {
                    track = new Track(
                            rs.getInt("idTrack"),
                            rs.getString("title"),
                            rs.getString("performer"),
                            rs.getInt("duration"),
                            rs.getString("album"),
                            rs.getInt("playcount"),
                            rs.getString("publicationDate"),
                            rs.getString("description"),
                            rs.getBoolean("offlineAvailable")
                    );
                }

                return track;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void add(Track track) {

    }

    @Override
    public void update(Track track) {
        String sql = "UPDATE track SET `title` = ?, `performer` = ?, `duration` = ?, `album` = ?, `playcount` = ?, `publicationDate` = ?, `description` = ?, `offlineAvailable` = ? WHERE idTrack = ?";

        var offline = (track.getOfflineAvailable()) ? 1 : 0;

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, track.getTitle());
            pstmt.setString(2, track.getPerformer());
            pstmt.setInt(3, track.getDuration());
            pstmt.setString(4, track.getAlbum());
            pstmt.setInt(5, track.getPlaycount());
            pstmt.setString(6, track.getPublicationDate());
            pstmt.setString(7, track.getDescription());
            pstmt.setInt(8, offline);
            pstmt.setInt(9, track.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Track track) {

    }

    @Override
    public List<Track> getAll() {
        String sql = "SELECT * FROM track";

        try (var conn = _database.GetConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            var tracks = new ArrayList<Track>();
            while (rs.next()) {
                tracks.add(new Track(
                    rs.getInt("idTrack"),
                    rs.getString("title"),
                    rs.getString("performer"),
                    rs.getInt("duration"),
                    rs.getString("album"),
                    rs.getInt("playcount"),
                    rs.getString("publicationDate"),
                    rs.getString("description"),
                    rs.getBoolean("offlineAvailable")
                ));
            }
            return tracks;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Track> getAllBy(Track track) {
        return null;
    }
}
