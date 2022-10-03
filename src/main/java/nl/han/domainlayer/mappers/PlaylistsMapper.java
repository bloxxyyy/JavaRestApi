package nl.han.domainlayer.mappers;

import jakarta.inject.Inject;
import nl.han.domainlayer.viewmodels.PlaylistViewModel;
import nl.han.domainlayer.viewmodels.PlaylistsViewModel;
import nl.han.domainlayer.viewmodels.TrackViewModel;
import nl.han.domainlayer.viewmodels.TracksViewModel;
import nl.han.servicelayer.Entities.Playlist;
import nl.han.servicelayer.Entities.Token;
import nl.han.servicelayer.daos.Dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlaylistsMapper {

    public int toTotalTimeInMinutes(PlaylistsViewModel playlistsViewModel) {
        var totalSeconds = 0;

        for (PlaylistViewModel playlist: playlistsViewModel.getPlaylists()) {
            for (TrackViewModel track: playlist.getTracks()) {
                totalSeconds += track.getDuration();
            }
        }

        return totalSeconds;
    }

    public PlaylistsViewModel toPlaylistsDto(List<Playlist> playlists, String username) {
        var playlistsViewModel = new PlaylistsViewModel();
        TransformPlaylists(playlists, playlistsViewModel, username);

        playlistsViewModel.setLength(toTotalTimeInMinutes(playlistsViewModel));

        return playlistsViewModel;
    }

    public TracksViewModel ToTracksDto(Playlist playlist) {
        var tracksViewModel = new TracksViewModel();
        var tracksList = TransformTracks(playlist);
        tracksViewModel.setTracks(tracksList.toArray(new TrackViewModel[0]));
        return tracksViewModel;
    }

    private void TransformPlaylists(List<Playlist> playlists, PlaylistsViewModel playlistsViewModel, String username) {
        ArrayList<PlaylistViewModel> playlistsList = new ArrayList<>();


        playlists.forEach(playlist -> {
            var owner = playlist.getOwner();
            var isOwner = Objects.equals(owner, username);
            var plvm = TransformPlaylist(playlist, isOwner);
            playlistsList.add(plvm);
        });

        playlistsViewModel.setPlaylists(playlistsList.toArray(new PlaylistViewModel[0]));
    }

    private PlaylistViewModel TransformPlaylist(Playlist playlist, boolean isOwner) {
        var playlistViewModel = new PlaylistViewModel();
        playlistViewModel.setId(playlist.getId());
        playlistViewModel.setName(playlist.getName());
        playlistViewModel.setOwner(isOwner);

        var tracksList = TransformTracks(playlist);
        playlistViewModel.setTracks(tracksList.toArray(new TrackViewModel[0]));
        return playlistViewModel;
    }

    private ArrayList<TrackViewModel> TransformTracks(Playlist playlist) {
        ArrayList<TrackViewModel> tracksList = new ArrayList<>();

        Arrays.stream(playlist.getTracks()).forEach(track -> {
            var trackViewModel = new TrackViewModel();
            trackViewModel.setId(track.getId());
            trackViewModel.setAlbum(track.getAlbum());
            trackViewModel.setDuration(track.getDuration());
            trackViewModel.setDescription(track.getDescription());
            trackViewModel.setPerformer(track.getPerformer());
            trackViewModel.setPlaycount(track.getPlaycount());
            trackViewModel.setTitle(track.getTitle());
            trackViewModel.setPublicationDate(track.getPublicationDate());
            trackViewModel.setOfflineAvailable(track.getOfflineAvailable());
            tracksList.add(trackViewModel);
        });

        return tracksList;
    }

    public Playlist ToPlaylistEntity(PlaylistViewModel playlistViewModel, Token tokenEntity) {
        return new Playlist(playlistViewModel.getId(), playlistViewModel.getName(), tokenEntity.GetUsername(), null);
    }
}
