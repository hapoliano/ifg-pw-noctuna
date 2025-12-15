document.addEventListener("DOMContentLoaded", () => {

  // ===============================
  // 🔘 MENU DE PERFIL
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
  // ❤️ SISTEMA DE FAVORITOS
  // ===============================
  const botoesFavorito = document.querySelectorAll(".fav-btn");

  function salvarFavoritos(favoritos) {
    localStorage.setItem("favoritos", JSON.stringify(favoritos));
  }

  function carregarFavoritos() {
    return JSON.parse(localStorage.getItem("favoritos")) || [];
  }

  function atualizarEstadoFavorito() {
    const favoritos = carregarFavoritos();
    document.querySelectorAll(".music-card").forEach(card => {
      const titulo = card.querySelector("h3").textContent;
      const btn = card.querySelector(".fav-btn");

      if (!btn) return;

      if (favoritos.some(f => f.titulo === titulo)) {
        btn.classList.add("favorito");
      } else {
        btn.classList.remove("favorito");
      }
    });
  }

  botoesFavorito.forEach(botao => {
    botao.addEventListener("click", () => {
      const card = botao.closest(".music-card");
      if (!card) return;

      const titulo = card.querySelector("h3").textContent;
      const img = card.querySelector("img")?.src;
      const src = card.querySelector(".play-btn")?.getAttribute("data-src");

      let favoritos = carregarFavoritos();
      const jaExiste = favoritos.some(f => f.titulo === titulo);

      if (jaExiste) {
        favoritos = favoritos.filter(f => f.titulo !== titulo);
        botao.classList.remove("favorito");
      } else {
        favoritos.push({ titulo, img, src });
        botao.classList.add("favorito");
      }

      salvarFavoritos(favoritos);
    });
  });

  // ===============================
  // 🎵 PLAYER DE MÚSICAS (Corrigido)
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

    // 1. Botão Play/Pause
    btn.addEventListener("click", () => {
      // Se tentar tocar uma música diferente, pausa a anterior
      if (audioAtual && audioAtual !== audio) {
        audioAtual.pause();
        if (botaoAtual) botaoAtual.classList.remove("tocando");
      }

      // Carrega a fonte se ainda não tiver
      if (!audio.src && src) {
        audio.src = src;
      }

      // Lógica de Tocar/Pausar
      if (audio.paused) {
        audio.play();
        btn.classList.add("tocando");
        audioAtual = audio;
        botaoAtual = btn;
      } else {
        audio.pause();
        btn.classList.remove("tocando");
        audioAtual = null;
        botaoAtual = null;
      }
    });

    // 2. Atualizar Barra e Tempo enquanto toca
    audio.addEventListener("timeupdate", () => {
      if (audio.duration) {
        // Atualiza a barra (0 a 100)
        const progress = (audio.currentTime / audio.duration) * 100;
        progressBar.value = progress;

        // Atualiza o texto do tempo (00:00)
        const minutes = Math.floor(audio.currentTime / 60);
        const seconds = Math.floor(audio.currentTime % 60);
        timer.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
      }
    });

    // 3. Permitir arrastar a barra (Seek)
    progressBar.addEventListener("input", () => {
      if (audio.duration) {
        const seekTime = (progressBar.value / 100) * audio.duration;
        audio.currentTime = seekTime;
      }
    });

    // 4. Resetar quando a música acabar
    audio.addEventListener("ended", () => {
      btn.classList.remove("tocando");
      progressBar.value = 0;
      timer.textContent = "00:00";
      audioAtual = null;
      botaoAtual = null;
    });
  });

  // Inicializa o estado dos favoritos ao carregar
  atualizarEstadoFavorito();

});