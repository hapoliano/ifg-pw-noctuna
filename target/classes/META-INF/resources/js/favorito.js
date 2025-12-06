// ========================================
// 🎵 MOSTRAR E GERENCIAR FAVORITOS SALVOS
// ========================================

const container = document.getElementById("listaFavoritos");
let favoritos = JSON.parse(localStorage.getItem("favoritos")) || [];

// Salvar favoritos no localStorage
function salvarFavoritos() {
    localStorage.setItem("favoritos", JSON.stringify(favoritos));
}

// Renderizar os cards das músicas favoritas
function renderizarFavoritos() {
    container.innerHTML = "";

    if (favoritos.length === 0) {
        container.innerHTML =
            "<p style='text-align:center; color:#aaa;'>Nenhuma música favorita ainda 💔</p>";
        return;
    }

    favoritos.forEach(fav => {
        const card = document.createElement("div");
        card.classList.add("music-card");
        card.innerHTML = `
      <h3>${fav.titulo}</h3>
      <img src="${fav.img}" alt="${fav.titulo}">
      <div class="progress-container">
        <span class="timer">00:00</span>
        <input type="range" class="progress-bar" value="0" min="0" max="100">
      </div>
      <button class="play-btn" data-src="${fav.src}"></button>
      <audio></audio>
      <button class="fav-btn favorito"></button>
    `;
        container.appendChild(card);
    });

    ativarFuncoes();
}

// ===============================
// ⚙️ PLAYER & FAVORITAR/REMOVER
// ===============================

function ativarFuncoes() {
    const botoesFav = document.querySelectorAll(".fav-btn");
    const botoesPlay = document.querySelectorAll(".play-btn");
    let audioAtual = null;

    // Remover favorito
    botoesFav.forEach(botao => {
        botao.addEventListener("click", () => {
            const card = botao.closest(".music-card");
            const titulo = card.querySelector("h3").textContent;

            favoritos = favoritos.filter(f => f.titulo !== titulo);
            salvarFavoritos();
            renderizarFavoritos();
        });
    });

    // Player
    botoesPlay.forEach(botao => {
        botao.addEventListener("click", () => {
            const card = botao.closest(".music-card");
            const audio = card.querySelector("audio");
            const src = botao.getAttribute("data-src");

            if (!src) return;

            if (audioAtual && audioAtual !== audio) {
                audioAtual.pause();
                audioAtual.closest(".music-card")
                    .querySelector(".play-btn")
                    .classList.remove("tocando");
            }

            if (audio.src !== src) {
                audio.src = src;
            }

            if (audio.paused) {
                audio.play();
                botao.classList.add("tocando");
                audioAtual = audio;
            } else {
                audio.pause();
                botao.classList.remove("tocando");
                audioAtual = null;
            }
        });
    });
}

// ===============================
// 🚀 INICIALIZAÇÃO
// ===============================

renderizarFavoritos();
