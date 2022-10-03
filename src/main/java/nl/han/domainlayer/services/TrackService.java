package nl.han.domainlayer.services;

import nl.han.domainlayer.viewmodels.TrackViewModel;
import nl.han.domainlayer.viewmodels.TracksViewModel;

public interface TrackService {
    TracksViewModel getTracksByPlaylist(int playlistId);

    TracksViewModel getTrackNotInPlaylist(int playlistId);

    void addTrackToPlaylist(TrackViewModel trackViewModel, int playlistId);

    void removeTrackFromPlaylist(int playlistId, int trackId);
}
