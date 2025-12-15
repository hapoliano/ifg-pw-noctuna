package br.edu.ifg.luziania.model.dao;

import br.edu.ifg.luziania.model.entity.Favorito;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FavoritoDAO implements PanacheRepository<Favorito> {

    /**
     * Busca todos os favoritos de um usuário específico.
     */
    public List<Favorito> listarPorUsuario(String email) {
        return find("usuarioEmail", email).list();
    }

    /**
     * Busca um favorito específico para verificar se já existe.
     */
    public Favorito buscarPorUsuarioEMusica(String email, String titulo, Long musicaId) {
        return find("usuarioEmail = ?1 and titulo = ?2 and musicaId = ?3", email, titulo, musicaId).firstResult();
    }
}