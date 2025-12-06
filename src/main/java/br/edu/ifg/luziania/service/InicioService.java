package br.edu.ifg.luziania.service;

import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InicioService {


    public UsuarioDTO getUsuarioLogado(String email) {

        if ("teste@teste.com".equals(email)) {
            return new UsuarioDTO(
                    "Yasmin Almeida",
                    "teste@teste.com",
                    "Plano Premium",
                    "25/10/2025",
                    "1234"
            );
        }
        return null;
    }
}
