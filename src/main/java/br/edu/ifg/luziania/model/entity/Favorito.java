package br.edu.ifg.luziania.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Favorito extends PanacheEntity {

    // Campos principais
    public Long usuarioId;
    public String usuarioEmail;
    public Long musicaId; // Pode ser nulo
    public String titulo;

    // Campos visuais (Capa, Áudio e Artista)
    public String capaUrl;
    public String audioUrl;
    public String artista;

    // Construtor padrão
    public Favorito() {}
}