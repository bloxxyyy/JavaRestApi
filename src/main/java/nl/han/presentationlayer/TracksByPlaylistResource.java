package nl.han.presentationlayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.domainlayer.services.TrackService;
import nl.han.domainlayer.viewmodels.TrackViewModel;

@Path("/playlists")
public class TracksByPlaylistResource {

    @Inject
    private TrackService _trackService;

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tracksByPlaylistRequest(@PathParam("id") int playlistId) {
        var tracks = _trackService.getTracksByPlaylist(playlistId);
        return Response.status(200).entity(tracks).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response tracksByPlaylistRequest(@PathParam("id") int playlistId, TrackViewModel trackViewModel) {
        _trackService.addTrackToPlaylist(trackViewModel, playlistId);
        var tracks = _trackService.getTracksByPlaylist(playlistId);
        return Response.status(200).entity(tracks).build();
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tracksByPlaylistRequest(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId) {
        _trackService.removeTrackFromPlaylist(playlistId, trackId);
        var tracks = _trackService.getTracksByPlaylist(playlistId);
        return Response.status(200).entity(tracks).build();
    }
}
