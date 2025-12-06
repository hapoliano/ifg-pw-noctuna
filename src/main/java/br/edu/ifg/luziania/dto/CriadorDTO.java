package br.edu.ifg.luziania.dto;

public class CriadorDTO {

    private String nome; // Faltava declarar o atributo

    public CriadorDTO() {}

    public CriadorDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
