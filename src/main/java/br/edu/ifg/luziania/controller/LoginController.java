package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.dto.LoginDTO;
import br.edu.ifg.luziania.service.LoginService;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginDTO) {
        boolean autenticado = loginService.autenticar(loginDTO);

        if (autenticado) {
            // Futuramente, aqui é onde você adicionaria o comando para criar o Cookie (NewCookie)
            return Response.ok("{\"mensagem\": \"Login bem-sucedido!\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"Email ou senha inválidos.\"}")
                    .build();
        }
    }
}