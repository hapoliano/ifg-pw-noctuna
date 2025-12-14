// Aguarda o DOM carregar completamente antes de buscar os elementos
document.addEventListener("DOMContentLoaded", function() {

    const form = document.getElementById("formCadastro");

    // Verifica se o formulário existe na página para evitar erros
    if (form) {
        form.addEventListener("submit", function(event){
            event.preventDefault(); // Impede o envio padrão do HTML

            // Captura os valores dos inputs
            const nome = document.getElementById("nome").value;
            const email = document.getElementById("email").value;
            const senha = document.getElementById("senha").value;
            const confirmar = document.getElementById("confirmar").value;
            const nascimento = document.getElementById("Nascimento").value;

            // Validação simples no Front-end
            if(senha !== confirmar) {
                alert("As senhas não coincidem!");
                return;
            }

            // Monta o objeto JSON (deve bater com o DTO Java)
            const dados = {
                nome: nome,
                email: email,
                senha: senha,
                dataNascimento: nascimento
            };

            // Faz a requisição para o Backend
            fetch("/cadastro", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dados)
            })
                .then(response => {
                    if(response.status === 201) {
                        alert("Cadastro realizado com sucesso!");
                        window.location.href = "/login"; // Redireciona para login
                    } else if (response.status === 409) {
                        alert("Este e-mail já está cadastrado.");
                    } else {
                        alert("Erro ao cadastrar. Código: " + response.status);
                    }
                })
                .catch(error => {
                    console.error("Erro na requisição:", error);
                    alert("Erro de conexão com o servidor.");
                });
        });
    }
});