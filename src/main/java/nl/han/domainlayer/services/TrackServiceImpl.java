package nl.han.domainlayer.services;

import jakarta.inject.Inject;
import nl.han.domainlayer.mappers.PlaylistsMapper;
import nl.han.domainlayer.viewmodels.TrackViewModel;
import nl.han.domainlayer.viewmodels.TracksViewModel;
import nl.han.servicelayer.Entities.Playlist;
import nl.han.servicelayer.Entities.PlaylistTrack;
import nl.han.servicelayer.Entities.Token;
import nl.han.servicelayer.Entities.Track;
import nl.han.servicelayer.daos.Dao;

import java.util.*;
import java.util.stream.Collectors;

public class TrackServiceImpl implements TrackService {

    private Dao<Playlist> _playlistDao;
    private Dao<Track> _trackDao;
    private Dao<PlaylistTrack> _playlistTrackDao;
    private PlaylistsMapper _mapper;

    @Inject
    public TrackServiceImpl(Dao<Playlist> playlistDao, Dao<Track> trackDao, Dao<PlaylistTrack> playlistTrackDao, PlaylistsMapper mapper) {
        _playlistDao = playlistDao;
        _trackDao = trackDao;
        _playlistTrackDao = playlistTrackDao;
        _mapper = mapper;
    }

    @Override
    public TracksViewModel getTracksByPlaylist(int playlistId) {
        var entity = _playlistDao.get(new Playlist(playlistId, null, "", null));
        GetTracksByPlaylist(playlistId, entity);
        return _mapper.ToTracksDto(entity);
    }

    @Override
    public TracksViewModel getTrackNotInPlaylist(int playlistId) {
        var entity = _playlistDao.get(new Playlist(playlistId, null, "", null));
        GetTracksByPlaylist(playlistId, entity);
        var tracksToList = Arrays.stream(entity.getTracks()).toList();

        final List<Integer> list2Ids = tracksToList.stream().map(Track::getId).collect(Collectors.toList());
        final List<Track> trackList = new LinkedList<>();

        for (Iterator<Track> iterator = _trackDao.getAll().iterator(); iterator.hasNext();) {
            Track next = iterator.next();

            if (!list2Ids.contains(next.getId())) {
                trackList.add(next);
                iterator.remove();
            }
        }

        entity.setTracks(trackList.toArray(Track[]::new));
        return _mapper.ToTracksDto(entity);
    }

    @Override
    public void addTrackToPlaylist(TrackViewModel trackViewModel, int playlistId) {
       var track = new Track(trackViewModel.getId(), null, null, -1, null, -1, null, null, false);
        track = _trackDao.get(track);
        track.setOfflineAvailable(trackViewModel.getOfflineAvailable());
        _trackDao.update(track);
        var playlistTrack = new PlaylistTrack(trackViewModel.getId(), playlistId);
        _playlistTrackDao.add(playlistTrack);
    }

    @Override
    public void removeTrackFromPlaylist(int playlistId, int trackId) {
        var playlistTrack = new PlaylistTrack(trackId, playlistId);
        _playlistTrackDao.delete(playlistTrack);
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
