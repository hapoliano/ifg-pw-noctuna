package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.MusicaBO;
import br.edu.ifg.luziania.model.dto.MusicaUploadForm;
import br.edu.ifg.luziania.model.entity.Musica;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

@Path("/musicas-db")
public class MusicaController {

    @Inject
    MusicaBO musicaBO; // Injeção do BO

    // 1. Upload da Música
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(MusicaUploadForm form) {
        try {
            // Delega toda a lógica para o BO
            musicaBO.salvarMusica(form);

            // Redireciona em caso de sucesso
            return Response.seeOther(URI.create("/inicio")).build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao processar arquivos.").build();
        }
    }

    // 2. Player de Áudio
    @GET
    @Path("/{id}/audio")
    @Produces("audio/mpeg")
    public Response baixarAudio(@PathParam("id") Long id) {
        // Busca via BO
        Musica musica = musicaBO.buscarPorId(id);

        if (musica == null || musica.dados == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Retorna os bytes do áudio
        return Response.ok(musica.dados)
                .header("Content-Disposition", "inline; filename=\"" + musica.nomeArquivo + "\"")
                .build();
    }

    // 3. Mostrar Capa
    @GET
    @Path("/{id}/capa")
    @Produces("image/jpeg")
    public Response baixarCapa(@PathParam("id") Long id) {
        // Busca via BO
        Musica musica = musicaBO.buscarPorId(id);

        if (musica == null || musica.capa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Retorna os bytes da imagem
        return Response.ok(musica.capa).build();
    }
}