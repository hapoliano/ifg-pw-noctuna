package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.model.dto.CadastroDTO; // Importe o DTO correto
import br.edu.ifg.luziania.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public boolean cadastrarUsuario(CadastroDTO dto) {

        // Verifica se já existe
        if (Usuario.findByEmail(dto.getEmail()) != null) {
            return false;
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.nome = dto.getNome();
        novoUsuario.email = dto.getEmail();
        novoUsuario.senha = dto.getSenha(); // Em produção, lembre-se de criptografar a senha!
        // novoUsuario.planoAtual = "Gratuito"; // Defina um padrão se necessário

        novoUsuario.persist();
        return true;
    }
}