package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dto.FavoritoDTO;
import br.edu.ifg.luziania.model.entity.Favorito;
import br.edu.ifg.luziania.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FavoritoBO {
    @Transactional
    public boolean alternarFavorito(String emailUsuario, FavoritoDTO dto) {
        // 1. Busca o usuário para pegar o ID
        Usuario usuario = Usuario.findByEmail(emailUsuario);
        if (usuario == null) return false; // Segurança

        // 2. Verifica se já existe favorito (pelo ID da música ou Título)
        Favorito existente = Favorito.findByUsuarioAndMusica(emailUsuario, dto.titulo, dto.id);

        if (existente != null) {
            // Se existe, remove
            existente.delete();
            return false;
        } else {
            // Se não existe, cria com os novos campos
            Favorito novo = new Favorito();

            novo.usuarioId = usuario.id; // ✅ ID do Usuário
            novo.usuarioEmail = usuario.email; // ✅ Email
            novo.musicaId = dto.id; // ✅ ID da Música (pode ser null)
            novo.titulo = dto.titulo; // ✅ Título

            // Dados extras para visualização
            novo.capaUrl = dto.capa;
            novo.audioUrl = dto.src;
            novo.artista = dto.artista != null ? dto.artista : "Desconhecido";

            novo.persist();
            return true;
        }
    }

    public List<Favorito> listarPorUsuario(String email) {
        return Favorito.findByUsuario(email);
    }
}
