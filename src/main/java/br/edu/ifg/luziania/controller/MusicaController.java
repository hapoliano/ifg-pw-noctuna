package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.dto.MusicaUploadForm;
import br.edu.ifg.luziania.model.entity.Musica;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;

@Path("/musicas-db")
public class MusicaController {

    // 1. Upload da Música
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response upload(MusicaUploadForm form) {
        try {
            Musica musica = new Musica();
            musica.titulo = form.titulo;
            musica.artista = form.artista;

            // Converte o arquivo enviado para bytes para salvar no banco
            musica.dados = Files.readAllBytes(form.arquivo.toPath());
            musica.nomeArquivo = form.arquivo.getName();

            musica.persist();

            // Redireciona para o início após salvar
            return Response.seeOther(java.net.URI.create("/inicio")).build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    // 2. Endpoint para o Player tocar a música (Stream)
    @GET
    @Path("/{id}/audio")
    @Produces("audio/mpeg")
    @Transactional
    public Response baixarAudio(@PathParam("id") Long id) {
        Musica musica = Musica.findById(id);

        if (musica == null || musica.dados == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(musica.dados)
                .header("Content-Disposition", "inline; filename=\"" + musica.nomeArquivo + "\"")
                .build();
    }
}