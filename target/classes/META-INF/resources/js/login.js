document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");

    if (form) {
        form.addEventListener("submit", function(event){
            event.preventDefault();

            // Pega os inputs
            const email = document.getElementById("email").value;

            // CORREÇÃO AQUI: O ID no seu HTML é "senha", não "password"
            const senha = document.getElementById("senha").value;

            const dados = {
                email: email,
                senha: senha
            };

            fetch("/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dados)
            })
                .then(response => {
                    if(response.ok) {
                        window.location.href = "/minha-conta";
                    } else {
                        alert("Email ou senha incorretos!");
                    }
                })
                .catch(error => {
                    console.error("Erro:", error);
                    alert("Erro de conexão.");
                });
        });
    }
});