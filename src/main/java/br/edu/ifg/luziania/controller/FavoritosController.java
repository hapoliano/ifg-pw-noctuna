package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.entity.Favorito;
import br.edu.ifg.luziania.service.FavoritoService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/favoritos")
public class FavoritosController {

    @Inject
    Template favoritos; // Isso busca o arquivo favoritos.html

    @Inject
    FavoritoService favoritoService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@CookieParam("usuario_logado") String email) {
        List<Favorito> lista;

        // Se o usuário estiver logado (cookie existe), busca no banco
        if (email != null) {
            lista = favoritoService.listarPorUsuario(email);
        } else {
            // Se não estiver logado, cria uma lista vazia para não dar erro
            lista = Collections.emptyList();
        }

        // --- A CORREÇÃO ESTÁ AQUI ---
        // Você PRECISA usar o .data("nomeNoHTML", variavelJava)
        // "listaFavoritos" deve ser igual ao que você usou no {#for ...} do HTML
        return favoritos.data("listaFavoritos", lista);
    }
}