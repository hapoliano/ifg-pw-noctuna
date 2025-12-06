package br.edu.ifg.luziania.controller;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cadastro")
public class CadastroController {

    @Inject
    Template cadastro;

    // 1. Exibe a tela de cadastro
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(cadastro.instance()).build();
    }

    // 2. Recebe os dados do formulário (Exemplo)
    // Você precisará criar um CadastroDTO com os campos (nome, email, senha, etc)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Object cadastroDTO) { // Troque Object pelo seu DTO
        System.out.println("Recebendo dados de cadastro...");

        // Aqui você chamaria: usuarioService.salvar(cadastroDTO);

        return Response.status(Response.Status.CREATED).build();
    }
}