package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.entity.Playlist;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/playlists")
public class PlaylistController {

    @Inject
    Template playlists;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        // Busca todas as playlists salvas no banco
        // Se quiser ordenar: Playlist.listAll(Sort.by("nome"))
        List<Playlist> lista = Playlist.listAll();
        return playlists.data("listaPlaylists", lista);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response adicionar(@FormParam("nome") String nome, @FormParam("url") String url) {
        Playlist p = new Playlist();
        p.nome = nome;
        p.url = url; // O usuário deve colar o link "Embed"
        p.persist();

        // Recarrega a página
        return Response.seeOther(URI.create("/playlists")).build();
    }

    @POST
    @Path("/deletar/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        Playlist.deleteById(id);
        return Response.seeOther(URI.create("/playlists")).build();
    }
}