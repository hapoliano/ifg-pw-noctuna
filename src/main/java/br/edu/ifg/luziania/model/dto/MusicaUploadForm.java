package br.edu.ifg.luziania.model.dto;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import java.io.File;

public class MusicaUploadForm {

    @FormParam("titulo")
    public String titulo;

    @FormParam("artista")
    public String artista;

    // Arquivo de MP3
    @RestForm("arquivo")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public File arquivo;

    // NOVO: Arquivo de Imagem
    @RestForm("imagem") // O nome "imagem" deve ser igual ao do input no HTML
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public File imagem;
}