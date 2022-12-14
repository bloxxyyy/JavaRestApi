package nl.han.domainlayer.services;

import nl.han.domainlayer.mappers.PlaylistsMapper;
import nl.han.domainlayer.viewmodels.PlaylistViewModel;
import nl.han.domainlayer.viewmodels.TrackViewModel;
import nl.han.servicelayer.Entities.*;
import nl.han.servicelayer.daos.*;
import nl.han.servicelayer.database.DatabaseProperties;
import nl.han.servicelayer.database.SqlDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PlaylistServiceTest {

    private PlaylistsMapper _mapper = Mockito.spy(PlaylistsMapper.class);
    private PlaylistDaoImpl _playlistDao = Mockito.mock(PlaylistDaoImpl.class);
    private TrackDaoImpl _trackDao = Mockito.mock(TrackDaoImpl.class);
    private PlaylistTrackDaoImpl _playlistTrackDao = Mockito.mock(PlaylistTrackDaoImpl.class);
    private TokenDAOImpl _tokenDao = Mockito.mock(TokenDAOImpl.class);

    private PlaylistsServiceImpl _ps;

    @BeforeEach
    public void update() {
        _ps = new PlaylistsServiceImpl(_playlistDao, _trackDao, _playlistTrackDao, _tokenDao, _mapper);
    }

    @Test
    public void getAllPlaylistsTest() {
        //Arrange
        String token = "1234";

        PlaylistViewModel toTest = new PlaylistViewModel();
        toTest.setId(1);
        toTest.setName("test");
        toTest.setOwner(true);
        toTest.setTracks(new TrackViewModel[0]);

        List<Playlist> playlists = List.of(new Playlist[]{new Playlist(1, "test", "owner", new Track[0])});

        when(_tokenDao.get(any(Token.class))).thenReturn(new Token("owner", token));
        when(_playlistDao.getAll()).thenReturn(playlists);

        //Act
        var gottenPlaylists = _ps.getPlaylistsWithTracks(token);
        var playlist = gottenPlaylists.getPlaylists()[0];

        //Assert
        Assertions.assertEquals(playlist.getId(), toTest.getId());
        Assertions.assertEquals(playlist.getName(), toTest.getName());
        Assertions.assertEquals(playlist.getTracks().length, toTest.getTracks().length);
        Assertions.assertEquals(playlist.getOwner(), toTest.getOwner());
    }

    @Test
    public void getTracksTest() {
        //Arrange
        _ps = new PlaylistsServiceImpl(_playlistDao, _trackDao, _playlistTrackDao, _tokenDao, _mapper);
        Playlist playlist = new Playlist(1, "test", "", null);

        when(_playlistTrackDao.getAllBy(any(PlaylistTrack.class))).thenReturn(List.of(new PlaylistTrack[] {new PlaylistTrack(1, 1)}));
        when(_trackDao.get(any(Track.class))).thenReturn(new Track(1, "title", "performer", 0, "album", 0, "date", "desc", false));

        //Act
        _ps.GetTracksByPlaylist(1, playlist);

        //Assert
        Assertions.assertEquals(playlist.getTracks()[0].getId(), 1);
        Assertions.assertEquals(playlist.getTracks()[0].getTitle(), "title");
    }

    @Test
    public void AddTrackToPlaylistTest() {
        //Arrange
        String token = "1234";
        PlaylistViewModel toTest = new PlaylistViewModel();
        toTest.setId(1);
        toTest.setName("test");
        toTest.setOwner(true);
        toTest.setTracks(new TrackViewModel[0]);

        when(_tokenDao.get(any(Token.class))).thenReturn(new Token("owner", token));

        var tracks = new ArrayList<Track>();

        PlaylistDaoImpl playlistDao = new PlaylistDaoImpl()  {
            @Override
            public void add(Playlist playlist) {
                var track = new Track(1, "", "", 0, "", 0, "", "", false);
                tracks.add(track);
            }
        };

        _ps = new PlaylistsServiceImpl(playlistDao, _trackDao, _playlistTrackDao, _tokenDao, _mapper);

        //Act
        _ps.addPlaylist(toTest, token);

        //Assert
        Assertions.assertEquals(tracks.size(), 1);
    }

    @Test
    public void UpdatePlaylistTest() {
        //Arrange
        String token = "1234";
        PlaylistViewModel toTest = new PlaylistViewModel();
        toTest.setId(1);
        toTest.setName("test");
        toTest.setOwner(true);
        toTest.setTracks(new TrackViewModel[0]);

        String ToTestName = "ToSet";
        boolean ToTestOwner = true;

        when(_tokenDao.get(any(Token.class))).thenReturn(new Token("owner", token));

        PlaylistDaoImpl playlistDao = new PlaylistDaoImpl()  {
            @Override
            public void update(Playlist playlist) {
                toTest.setName(ToTestName);
                toTest.setOwner(true);
            }
        };

        _ps = new PlaylistsServiceImpl(playlistDao, _trackDao, _playlistTrackDao, _tokenDao, _mapper);

        //Act
        _ps.updatePlaylist(toTest, token);

        //Assert
        Assertions.assertEquals(ToTestName, toTest.getName());
        Assertions.assertEquals(ToTestOwner, toTest.getOwner());
    }

    @Test
    public void DeletePlaylistTest() {
        //Arrange
        var playlistTracks = new ArrayList<PlaylistTrack>();
        var playlistTrack = new PlaylistTrack(1, 1);
        playlistTracks.add(playlistTrack);

        PlaylistTrackDaoImpl playlistTrackDao = new PlaylistTrackDaoImpl()  {
            @Override
            public void delete(PlaylistTrack playlistTrack) {
                playlistTracks.remove(playlistTrack);
            }

            @Override
            public ArrayList<PlaylistTrack> getAllBy(PlaylistTrack playlistTrack) {
                return playlistTracks;
            }
        };

        _ps = new PlaylistsServiceImpl(_playlistDao, _trackDao, playlistTrackDao, _tokenDao, _mapper);

        //Act
        _ps.deletePlaylistById(1);

        //Assert
        Assertions.assertEquals(0, playlistTracks.size());
    }
}
