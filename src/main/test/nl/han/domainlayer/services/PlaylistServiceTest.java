package nl.han.domainlayer.services;

import nl.han.domainlayer.mappers.PlaylistsMapper;
import nl.han.domainlayer.mappers.UserMapper;
import nl.han.domainlayer.viewmodels.PlaylistViewModel;
import nl.han.domainlayer.viewmodels.PlaylistsViewModel;
import nl.han.domainlayer.viewmodels.TrackViewModel;
import nl.han.servicelayer.Entities.*;
import nl.han.servicelayer.daos.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PlaylistServiceTest {

    private PlaylistsMapper _mapper = Mockito.spy(PlaylistsMapper.class);
    private PlaylistDaoImpl _playlistDao = Mockito.mock(PlaylistDaoImpl.class);
    private TrackDaoImpl _trackDao = Mockito.mock(TrackDaoImpl.class);
    private PlaylistTrackDaoImpl _playlistTrackDao = Mockito.mock(PlaylistTrackDaoImpl.class);
    private TokenDAOImpl _tokenDao = Mockito.mock(TokenDAOImpl.class);

    @Test
    public void getAllPlaylistsTest() {
        //Arrange
        String token = "1234";
        PlaylistsServiceImpl ps = new PlaylistsServiceImpl(_playlistDao, _trackDao, _playlistTrackDao, _tokenDao, _mapper);

        PlaylistViewModel toTest = new PlaylistViewModel();
        toTest.setId(1);
        toTest.setName("test");
        toTest.setOwner(true);
        toTest.setTracks(new TrackViewModel[0]);

        List<Playlist> playlists = List.of(new Playlist[]{new Playlist(1, "test", "owner", new Track[0])});

        when(_tokenDao.get(any(Token.class))).thenReturn(new Token("owner", token));
        when(_playlistDao.getAll()).thenReturn(playlists);

        //Act
        var gottenPlaylists = ps.getPlaylistsWithTracks(token);
        var playlist = gottenPlaylists.getPlaylists()[0];

        //Assert
        Assertions.assertEquals(playlist.getId(), toTest.getId());
        Assertions.assertEquals(playlist.getName(), toTest.getName());
        Assertions.assertEquals(playlist.getTracks().length, toTest.getTracks().length);
        Assertions.assertEquals(playlist.getOwner(), toTest.getOwner());
    }

}
