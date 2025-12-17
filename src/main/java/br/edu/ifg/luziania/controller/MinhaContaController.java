package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.MinhaContaDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/minha-conta")
public class MinhaContaController {

    @Inject
    Template minhaconta;

    @Inject
    UsuarioBO usuarioBO;

    @GET
    @Produces(MediaType.TEXT_HTML)
    // O @CookieParam tenta pegar o valor do cookie "usuario_logado"
    public Response get(@CookieParam("usuario_logado") String emailLogado) {

        // Se o cookie estiver vazio, chuta o usuário para o login
        if (emailLogado == null || emailLogado.isEmpty()) {
            return Response.seeOther(URI.create("/login")).build();
        }

        MinhaContaDTO dados = usuarioBO.buscarDadosMinhaConta(emailLogado);

        if (dados == null) {
            // Se tem cookie mas não achou no banco (ex: usuario deletado), volta pro login
            return Response.seeOther(URI.create("/login")).build();
        }

        // Retorna a página preenchida
        return Response.ok(minhaconta.data("perfil", dados)).build();
    }

    @PUT
    @Path("/nome-completo")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarNomeCompleto(@CookieParam("usuario_logado") String emailLogado, String novoNome) {
        if (emailLogado == null || emailLogado.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        boolean sucesso = usuarioBO.atualizarNomeCompleto(emailLogado, novoNome);

        if (sucesso) {
            return Response.ok("{\"mensagem\": \"Nome completo atualizado com sucesso\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"erro\": \"Não foi possível atualizar o nome completo ou o campo está vazio.\"}").build();
        }
    }

    @PUT
    @Path("/email")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarEmail(@CookieParam("usuario_logado") String emailLogado, String novoEmail) {
        if (emailLogado == null || emailLogado.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String novoEmailLimpo = novoEmail != null ? novoEmail.trim() : "";

        // Verifica se o email está sendo alterado para algo diferente
        if (novoEmailLimpo.equalsIgnoreCase(emailLogado.trim())) {
            return Response.ok("{\"mensagem\": \"Email inalterado.\"}").build();
        }

        boolean sucesso = usuarioBO.atualizarEmail(emailLogado, novoEmailLimpo);

        if (sucesso) {
            // Recomenda-se que o frontend lide com o redirecionamento/relogin após a mudança de chave (email)
            return Response.ok("{\"mensagem\": \"Email atualizado com sucesso. Você pode precisar fazer login novamente.\"}").build();
        } else {
            // Verifica a causa do erro para retornar um código HTTP mais preciso
            Usuario usuarioExistente = Usuario.findByEmail(novoEmailLimpo);
            if (usuarioExistente != null) {
                return Response.status(Response.Status.CONFLICT).entity("{\"erro\": \"O email fornecido já está em uso por outra conta.\"}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"erro\": \"Não foi possível atualizar o email ou o campo está vazio.\"}").build();
        }
    }

    // Endpoint para atualizar o telefone
    @PUT
    @Path("/telefone")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarTelefone(@CookieParam("usuario_logado") String emailLogado, String novoTelefone) {
        if (emailLogado == null || emailLogado.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        boolean sucesso = usuarioBO.atualizarTelefone(emailLogado, novoTelefone);

        if (sucesso) {
            return Response.ok("{\"mensagem\": \"Telefone atualizado com sucesso\"}").build();
        } else {
            // Em caso de erro do banco de dados (raro aqui), mas mantendo a lógica de erro
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"erro\": \"Não foi possível atualizar o telefone.\"}").build();
        }
    }

    @PUT
    @Path("/data-nascimento")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarDataNascimento(@CookieParam("usuario_logado") String emailLogado, String novaData) {
        if (emailLogado == null || emailLogado.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        boolean sucesso = usuarioBO.atualizarDataNascimento(emailLogado, novaData);

        if (sucesso) {
            return Response.ok("{\"mensagem\": \"Data de Nascimento atualizada com sucesso\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"erro\": \"Formato de data inválido. Use DD/MM/AAAA (ou erro interno).\"}").build();
        }
    }
}