package br.edu.ifg.luziania.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate; // Usaremos LocalDate para data

@Entity
public class Usuario extends PanacheEntity {

    public String nome;
    public String email;
    public String senha;
    public String planoAtual;

    // Novos campos necessários para a tela Minha Conta
    public String telefone;
    public LocalDate dataNascimento;

    public Usuario() {}

    public static Usuario findByEmail(String email){
        return find("email", email).firstResult();
    }
}