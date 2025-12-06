package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.service.InicioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/inicio")
public class InicioController {

    @Inject
    InicioService inicioService;


    @GET
    @Path("/usuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@QueryParam("email") String email) {
        com.seuprojeto.dto.UsuarioDTO usuario = inicioService.getUsuarioLogado(email);
        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensagem\":\"Usuário não encontrado\"}")
                    .build();
        }
    }
}
