package nl.han.domainlayer.services;

import nl.han.domainlayer.mappers.PlaylistsMapper;
import nl.han.domainlayer.viewmodels.TrackViewModel;
import nl.han.servicelayer.Entities.Playlist;
import nl.han.servicelayer.Entities.PlaylistTrack;
import nl.han.servicelayer.Entities.Track;
import nl.han.servicelayer.daos.PlaylistDaoImpl;
import nl.han.servicelayer.daos.PlaylistTrackDaoImpl;
import nl.han.servicelayer.daos.TrackDaoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TrackServiceTest {

    private PlaylistsMapper _mapper = Mockito.spy(PlaylistsMapper.class);
    private PlaylistDaoImpl _playlistDao = Mockito.mock(PlaylistDaoImpl.class);
    private TrackDaoImpl _trackDao = Mockito.mock(TrackDaoImpl.class);
    private PlaylistTrackDaoImpl _playlistTrackDao = Mockito.mock(PlaylistTrackDaoImpl.class);

    private TrackServiceImpl _ps;

    @BeforeEach
    public void update() {
        _ps = new TrackServiceImpl(_playlistDao, _trackDao, _playlistTrackDao, _mapper);
    }

    @Test
    public void getTracksByPlaylistTest() {
        //Arrange
        String title = "Title";
        Playlist playlist = new Playlist(1, "user", "owner", null);
        Track track = new Track(1, title, "owner", 0, "", 0, "", "", false);

        var playlistTrackList = new ArrayList<>();
        PlaylistTrack playlistTrack = new PlaylistTrack(1, 1);
        playlistTrackList.add(playlistTrack);
        var pltl = playlistTrackList.toArray(new PlaylistTrack[0]);

        when(_playlistDao.get(any(Playlist.class))).thenReturn(playlist);
        when(_playlistTrackDao.getAllBy(any(PlaylistTrack.class))).thenReturn(List.of(pltl));
        when(_trackDao.get(any(Track.class))).thenReturn(track);

        //Act
        var gottenPlaylists = _ps.getTracksByPlaylist(1);

        //Assert
        Assertions.assertEquals(gottenPlaylists.getTracks().length, 1);
        Assertions.assertEquals(gottenPlaylists.getTracks()[0].getTitle(), title);
    }

    @Test
    public void addTrackFromPlaylistTest() {
        final Track[] trackToUpdate = {new Track(1, null, null, -1, null, -1, null, null, false)};
        ArrayList<PlaylistTrack> plts = new ArrayList<>();

        var viewModel = new TrackViewModel();
        viewModel.setId(1);
        viewModel.setOfflineAvailable(true);

        //Arrange
        TrackDaoImpl TrackDao = new TrackDaoImpl()  {
            @Override
            public Track get(Track track) {return track;}

            @Override
            public void update(Track track) {
                trackToUpdate[0] = track;
            }
        };

        PlaylistTrackDaoImpl playlistTrackDao = new PlaylistTrackDaoImpl()  {
            @Override
            public void add(PlaylistTrack playlistTrack) {
                plts.add(playlistTrack);
            }
        };

        _ps = new TrackServiceImpl(_playlistDao, TrackDao, playlistTrackDao, _mapper);

        //Act
        _ps.addTrackToPlaylist(viewModel, 1);

        //Assert
        Assertions.assertEquals(1, plts.size());
        Assertions.assertTrue(trackToUpdate[0].getOfflineAvailable());
    }

    @Test
    public void removeTrackFromPlaylistTest() {
        //Arrange
        var playlistTracks = new ArrayList<PlaylistTrack>();
        var playlistTrackToRemove = new PlaylistTrack(1, 1);
        playlistTracks.add(playlistTrackToRemove);

        PlaylistTrackDaoImpl playlistTrackDao = new PlaylistTrackDaoImpl()  {
            @Override
            public void delete(PlaylistTrack playlistTrack) {
                playlistTracks.remove(playlistTrackToRemove);
            }
        };

        _ps = new TrackServiceImpl(_playlistDao, _trackDao, playlistTrackDao, _mapper);

        //Act
        _ps.removeTrackFromPlaylist(playlistTrackToRemove.getPlaylistId(), playlistTrackToRemove.getTrackId());

        //Assert
        Assertions.assertEquals(playlistTracks.size(), 0);
    }

    @Test
    public void getTrackNotInPlaylistTest() {
        //Arrange
        String title = "Title";
        Playlist playlist = new Playlist(1, "user", "owner", null);
        Track track = new Track(1, title, "owner", 0, "", 0, "", "", false);

        var playlistTrackList = new ArrayList<>();
        PlaylistTrack playlistTrack = new PlaylistTrack(1, 1);
        playlistTrackList.add(playlistTrack);
        var pltl = playlistTrackList.toArray(new PlaylistTrack[0]);

        when(_playlistDao.get(any(Playlist.class))).thenReturn(playlist);
        when(_playlistTrackDao.getAllBy(any(PlaylistTrack.class))).thenReturn(List.of(pltl));
        when(_trackDao.get(any(Track.class))).thenReturn(track);

        //Act
        var gottenPlaylists = _ps.getTrackNotInPlaylist(1);

        //Assert
        Assertions.assertEquals(gottenPlaylists.getTracks().length, 0);
    }
}
