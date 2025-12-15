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
        // Busca usando o DAO
        Favorito existente = favoritoDAO.buscarPorUsuarioEMusica(email, dto.titulo, dto.id);

        if (existente != null) {
            // Se já existe, remove
            favoritoDAO.delete(existente);
            return false;
        } else {
            // Se não existe, cria um novo
            Favorito novo = new Favorito();
            novo.usuarioEmail = email;
            novo.titulo = dto.titulo;
            novo.musicaId = dto.id;
            novo.artista = dto.artista;

            // ✅ CORREÇÃO: Salvando os campos visuais que faltavam
            novo.capaUrl = dto.capa;
            novo.audioUrl = dto.src;

            favoritoDAO.persist(novo);
            return true;
        }
    }
}