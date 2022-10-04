package nl.han.domainlayer.services;

import jakarta.inject.Inject;
import nl.han.domainlayer.mappers.PlaylistsMapper;
import nl.han.domainlayer.viewmodels.PlaylistViewModel;
import nl.han.domainlayer.viewmodels.PlaylistsViewModel;
import nl.han.servicelayer.Entities.Playlist;
import nl.han.servicelayer.Entities.PlaylistTrack;
import nl.han.servicelayer.Entities.Token;
import nl.han.servicelayer.Entities.Track;
import nl.han.servicelayer.daos.Dao;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PlaylistsServiceImpl implements PlayListService {

    private Dao<Playlist> _playlistDao;
    private Dao<Track> _trackDao;
    private Dao<PlaylistTrack> _playlistTrackDao;
    private Dao<Token> _tokenDao;
    private PlaylistsMapper _mapper;

    @Inject
    public PlaylistsServiceImpl(Dao<Playlist> playlistDao, Dao<Track> trackDao, Dao<PlaylistTrack> playlistTrackDao, Dao<Token> tokenDao, PlaylistsMapper mapper) {
        _playlistDao = playlistDao;
        _trackDao = trackDao;
        _playlistTrackDao = playlistTrackDao;
        _tokenDao = tokenDao;
        _mapper = mapper;
    }

    @Override
    public PlaylistsViewModel getPlaylistsWithTracks(String token) {
        var entity = _playlistDao.getAll().stream().map(playlist -> {
            GetTracksByPlaylist(playlist.getId(), playlist);
            return playlist;
        }).collect(Collectors.toList());

        var TokenEntity = new Token("", token);
        TokenEntity = _tokenDao.get(TokenEntity);

        return _mapper.toPlaylistsDto(entity, TokenEntity.GetUsername());
    }

    @Override
    public void deletePlaylistById(int playlistId) {
        var playlistTrack = new PlaylistTrack(-1, playlistId);
        var playlist = new Playlist(playlistId, null, "", null);
        var playListTracks =  _playlistTrackDao.getAllBy(playlistTrack);
        playListTracks.forEach(plt -> _playlistTrackDao.delete(plt));
        _playlistDao.delete(playlist);
    }

    @Override
    public void addPlaylist(PlaylistViewModel playlistViewModel, String token) {
        var TokenEntity = new Token("", token);
        TokenEntity = _tokenDao.get(TokenEntity);
        var playlistEntity = _mapper.ToPlaylistEntity(playlistViewModel, TokenEntity);
        _playlistDao.add(playlistEntity);
    }

    @Override
    public void updatePlaylist(PlaylistViewModel playlistViewModel, String token) {
        var TokenEntity = new Token("", token);
        TokenEntity = _tokenDao.get(TokenEntity);

        var entity = _mapper.ToPlaylistEntity(playlistViewModel, TokenEntity);
        _playlistDao.update(entity);
    }

    private void GetTracksByPlaylist(int playlistId, Playlist entity) {
        var playlistTrack = new PlaylistTrack(-1, playlistId);
        ArrayList<Track> tracks = new ArrayList<>();

        _playlistTrackDao.getAllBy(playlistTrack).forEach(
                plt -> tracks.add(new Track(plt.getTrackId(),
                        null, null, 0, null, 0, null, null, false)
                )
        );

        entity.setTracks(tracks.stream().map(track -> _trackDao.get(track)).toArray(Track[]::new));
    }
}
