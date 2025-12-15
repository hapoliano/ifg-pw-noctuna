package br.edu.ifg.luziania.model.dao;

import br.edu.ifg.luziania.model.entity.Favorito;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FavoritoDAO implements PanacheRepository<Favorito> {

    public List<Favorito> listarPorUsuario(String email) {
        return find("usuarioEmail", email).list();
    }

    public Favorito buscarPorUsuarioEMusica(String email, String titulo, Long musicaId) {
        // Se tiver ID da música, busca pelo ID e Email
        if (musicaId != null) {
            return find("usuarioEmail = ?1 and musicaId = ?2", email, musicaId).firstResult();
        }

        // Se não tiver ID (música estática), busca pelo Título e Email
        return find("usuarioEmail = ?1 and titulo = ?2", email, titulo).firstResult();
    }
}