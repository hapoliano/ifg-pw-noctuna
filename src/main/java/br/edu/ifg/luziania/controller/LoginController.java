package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.LoginBO;
import br.edu.ifg.luziania.model.dto.LoginDTO;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/login")
public class LoginController {

    @Inject
    Template login;

    @Inject
    LoginBO loginBO;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(login.instance()).build();
    }

    // Login via JSON (API / JavaScript)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logar(LoginDTO dto) {

        boolean autenticado = loginBO.autenticar(dto);

        if (autenticado) {
            NewCookie cookie = new NewCookie.Builder("usuario_logado")
                    .value(dto.getEmail())
                    .path("/")
                    .maxAge(3600)
                    .build();

            return Response.ok()
                    .cookie(cookie)
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}