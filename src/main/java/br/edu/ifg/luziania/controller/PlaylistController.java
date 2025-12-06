package br.edu.ifg.luziania.controller;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController {

    @Inject
    Template playlists;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(playlists.instance()).build();
    }

}