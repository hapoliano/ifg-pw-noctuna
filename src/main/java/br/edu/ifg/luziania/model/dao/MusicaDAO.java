package br.edu.ifg.luziania.model.dao;

import br.edu.ifg.luziania.model.entity.Musica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MusicaDAO implements PanacheRepository<Musica> {

    public List<Musica> listarTodos() {
        return listAll();
    }
}
