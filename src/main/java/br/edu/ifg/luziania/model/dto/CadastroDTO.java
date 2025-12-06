package br.edu.ifg.luziania.model.dto;

import java.util.Date;

public class CadastroDTO {
    private String email;
    private String senha;
    private String confirme;
    private String Nome;
    private Date DataNascimento;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirme() {
        return confirme;
    }

    public void setConfirme(String confirme) {
        this.confirme = confirme;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Date getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        DataNascimento = dataNascimento;
    }
}
