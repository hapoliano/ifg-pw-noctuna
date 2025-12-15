package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dao.MusicaDAO;
import br.edu.ifg.luziania.model.dto.MusicaUploadForm;
import br.edu.ifg.luziania.model.entity.Musica;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;

@ApplicationScoped
public class MusicaBO {

    @Inject
    MusicaDAO musicaDAO;

    @Transactional
    public void salvarMusica(MusicaUploadForm form) throws IOException {
        Musica musica = new Musica();
        musica.titulo = form.titulo;
        musica.artista = form.artista;

        if (form.arquivo != null) {
            musica.dados = Files.readAllBytes(form.arquivo.toPath());
            musica.nomeArquivo = form.arquivo.getName();
        }

        if (form.imagem != null) {
            musica.capa = Files.readAllBytes(form.imagem.toPath());
        }

        musicaDAO.adicionar(musica);
    }

    /**
     * ✅ CORREÇÃO: Adicionado @Transactional aqui.
     * O Postgres exige transação para ler campos @Lob (bytes da música/capa).
     */
    @Transactional
    public Musica buscarPorId(Long id) {
        return musicaDAO.buscarPorId(id);
    }
}