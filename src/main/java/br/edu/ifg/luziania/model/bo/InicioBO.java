package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dao.MusicaDAO; // Importe o DAO
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Musica;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject; // Importe o Inject
import java.util.List;

@ApplicationScoped
public class InicioBO {

    @Inject
    MusicaDAO musicaDAO; // Injeta o DAO para usar seus métodos

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

    // Alterei o retorno para List<Musica> para ser compatível com o DAO e sua View
    public List<Musica> listasMusicas() {
        return musicaDAO.listarTodos();
    }
}