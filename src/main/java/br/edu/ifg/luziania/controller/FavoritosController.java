package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.dto.FavoritoDTO; // <--- Adicione este import
import br.edu.ifg.luziania.model.entity.Favorito;
import br.edu.ifg.luziania.service.FavoritoService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*; // Imports do JAX-RS (POST, Consumes, etc)
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response; // <--- Import do Response
import java.util.Collections;
import java.util.List;

@Path("/favoritos")
public class FavoritosController {

    @Inject
    Template favoritos;

    @Inject
    FavoritoService favoritoService;

    // ... seu método GET existente continua aqui ...
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@CookieParam("usuario_logado") String email) {
        // ... mantenha o código do GET como estava ...
        List<Favorito> lista;
        if (email != null) {
            lista = favoritoService.listarPorUsuario(email);
        } else {
            lista = Collections.emptyList();
        }
        return favoritos.data("listaFavoritos", lista);
    }

    // 👇 ADICIONE ESTE MÉTODO POST AQUI 👇
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alternarFavorito(@CookieParam("usuario_logado") String email, FavoritoDTO dto) {

        // Verifica se o usuário está logado
        if (email == null || email.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        // Chama o serviço que cria ou deleta o favorito
        boolean virouFavorito = favoritoService.alternarFavorito(email, dto);

        // Retorna um JSON simples: { "favorito": true } ou { "favorito": false }
        return Response.ok("{\"favorito\": " + virouFavorito + "}").build();
    }
}