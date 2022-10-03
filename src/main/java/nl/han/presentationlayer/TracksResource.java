package nl.han.presentationlayer;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.domainlayer.services.TrackService;

@Path("/tracks")
public class TracksResource {

    @Inject
    private TrackService _trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response tracksByPlaylistRequest(@QueryParam("forPlaylist") int playlistId) {
        var tracks = _trackService.getTrackNotInPlaylist(playlistId);
        return Response.status(200).entity(tracks).build();
    }
}
