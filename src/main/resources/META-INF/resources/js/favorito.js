document.addEventListener("DOMContentLoaded", () => {

    // ===============================
    // 1. MENU DE PERFIL
    // ===============================
    const perfil = document.getElementById('perfil');
    const menuPerfil = document.getElementById('menuPerfil');

    if (perfil && menuPerfil) {
        perfil.addEventListener('click', function (e) {
            e.stopPropagation();
            menuPerfil.classList.toggle('ativo');
        });

        document.addEventListener('click', function (e) {
            if (!perfil.contains(e.target) && !menuPerfil.contains(e.target)) {
                menuPerfil.classList.remove('ativo');
            }
        });
    }

    // ===============================
    // 2. SISTEMA DE FAVORITOS (Com Remoção Automática)
    // ===============================
    const botoesFavorito = document.querySelectorAll(".fav-btn");

    async function removerFavoritoBackend(idMusica, titulo, btn) {
        try {
            // Monta o objeto (mesmo DTO do início)
            const dados = {
                id: idMusica,
                titulo: titulo,
                capa: "",
                src: "",
                artista: ""
            };

            const resposta = await fetch('/favoritos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });

            if (resposta.status === 401) {
                window.location.href = "/login";
                return;
            }

            if (resposta.ok) {
                const json = await resposta.json();

                // Se json.favorito for false, significa que foi removido
                if (!json.favorito) {
                    btn.classList.remove("favorito");

                    // EFEITO VISUAL: Remove o card da tela suavemente
                    const card = btn.closest(".music-card");
                    card.style.transition = "all 0.5s ease";
                    card.style.opacity = "0";
                    card.style.transform = "scale(0.8)";

                    setTimeout(() => {
                        card.remove();
                        // Se não sobrar cards, recarrega para mostrar a mensagem de vazio (opcional)
                        if (document.querySelectorAll(".music-card").length === 0) {
                            location.reload();
                        }
                    }, 500);
                } else {
                    // Caso raro: se por algum motivo ele adicionar de novo
                    btn.classList.add("favorito");
                }
            }
        } catch (error) {
            console.error("Erro ao desfavoritar:", error);
        }
    }

    botoesFavorito.forEach(botao => {
        botao.addEventListener("click", () => {
            const card = botao.closest(".music-card");
            const titulo = card.querySelector("h3").innerText;
            const playBtn = card.querySelector(".play-btn");

            const idRaw = playBtn ? playBtn.getAttribute("data-id") : null;
            const idMusica = idRaw ? parseInt(idRaw) : null;

            // Chama a função
            removerFavoritoBackend(idMusica, titulo, botao);
        });
    });

    // ===============================
    // 3. PLAYER DE MÚSICA (Igual ao Início)
    // ===============================
    const musicCards = document.querySelectorAll(".music-card");
    let audioAtual = null;
    let botaoAtual = null;

    musicCards.forEach(card => {
        const btn = card.querySelector(".play-btn");
        const audio = card.querySelector("audio");
        const progressBar = card.querySelector(".progress-bar");
        const timer = card.querySelector(".timer");
        const src = btn.getAttribute("data-src");

        btn.addEventListener("click", () => {
            if (audioAtual && audioAtual !== audio) {
                audioAtual.pause();
                if (botaoAtual) botaoAtual.classList.remove("tocando");
            }

            if (!audio.src || !audio.src.includes(src)) {
                audio.src = src;
            }

            if (audio.paused) {
                audio.play()
                    .then(() => {
                        btn.classList.add("tocando");
                        audioAtual = audio;
                        botaoAtual = btn;
                    })
                    .catch(e => console.error(e));
            } else {
                audio.pause();
                btn.classList.remove("tocando");
                audioAtual = null;
                botaoAtual = null;
            }
        });

        audio.addEventListener("timeupdate", () => {
            if (audio.duration) {
                const progress = (audio.currentTime / audio.duration) * 100;
                progressBar.value = progress;
                const min = Math.floor(audio.currentTime / 60);
                const sec = Math.floor(audio.currentTime % 60);
                timer.textContent = `${min}:${sec < 10 ? '0' + sec : sec}`;
            }
        });

        progressBar.addEventListener("input", () => {
            if (audio.duration) {
                audio.currentTime = (progressBar.value / 100) * audio.duration;
            }
        });

        audio.addEventListener("ended", () => {
            btn.classList.remove("tocando");
            progressBar.value = 0;
            timer.textContent = "00:00";
        });
    });
});