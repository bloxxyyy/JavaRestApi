package nl.han.domainlayer.viewmodels;

public class PlaylistsViewModel {

    private PlaylistViewModel[] playlists;
    private int length;

    public PlaylistsViewModel() {}

    public PlaylistViewModel[] getPlaylists() {
        return playlists;
    }

    public void setPlaylists(PlaylistViewModel[] playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
