package br.edu.ifg.luziania.model.dto;

public class UsuarioDTO {
    private String nome;
    private String email;
    private String planoAtual;
    private String proximaCobranca;
    private String senha; // atributo adicionado

    public UsuarioDTO() {}

    public UsuarioDTO(String nome, String email, String planoAtual, String proximaCobranca, String senha) {
        this.nome = nome;
        this.email = email;
        this.planoAtual = planoAtual;
        this.proximaCobranca = proximaCobranca;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPlanoAtual() { return planoAtual; }
    public void setPlanoAtual(String planoAtual) { this.planoAtual = planoAtual; }

    public String getProximaCobranca() { return proximaCobranca; }
    public void setProximaCobranca(String proximaCobranca) { this.proximaCobranca = proximaCobranca; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
