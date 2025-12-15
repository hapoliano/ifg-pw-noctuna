package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.PlaylistBO; // Importe o BO
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/playlists")
public class PlaylistController {

    @Inject
    Template playlists;

    @Inject
    PlaylistBO playlistBO; // Injeção do BO

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        // Busca a lista através do BO
        return playlists.data("listaPlaylists", playlistBO.listar());
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response adicionar(@FormParam("nome") String nome, @FormParam("url") String url) {
        // Delega a criação para o BO
        playlistBO.adicionar(nome, url);
        return Response.seeOther(URI.create("/playlists")).build();
    }

    @POST
    @Path("/deletar/{id}")
    public Response deletar(@PathParam("id") Long id) {
        // Delega a remoção para o BO
        playlistBO.deletar(id);
        return Response.seeOther(URI.create("/playlists")).build();
    }
}