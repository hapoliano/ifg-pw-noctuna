package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dto.LoginDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginBO {

    public boolean autenticar(LoginDTO loginDTO) {
        // Busca o usuário no banco pelo email
        Usuario usuarioEncontrado = Usuario.findByEmail(loginDTO.getEmail());

        if (usuarioEncontrado != null) {
            // Verifica se a senha está correta
            return usuarioEncontrado.senha.equals(loginDTO.getSenha());
        }

        return false;
    }
}