package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.FavoritoBO; // Importe o BO
import br.edu.ifg.luziania.model.dto.FavoritoDTO;
import br.edu.ifg.luziania.model.entity.Favorito;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/favoritos")
public class FavoritosController {

    @Inject
    Template favoritos;

    @Inject
    FavoritoBO favoritoBO; // Agora usamos o BO

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@CookieParam("usuario_logado") String email) {
        List<Favorito> lista;
        if (email != null) {
            lista = favoritoBO.listar(email);
        } else {
            lista = Collections.emptyList();
        }
        return favoritos.data("listaFavoritos", lista);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alternarFavorito(@CookieParam("usuario_logado") String email, FavoritoDTO dto) {
        if (email == null || email.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        boolean virouFavorito = favoritoBO.alternar(email, dto);

        return Response.ok("{\"favorito\": " + virouFavorito + "}").build();
    }
}