package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.CriadorBO;
import br.edu.ifg.luziania.model.dto.MusicaDTO;
import br.edu.ifg.luziania.model.dto.MusicaUploadForm; // Importe o Form
import br.edu.ifg.luziania.model.entity.Musica; // Importe a Entity
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional; // Importante para salvar no banco
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
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

    // Listar músicas (Mantido igual, mas agora busca do banco se quiser)
    @GET
    @Path("/musicas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MusicaDTO> listarMusicas() {
        return criadorBO.listarMusicas();
    }

    // ✅ AJUSTADO: Agora aceita Upload de Arquivo
    @POST
    @Path("/musicas/adicionar")
    @Consumes(MediaType.MULTIPART_FORM_DATA) // Mudado para aceitar arquivos
    @Transactional // Necessário para salvar no banco
    public Response adicionarMusica(MusicaUploadForm form) {
        try {
            // Cria a entidade Música e preenche com os dados do formulário
            Musica novaMusica = new Musica();
            novaMusica.titulo = form.titulo;
            novaMusica.artista = form.artista;

            // Converte o arquivo recebido para bytes
            if (form.arquivo != null) {
                novaMusica.dados = Files.readAllBytes(form.arquivo.toPath());
                novaMusica.nomeArquivo = form.arquivo.getName();
            }

            // Salva no banco de dados
            novaMusica.persist();

            // Redireciona o usuário de volta para o início (ou para o painel)
            // Se fosse retornar JSON, o navegador apenas mostraria o texto na tela
            return Response.seeOther(URI.create("/inicio")).build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao processar o arquivo").build();
        }
    }

    // Estatísticas (Mantido)
    @GET
    @Path("/estatisticas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estatisticas() {
        // Verifica se o serviço retornou nulo para evitar erro 500
        var maisTocada = criadorBO.musicaMaisTocada();
        String nomeMaisTocada = (maisTocada != null) ? maisTocada.getNome() : "Nenhuma";

        return Response.ok("{"
                + "\"totalUsuarios\": " + criadorBO.getTotalUsuarios() + ","
                + "\"totalMusicas\": " + criadorBO.getTotalMusicas() + ","
                + "\"musicaMaisTocada\": \"" + nomeMaisTocada + "\""
                + "}").build();
    }
}