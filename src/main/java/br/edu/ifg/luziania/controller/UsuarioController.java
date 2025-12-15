package br.edu.ifg.luziania.controller;

// 1. Mude o import para usar o CadastroDTO
import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.CadastroDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cadastro") // Certifique-se que esta rota não conflita com CadastroController se ele ainda existir
public class UsuarioController {

    @Inject
    UsuarioBO usuarioBO;

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(CadastroDTO cadastroDTO) {

        boolean sucesso = usuarioBO.cadastrarUsuario(cadastroDTO);

        if (sucesso) {
            return Response.ok("{\"mensagem\": \"Cadastro realizado com sucesso!\"}").build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"mensagem\": \"Email já cadastrado.\"}")
                    .build();
        }
    }
}