package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.CriadorBO;
import br.edu.ifg.luziania.model.dto.MusicaDTO;
import br.edu.ifg.luziania.model.dto.MusicaUploadForm;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Path("/criador")
public class CriadorController {

    @Inject
    CriadorBO criadorBO;

    @Inject
    Template criador;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(criador.instance()).build();
    }

    @GET
    @Path("/musicas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MusicaDTO> listarMusicas() {
        return criadorBO.listarMusicas();
    }

    // ✅ REFATORADO: Controller limpo, delegando para o BO
    @POST
    @Path("/musicas/adicionar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response adicionarMusica(MusicaUploadForm form) {
        try {
            // Apenas chama o BO. Se der erro, cai no catch.
            criadorBO.adicionar(form);

            // Sucesso
            return Response.seeOther(URI.create("/inicio")).build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao processar o arquivo").build();
        }
    }

    @GET
    @Path("/estatisticas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estatisticas() {
        var maisTocada = criadorBO.musicaMaisTocada();
        String nomeMaisTocada = (maisTocada != null) ? maisTocada.getNome() : "Nenhuma";

        return Response.ok("{"
                + "\"totalUsuarios\": " + criadorBO.getTotalUsuarios() + ","
                + "\"totalMusicas\": " + criadorBO.getTotalMusicas() + ","
                + "\"musicaMaisTocada\": \"" + nomeMaisTocada + "\""
                + "}").build();
    }
}