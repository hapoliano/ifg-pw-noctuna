package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.MinhaContaDTO;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam; // Importante
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/minha-conta")
public class MinhaContaController {

    @Inject
    Template minhaconta;

    @Inject
    UsuarioBO usuarioBO;

    @GET
    @Produces(MediaType.TEXT_HTML)
    // O @CookieParam tenta pegar o valor do cookie "usuario_logado"
    public Response get(@CookieParam("usuario_logado") String emailLogado) {

        // Se o cookie estiver vazio, chuta o usuário para o login
        if (emailLogado == null || emailLogado.isEmpty()) {
            return Response.seeOther(URI.create("/login")).build();
        }

        MinhaContaDTO dados = usuarioBO.buscarDadosMinhaConta(emailLogado);

        if (dados == null) {
            // Se tem cookie mas não achou no banco (ex: usuario deletado), volta pro login
            return Response.seeOther(URI.create("/login")).build();
        }

        // Retorna a página preenchida
        return Response.ok(minhaconta.data("perfil", dados)).build();
    }
}