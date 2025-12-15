package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.LoginBO;
import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.LoginDTO; // Certifique-se de ter esse DTO (email, senha)
import br.edu.ifg.luziania.model.entity.Usuario;
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
    UsuarioBO usuarioBO;

    @Inject
    LoginBO loginBO;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(login.instance()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response loginForm(@FormParam("email") String email, @FormParam("senha") String senha) {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setSenha(senha);

        boolean autenticado = loginBO.autenticar(loginDTO);

        if (autenticado) {
            // --- CORREÇÃO: CRIAR O COOKIE ---
            NewCookie cookie = new NewCookie.Builder("usuario_logado")
                    .value(email)
                    .path("/")
                    .maxAge(3600)
                    .build();

            // Adiciona o cookie na resposta junto com o redirecionamento
            return Response.seeOther(URI.create("/inicio"))
                    .cookie(cookie)
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Login falhou! <a href='/login'>Tente novamente</a>")
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logar(LoginDTO dto) {

        Usuario usuario = usuarioBO.validarLogin(dto.getEmail(), dto.getSenha());

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