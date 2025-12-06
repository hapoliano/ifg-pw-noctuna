package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.dto.MusicaDTO;
import br.edu.ifg.luziania.service.CriadorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/criador")
public class CriadorController {

    @Inject
    CriadorService criadorService;

    // Endpoint para listar todas as músicas do criador
    @GET
    @Path("/musicas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MusicaDTO> listarMusicas() {
        return criadorService.listarMusicas();
    }


    @POST
    @Path("/musicas/adicionar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adicionarMusica(MusicaDTO musicaDTO) {
        criadorService.adicionarMusica(musicaDTO);
        return Response.ok("{\"mensagem\": \"Música adicionada com sucesso!\"}").build();
    }


    @GET
    @Path("/estatisticas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estatisticas() {
        return Response.ok("{"
                + "\"totalUsuarios\": " + criadorService.getTotalUsuarios() + ","
                + "\"totalMusicas\": " + criadorService.getTotalMusicas() + ","
                + "\"musicaMaisTocada\": \"" + criadorService.musicaMaisTocada().getNome() + "\""
                + "}").build();
    }
}
