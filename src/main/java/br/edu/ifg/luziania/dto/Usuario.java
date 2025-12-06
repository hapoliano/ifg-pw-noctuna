package br.edu.ifg.luziania.dto;

public class Usuario {

    private String admin;
    private String mail;
    private String number;
    private String senha; // faltava este atributo

    public Usuario(String admin, String mail, String number, String senha) {
        this.admin = admin;
        this.mail = mail;
        this.number = number;
        this.senha = senha;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
