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

    // Recebe o arquivo do HTML
    @RestForm("arquivo")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public File arquivo;
}