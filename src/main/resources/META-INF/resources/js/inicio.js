<script>
  // ===============================
  // 🔘 MENU DE PERFIL
  // ===============================
  const perfil = document.getElementById('perfil');
  const menuPerfil = document.getElementById('menuPerfil');

  if (perfil && menuPerfil) {
    perfil.addEventListener('click', function(e) {
      console.log('Clicou no perfil!'); // DEBUG
      e.stopPropagation();
      menuPerfil.classList.toggle('ativo');
    });

    // Fechar menu ao clicar fora
    document.addEventListener('click', function(e) {
      if (!perfil.contains(e.target)) {
        menuPerfil.classList.remove('ativo');
      }
    });

    menuPerfil.addEventListener('click', function(e) {
      e.stopPropagation();
    });
  } else {
    console.error('Elementos perfil ou menuPerfil não encontrados!');
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
      const titulo = card.querySelector("h3").textContent;
      const img = card.querySelector("img").getAttribute("src");
      const src = card.querySelector(".play-btn").getAttribute("data-src");

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

  botoesPlay.forEach(botao => {
    botao.addEventListener("click", () => {
      const card = botao.closest(".music-card");
      const audio = card.querySelector("audio");
      const src = botao.getAttribute("data-src");

      if (!src) return;

      if (audioAtual && audioAtual !== audio) {
        audioAtual.pause();
        audioAtual.closest(".music-card").querySelector(".play-btn").classList.remove("tocando");
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

  document.addEventListener("DOMContentLoaded", atualizarEstadoFavorito);
</script>
