package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.dto.Usuario;
import com.seuprojeto.dto.UsuarioDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();

    public UsuarioService() {
        usuarios.add(new Usuario("Admin", "admin@teste.com", "123456", "1234"));
    }

    public boolean cadastrarUsuario(UsuarioDTO usuarioDTO) {
        for (Usuario u : usuarios) {
            if (u.getMail().equals(usuarioDTO.getEmail())) {
                return false;
            }
        }

        Usuario novoUsuario = new Usuario(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getPlanoAtual(),
                usuarioDTO.getSenha()
        );
        usuarios.add(novoUsuario);
        return true;
    }

}

