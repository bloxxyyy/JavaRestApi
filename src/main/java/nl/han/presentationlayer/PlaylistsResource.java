package nl.han.presentationlayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.domainlayer.services.PlayListService;
import nl.han.domainlayer.viewmodels.PlaylistViewModel;

@Path("/playlists")
public class PlaylistsResource {

    @Inject
    private PlayListService _playlistService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response playlistsRequest(PlaylistViewModel playlistViewModel, @QueryParam("token") String token) {
        _playlistService.addPlaylist(playlistViewModel, token);
        var playlists = _playlistService.getPlaylistsWithTracks(token);
        return Response.status(200).entity(playlists).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response playlistsRequest(@QueryParam("token") String token) {
        var playlists = _playlistService.getPlaylistsWithTracks(token);
        return Response.status(200).entity(playlists).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlaylistRequest(PlaylistViewModel playlistViewModel, @QueryParam("token") String token) {
        _playlistService.updatePlaylist(playlistViewModel, token);
        var playlists = _playlistService.getPlaylistsWithTracks(token);
        return Response.status(200).entity(playlists).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylistRequest(@PathParam("id") int playlistId, @QueryParam("token") String token) {
        _playlistService.deletePlaylistById(playlistId);
        var playlists = _playlistService.getPlaylistsWithTracks(token);
        return Response.status(200).entity(playlists).build();
    }
}
