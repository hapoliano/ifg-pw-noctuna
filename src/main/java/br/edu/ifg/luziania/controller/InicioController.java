package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.service.InicioService;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/inicio")
public class InicioController {

    @Inject
    Template inicio;


    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(inicio.instance()).build();
    }
}
