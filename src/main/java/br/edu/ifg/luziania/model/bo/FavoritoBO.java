package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dao.FavoritoDAO; // Importe o DAO
import br.edu.ifg.luziania.model.dto.FavoritoDTO;
import br.edu.ifg.luziania.model.entity.Favorito;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FavoritoBO {

    @Inject
    FavoritoDAO favoritoDAO; // Injeção do DAO

    public List<Favorito> listar(String email) {
        return favoritoDAO.listarPorUsuario(email);
    }

    @Transactional
    public boolean alternar(String email, FavoritoDTO dto) {
        // Usa o DAO para buscar
        Favorito existente = favoritoDAO.buscarPorUsuarioEMusica(email, dto.titulo, dto.id);

        if (existente != null) {
            // Usa o DAO para deletar
            favoritoDAO.delete(existente);
            return false; // Retorna false (não é mais favorito)
        } else {
            // Cria e usa o DAO para salvar
            Favorito novo = new Favorito();
            novo.usuarioEmail = email;
            novo.titulo = dto.titulo;
            novo.musicaId = dto.id;
            novo.artista = dto.artista;

            favoritoDAO.persist(novo);
            return true; // Retorna true (agora é favorito)
        }
    }
}