package br.edu.ifg.luziania.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class Musica extends PanacheEntity {

    public String titulo;
    public String artista;

    // Arquivo de Áudio (MP3)
    @Lob
    public byte[] dados;
    public String nomeArquivo;

    // NOVO: Arquivo da Capa (Imagem)
    @Lob
    public byte[] capa;

    public Musica() {}
}