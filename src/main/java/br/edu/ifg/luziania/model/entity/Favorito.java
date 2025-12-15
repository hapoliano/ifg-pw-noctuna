package br.edu.ifg.luziania.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.util.List;

@Entity
public class Favorito extends PanacheEntity {

    // Campos solicitados
    public Long usuarioId;
    public String usuarioEmail;
    public Long musicaId; // Pode ser nulo se for uma música estática (sem ID no banco)
    public String titulo;

    // Campos auxiliares para exibição (Capa e Audio)
    public String capaUrl;
    public String audioUrl;
    public String artista;

    // Busca por usuário (pelo ID ou Email)
    public static List<Favorito> findByUsuario(String email) {
        return list("usuarioEmail", email);
    }

    // Verifica se já existe (agora checando também pelo ID da música se houver)
    public static Favorito findByUsuarioAndMusica(String email, String titulo, Long musicaId) {
        if (musicaId != null) {
            return find("usuarioEmail = ?1 and musicaId = ?2", email, musicaId).firstResult();
        }
        return find("usuarioEmail = ?1 and titulo = ?2", email, titulo).firstResult();
    }
}