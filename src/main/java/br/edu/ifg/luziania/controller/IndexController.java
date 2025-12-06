package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.dto.LoginDTO;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/index")
public class IndexController {

    // 1. Injeção direta do template (padrão do ContaController)
    @Inject
    Template index;

    // 2. Método GET agora retorna Response
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(index.instance()).build();
    }

    // Os métodos abaixo já seguiam o padrão Response, mantive como estavam
    @GET
    @Path("logar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logarQuery(@QueryParam("login") String login, @QueryParam("senha") String senha) {
        System.out.println("login: " + login);
        System.out.println("senha: " + senha);
        return Response.ok().build();
    }

    @POST
    @Path("logar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logarPost(LoginDTO dto) {
        System.out.println("login: " + dto.getEmail());
        System.out.println("senha: " + dto.getSenha());
        return Response.ok().build();
    }

    @GET
    @Path("logar/{login}/{senha}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logarPath(@PathParam("login") String login, @PathParam("senha") String senha) {
        System.out.println("login: " + login);
        System.out.println("senha: " + senha);
        return Response.ok().build();
    }
}