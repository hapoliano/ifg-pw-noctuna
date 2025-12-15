package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.CadastroDTO;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cadastro")
public class CadastroController {

    @Inject
    Template cadastro;

    @Inject
    UsuarioBO usuarioBO; // Injeta o serviço

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(cadastro.instance()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(CadastroDTO dto) {
        System.out.println("Tentando cadastrar: " + dto.getEmail());

        boolean sucesso = usuarioBO.cadastrarUsuario(dto);

        if (sucesso) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            // Retorna erro se o email já existe (Conflito)
            return Response.status(Response.Status.CONFLICT).entity("Email já cadastrado").build();
        }
    }
}