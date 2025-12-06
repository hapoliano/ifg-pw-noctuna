package br.edu.ifg.luziania.controller;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/minha-conta")
public class MinhaContaController {

    @Inject
    Template minhaconta;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        // Retorna o template minhaConta (ex: minhaConta.html)
        return Response.ok(minhaconta.instance()).build();
    }
}