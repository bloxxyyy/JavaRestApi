package nl.han.servicelayer.Entities;

public class PlaylistTrack {

    private int trackId;
    private int playlistId;


    public PlaylistTrack(int trackId, int playlistId) {
        this.trackId = trackId;
        this.playlistId = playlistId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }
}
