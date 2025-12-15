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
      // Pega o src correto, seja do data-src ou de onde estiver
      const playBtn = card.querySelector(".play-btn");
      const src = playBtn ? playBtn.getAttribute("data-src") : "";

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
  // 🎵 PLAYER DE MÚSICAS
  // ===============================
  const botoesPlay = document.querySelectorAll(".play-btn");
  let audioAtual = null;
  let botaoAtual = null;

  botoesPlay.forEach(botao => {
    botao.addEventListener("click", () => {
      const card = botao.closest(".music-card");
      if (!card) return;

      const audio = card.querySelector("audio");
      const progressBar = card.querySelector(".progress-bar");
      const timer = card.querySelector(".timer");
      const src = botao.getAttribute("data-src");

      if (!audio || !src) return;

      // Se mudar de música, pausa a anterior
      if (audioAtual && audioAtual !== audio) {
        audioAtual.pause();
        if (botaoAtual) botaoAtual.classList.remove("tocando");
      }

      // Carrega a nova fonte se necessário
      if (!audio.src || audio.src !== window.location.origin + src && !audio.src.endsWith(src)) {
        audio.src = src;
      }

      // Play/Pause
      if (audio.paused) {
        audio.play()
            .then(() => {
              botao.classList.add("tocando");
              audioAtual = audio;
              botaoAtual = botao;
            })
            .catch(e => console.error("Erro ao reproduzir:", e));
      } else {
        audio.pause();
        botao.classList.remove("tocando");
        audioAtual = null;
        botaoAtual = null;
      }

      // Atualiza a barra de progresso
      audio.ontimeupdate = () => {
        if (progressBar && audio.duration) {
          const progress = (audio.currentTime / audio.duration) * 100;
          progressBar.value = progress;

          const min = Math.floor(audio.currentTime / 60);
          const sec = Math.floor(audio.currentTime % 60);
          if (timer) timer.textContent = `${min}:${sec < 10 ? '0' + sec : sec}`;
        }
      };

      // Permite arrastar a barra
      if (progressBar) {
        progressBar.oninput = () => {
          if (audio.duration) {
            audio.currentTime = (progressBar.value / 100) * audio.duration;
          }
        };
      }

      // Reseta ao terminar
      audio.onended = () => {
        botao.classList.remove("tocando");
        if(progressBar) progressBar.value = 0;
        if(timer) timer.textContent = "00:00";
      };
    });
  });

  atualizarEstadoFavorito();

});