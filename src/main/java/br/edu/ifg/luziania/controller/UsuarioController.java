package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cadastro")
public class UsuarioController {

    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(UsuarioDTO usuarioDTO) {
        boolean sucesso = usuarioService.cadastrarUsuario(usuarioDTO);

        if (sucesso) {
            return Response.ok("{\"mensagem\": \"Cadastro realizado com sucesso!\"}").build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"mensagem\": \"Email já cadastrado.\"}")
                    .build();
        }
    }
}
