package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.dto.MusicaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CriadorService {

    private List<MusicaDTO> playlist = new ArrayList<>();
    private int totalUsuarios = 2;
    private int totalMusicas = 4;

    public CriadorService() {

        playlist.add(new MusicaDTO("Largado as Traças"));
        playlist.add(new MusicaDTO("Música 2"));
        playlist.add(new MusicaDTO("Música 3"));
        playlist.add(new MusicaDTO("Música 4"));
    }

    public void adicionarMusica(MusicaDTO musicaDTO) {
        playlist.add(musicaDTO);
        totalMusicas++;
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
