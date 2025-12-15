package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dao.PlaylistDAO; // Importe o DAO
import br.edu.ifg.luziania.model.entity.Playlist;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PlaylistBO {

    @Inject
    PlaylistDAO playlistDAO;

    public List<Playlist> listar() {
        return playlistDAO.listarTodas();
    }

    @Transactional
    public void adicionar(String nome, String url) {
        // Regra de negócio: Cria o objeto aqui antes de mandar pro banco
        Playlist p = new Playlist();
        p.nome = nome;
        p.url = url;

        playlistDAO.adicionar(p);
    }

    @Transactional
    public void deletar(Long id) {
        playlistDAO.deletar(id);
    }
}