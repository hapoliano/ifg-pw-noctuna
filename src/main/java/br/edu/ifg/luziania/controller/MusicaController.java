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

    // 1. Upload da Música (Atualizado)
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response upload(MusicaUploadForm form) {
        try {
            Musica musica = new Musica();
            musica.titulo = form.titulo;
            musica.artista = form.artista;

            // Salva o áudio
            if (form.arquivo != null) {
                musica.dados = Files.readAllBytes(form.arquivo.toPath());
                musica.nomeArquivo = form.arquivo.getName();
            }

            // NOVO: Salva a imagem (se o usuário enviou)
            if (form.imagem != null) {
                musica.capa = Files.readAllBytes(form.imagem.toPath());
            }

            musica.persist();
            return Response.seeOther(java.net.URI.create("/inicio")).build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    // 2. Player de Áudio (Mantido)
    @GET
    @Path("/{id}/audio")
    @Produces("audio/mpeg")
    @Transactional
    public Response baixarAudio(@PathParam("id") Long id) {
        Musica musica = Musica.findById(id);
        if (musica == null || musica.dados == null) return Response.status(404).build();
        return Response.ok(musica.dados).build();
    }

    // 3. NOVO: Endpoint para mostrar a Capa
    @GET
    @Path("/{id}/capa")
    @Produces("image/jpeg") // Assume JPEG (navegadores modernos detectam PNG auto)
    @Transactional
    public Response baixarCapa(@PathParam("id") Long id) {
        Musica musica = Musica.findById(id);

        // Se não tiver capa, retorna 404 (o HTML vai usar uma imagem padrão)
        if (musica == null || musica.capa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(musica.capa).build();
    }
}