package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.dto.LoginDTO;
import br.edu.ifg.luziania.service.LoginService;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/login")
public class LoginController {

    @Inject
    Template login;

    @Inject
    LoginService loginService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(login.instance()).build();
    }

    // ✅ MÉTODO NOVO (ESSENCIAL): Aceita o formulário HTML
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response loginForm(@FormParam("email") String email, @FormParam("senha") String senha) {

        // 1. Cria o DTO com os dados que vieram do formulário
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setSenha(senha);

        // 2. Verifica no banco de dados
        boolean autenticado = loginService.autenticar(loginDTO);

        if (autenticado) {
            // 3. Se deu certo, redireciona para a página inicial
            return Response.seeOther(URI.create("/inicio")).build();
        } else {
            // 4. Se errou a senha, devolve a página de login com erro (ou redireciona de volta)
            // Uma forma simples é redirecionar de volta para o login:
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Login falhou! <a href='/login'>Tente novamente</a>")
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
    }

    // Método antigo (Pode manter para uso futuro com APIs/React, mas não é usado pelo formulário HTML)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginJson(LoginDTO loginDTO) {
        boolean autenticado = loginService.autenticar(loginDTO);

        if (autenticado) {
            return Response.ok("{\"mensagem\": \"Login bem-sucedido!\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"Email ou senha inválidos.\"}")
                    .build();
        }
    }
}