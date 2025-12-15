package br.edu.ifg.luziania.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class Musica extends PanacheEntity {

    public String titulo;
    public String artista;

    // @Lob indica que este campo guardará um arquivo grande (BLOB)
    @Lob
    public byte[] dados;

    public String nomeArquivo; // Ex: "minhamusica.mp3"

    public Musica() {}
}