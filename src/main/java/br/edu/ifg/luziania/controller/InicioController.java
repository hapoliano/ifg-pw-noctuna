package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.entity.Musica;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/inicio")
public class InicioController {

    @Inject
    Template inicio;


    @GET
    @Produces(MediaType.TEXT_HTML)
    @Transactional // ✅ 2. Adicione esta anotação
    public TemplateInstance get() {
        // Busca todas as músicas salvas no banco
        // O @Transactional garante a conexão para ler os arquivos MP3 (Lob)
        List<Musica> musicasDoBanco = Musica.listAll();

        return inicio.data("musicasBanco", musicasDoBanco);
    }
}
