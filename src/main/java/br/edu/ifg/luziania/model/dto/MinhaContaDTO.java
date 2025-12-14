package br.edu.ifg.luziania.model.dto;

public class MinhaContaDTO {

    private String nomeCompleto;
    private String email;
    private String telefone;
    private String dataNascimento; // String para exibir já formatado
    private String planoAtual;
    private String membroDesde;

    // Getters e Setters
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getPlanoAtual() { return planoAtual; }
    public void setPlanoAtual(String planoAtual) { this.planoAtual = planoAtual; }

    public String getMembroDesde() { return membroDesde; }
    public void setMembroDesde(String membroDesde) { this.membroDesde = membroDesde; }
}