package br.edu.ifg.luziania.model.dao;

import br.edu.ifg.luziania.model.entity.Playlist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PlaylistDAO implements PanacheRepository<Playlist> {

    public List<Playlist> listarTodas() {
        return listAll();
    }

    public void adicionar(Playlist playlist) {
        persist(playlist);
    }

    public void deletar(Long id) {
        deleteById(id);
    }
}