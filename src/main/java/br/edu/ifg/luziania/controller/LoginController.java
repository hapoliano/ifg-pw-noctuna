package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.dto.LoginDTO; // Certifique-se de ter esse DTO (email, senha)
import br.edu.ifg.luziania.model.entity.Usuario;
import br.edu.ifg.luziania.service.UsuarioService;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/login")
public class LoginController {

    @Inject
    Template login;

    @Inject
    UsuarioService usuarioService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(login.instance()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logar(LoginDTO dto) {

        Usuario usuario = usuarioService.validarLogin(dto.getEmail(), dto.getSenha());

        if (usuario != null) {
            // Cria um cookie chamado "usuario_logado" com o e-mail do usuário
            // Path "/" significa que o cookie vale para todo o site
            NewCookie cookie = new NewCookie.Builder("usuario_logado")
                    .value(usuario.email)
                    .path("/")
                    .maxAge(3600) // Cookie dura 1 hora (3600 segundos)
                    .build();

            return Response.ok()
                    .cookie(cookie) // Envia o cookie para o navegador
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}