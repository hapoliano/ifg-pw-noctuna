package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.model.dto.LoginDTO;
import br.edu.ifg.luziania.model.dto.MinhaContaDTO; // Importe o DTO correto
import br.edu.ifg.luziania.model.dto.CadastroDTO; // Seu DTO de cadastro (se estiver usando)
import br.edu.ifg.luziania.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public boolean cadastrarUsuario(CadastroDTO dto) {
        if (Usuario.findByEmail(dto.getEmail()) != null) {
            return false;
        }
        Usuario novo = new Usuario();
        novo.nome = dto.getNome();
        novo.email = dto.getEmail();
        novo.senha = dto.getSenha();
        novo.dataNascimento = dto.getDataNascimento(); // Agora salvamos a data!
        // novo.telefone = ... (se tiver no cadastro)
        novo.persist();
        return true;
    }

    // NOVO MÉTODO: Busca os dados para a tela Minha Conta
    public MinhaContaDTO buscarDadosMinhaConta(String email) {
        Usuario usuario = Usuario.findByEmail(email);

        if (usuario == null) {
            return null;
        }

        MinhaContaDTO dto = new MinhaContaDTO();
        dto.setNomeCompleto(usuario.nome);
        dto.setEmail(usuario.email);
        dto.setTelefone(usuario.telefone != null ? usuario.telefone : "Não informado");

        // Formata a data (ex: 2025-01-01 vira 01/01/2025)
        if (usuario.dataNascimento != null) {
            dto.setDataNascimento(usuario.dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            dto.setDataNascimento("--/--/----");
        }

        dto.setPlanoAtual(usuario.planoAtual != null ? usuario.planoAtual : "Gratuito");
        dto.setMembroDesde("2025"); // Exemplo estático ou pegue do banco se tiver created_at

        return dto;
    }

    public Usuario validarLogin(String email, String senha) {
        Usuario usuario = Usuario.findByEmail(email);

        // Verifica se achou o usuário e se a senha é igual
        // Obs: Em um app real, usaríamos BCrypt para comparar hashes, não texto puro
        if (usuario != null && usuario.senha.equals(senha)) {
            return usuario;
        }
        return null;
    }

    public boolean autenticar(LoginDTO dto) {
        Usuario usuario = Usuario.findByEmail(dto.getEmail());

        // Verifica se o usuário existe e se a senha bate
        if (usuario != null && usuario.senha.equals(dto.getSenha())) {
            return true;
        }
        return false;
    }
}