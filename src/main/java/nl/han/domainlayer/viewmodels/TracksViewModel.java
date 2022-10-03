package nl.han.domainlayer.viewmodels;

public class TracksViewModel {
    private TrackViewModel[] tracks;

    public TracksViewModel() {}

    public TrackViewModel[] getTracks() {
        return tracks;
    }

    public void setTracks(TrackViewModel[] tracks) {
        this.tracks = tracks;
    }
}
