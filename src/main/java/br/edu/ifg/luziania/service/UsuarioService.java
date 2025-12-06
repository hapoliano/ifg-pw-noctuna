package br.edu.ifg.luziania.service;

 // Importe a nova entidade
 // Verifique o pacote correto do seu DTO
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional; // Importante!

@ApplicationScoped
public class UsuarioService {

    @Transactional // Abre uma transação no banco para salvar
    public boolean cadastrarUsuario(UsuarioDTO usuarioDTO) {

        // Verifica no banco se o email já existe
        if (Usuario.findByEmail(usuarioDTO.getEmail()) != null) {
            return false; // Email já cadastrado
        }

        // Cria o usuário e preenche os dados
        Usuario novoUsuario = new Usuario();
        novoUsuario.nome = usuarioDTO.getNome();
        novoUsuario.email = usuarioDTO.getEmail();
        novoUsuario.senha = usuarioDTO.getSenha();
        novoUsuario.planoAtual = usuarioDTO.getPlanoAtual();

        // Salva no PostgreSQL
        novoUsuario.persist();

        return true;
    }
}