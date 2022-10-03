package nl.han.domainlayer.viewmodels;

public class PlaylistViewModel {

    private int id;
    private String name;
    private boolean owner;
    private TrackViewModel[] tracks;

    public PlaylistViewModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public TrackViewModel[] getTracks() {
        return tracks;
    }

    public void setTracks(TrackViewModel[] tracks) {
        this.tracks = tracks;
    }
}
