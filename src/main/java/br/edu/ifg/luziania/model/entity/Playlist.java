package br.edu.ifg.luziania.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Playlist extends PanacheEntity {
    public String nome;
    public String url; // O link "Embed" do Spotify ou YouTube

    // Opcional: Se quiser que cada usuário tenha a sua, descomente a linha abaixo
    // public String usuarioEmail;
}