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

    // Método para salvar
    public void adicionar(Musica musica) {
        persist(musica);
    }

    // Método para buscar por ID (encapsulando o Panache)
    public Musica buscarPorId(Long id) {
        return findById(id);
    }
}
