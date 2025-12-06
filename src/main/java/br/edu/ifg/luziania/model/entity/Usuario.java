package br.edu.ifg.luziania.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity // Isso diz ao Quarkus que esta classe é uma tabela
public class Usuario extends PanacheEntity {

    // O ID é criado automaticamente pelo PanacheEntity

    public String nome;
    public String email;
    public String senha;
    public String planoAtual;

    // O JPA exige um construtor vazio
    public Usuario() {}

    // Método auxiliar para buscar por email
    public static Usuario findByEmail(String email){
        return find("email", email).firstResult();
    }
}
