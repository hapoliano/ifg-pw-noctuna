package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dto.CadastroDTO;
import br.edu.ifg.luziania.model.dto.LoginDTO;
import br.edu.ifg.luziania.model.dto.MinhaContaDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class UsuarioBO {

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

    @Transactional
    public boolean atualizarNomeCompleto(String emailLogado, String novoNome) {
        Usuario usuario = Usuario.findByEmail(emailLogado);

        if (usuario == null) {
            return false;
        }

        if (novoNome != null && !novoNome.trim().isEmpty()) {
            usuario.nome = novoNome;
            usuario.persist();
            return true;
        }
        return false;
    }

    @Transactional
    public boolean atualizarEmail(String emailLogado, String novoEmail) {
        Usuario usuario = Usuario.findByEmail(emailLogado);

        if (usuario == null || novoEmail == null || novoEmail.trim().isEmpty()) {
            return false;
        }

        String emailAjustado = novoEmail.trim().toLowerCase();

        // 1. Verificar se o novo email já existe para OUTRO usuário
        Usuario usuarioExistente = Usuario.findByEmail(emailAjustado);
        if (usuarioExistente != null && !usuarioExistente.id.equals(usuario.id)) {
            // Este email já está em uso por outra conta
            return false;
        }

        // 2. Atualizar o email
        usuario.email = emailAjustado;
        usuario.persist();

        return true;
    }

    @Transactional
    public boolean atualizarTelefone(String emailLogado, String novoTelefone) {
        Usuario usuario = Usuario.findByEmail(emailLogado);

        if (usuario == null) {
            return false;
        }

        // Se a string for vazia ou null, define o telefone como null no banco
        usuario.telefone = novoTelefone != null && !novoTelefone.trim().isEmpty() ? novoTelefone : null;
        usuario.persist();
        return true;
    }

    @Transactional
    public boolean atualizarDataNascimento(String emailLogado, String novaDataString) {
        Usuario usuario = Usuario.findByEmail(emailLogado);

        if (usuario == null) {
            return false;
        }

        if (novaDataString == null || novaDataString.trim().isEmpty()) {
            usuario.dataNascimento = null;
            usuario.persist();
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate novaData = LocalDate.parse(novaDataString.trim(), formatter);

            usuario.dataNascimento = novaData;
            usuario.persist();
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            System.err.println("Erro ao converter data: " + e.getMessage());
            return false;
        }
    }

    public Usuario validarLogin(String email, String senha) {
        Usuario usuario = Usuario.findByEmail(email);

        if (usuario != null && usuario.senha.equals(senha)) {
            return usuario;
        }
        return null;
    }

    public boolean autenticar(LoginDTO dto) {
        Usuario usuario = Usuario.findByEmail(dto.getEmail());

        if (usuario != null && usuario.senha.equals(dto.getSenha())) {
            return true;
        }
        return false;
    }
}
