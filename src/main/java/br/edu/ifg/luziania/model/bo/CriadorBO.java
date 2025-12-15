package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dao.MusicaDAO;
import br.edu.ifg.luziania.model.dto.MusicaDTO;
import br.edu.ifg.luziania.model.dto.MusicaUploadForm;
import br.edu.ifg.luziania.model.entity.Musica;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CriadorBO {

    @Inject
    MusicaDAO musicaDAO;

    private List<MusicaDTO> playlist = new ArrayList<>();
    private int totalUsuarios = 2;
    private int totalMusicas = 4;

    public CriadorBO() {

        playlist.add(new MusicaDTO("Largado as Traças"));
        playlist.add(new MusicaDTO("Música 2"));
        playlist.add(new MusicaDTO("Música 3"));
        playlist.add(new MusicaDTO("Música 4"));
    }

    @Transactional
    public void adicionar(MusicaUploadForm form) throws IOException {
        Musica novaMusica = new Musica();
        novaMusica.titulo = form.titulo;
        novaMusica.artista = form.artista;

        // Regra de Negócio: Converter Arquivo de Áudio
        if (form.arquivo != null) {
            novaMusica.dados = Files.readAllBytes(form.arquivo.toPath());
            novaMusica.nomeArquivo = form.arquivo.getName();
        }

        // Regra de Negócio: Converter Arquivo de Imagem (Capa)
        if (form.imagem != null) {
            novaMusica.capa = Files.readAllBytes(form.imagem.toPath());
        }

        // Chama a camada de dados para salvar
        musicaDAO.adicionar(novaMusica);
    }

    public List<MusicaDTO> listarMusicas() {
        return playlist;
    }


    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public int getTotalMusicas() {
        return totalMusicas;
    }


    public MusicaDTO musicaMaisTocada() {
        return playlist.get(0);
    }
}
