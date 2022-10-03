package nl.han.domainlayer.services;

import nl.han.domainlayer.viewmodels.PlaylistViewModel;
import nl.han.domainlayer.viewmodels.PlaylistsViewModel;

public interface PlayListService {
    PlaylistsViewModel getPlaylistsWithTracks(String token);

    void deletePlaylistById(int playlistId);

    void addPlaylist(PlaylistViewModel playlistViewModel, String token);

    void updatePlaylist(PlaylistViewModel playlistViewModel, String token);
}
