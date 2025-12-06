package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.dto.LoginDTO;
import br.edu.ifg.luziania.dto.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LoginService {


    private List<Usuario> usuarios = new ArrayList<>();

    public LoginService() {

        usuarios.add(new Usuario("Admin", "exemplo@teste.com", "123456", "1234"));
    }


    public boolean autenticar(LoginDTO loginDTO) {
        for (Usuario u : usuarios) {
            if (u.getMail().equals(loginDTO.getEmail()) && u.getSenha().equals(loginDTO.getSenha())) {
                return true;
            }
        }
        return false;
    }
}
