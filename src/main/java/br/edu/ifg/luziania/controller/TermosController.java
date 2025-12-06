package br.edu.ifg.luziania.controller;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/termos")
public class TermosController {

    @Inject
    Template termos;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(termos.instance()).build();
    }

}