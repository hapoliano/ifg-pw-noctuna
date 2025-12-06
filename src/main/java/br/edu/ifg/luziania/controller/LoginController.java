package com.seuprojeto.controller;

import br.edu.ifg.luziania.dto.LoginDTO;
import br.edu.ifg.luziania.service.LoginService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    @Inject
    LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginDTO) {
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

