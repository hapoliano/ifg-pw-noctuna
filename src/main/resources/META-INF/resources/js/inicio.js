document.addEventListener("DOMContentLoaded", () => {

  // ======================================================
  // 1. 🔘 MENU DE PERFIL (Dropdown)
  // ======================================================
  const perfil = document.getElementById('perfil');
  const menuPerfil = document.getElementById('menuPerfil');

  if (perfil && menuPerfil) {
    // Alterna o menu ao clicar na foto
    perfil.addEventListener('click', function (e) {
      e.stopPropagation(); // Impede que o clique feche o menu imediatamente
      menuPerfil.classList.toggle('ativo');
    });

    // Fecha o menu se clicar em qualquer lugar fora dele
    document.addEventListener('click', function (e) {
      if (!perfil.contains(e.target) && !menuPerfil.contains(e.target)) {
        menuPerfil.classList.remove('ativo');
      }
    });
  }

  // ======================================================
  // 2. ❤️ SISTEMA DE FAVORITOS (Com Backend)
  // ======================================================
  const botoesFavorito = document.querySelectorAll(".fav-btn");

  /**
   * Envia a requisição para a API do Backend para salvar ou remover o favorito.
   */
  async function alternarFavoritoBackend(idMusica, titulo, img, src, btn) {
    try {
      // Monta o objeto que o Java (FavoritoDTO) espera
      const dados = {
        id: idMusica,       // ID do banco (ou null se for estática)
        titulo: titulo,
        capa: img,
        src: src,
        artista: "Desconhecido" // Você pode melhorar isso pegando do HTML se quiser
      };

      // Faz o POST para o servidor
      const resposta = await fetch('/api/favoritos', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
      });

      // Se der erro 401, o usuário não está logado
      if (resposta.status === 401) {
        alert("Você precisa estar logado para favoritar!");
        window.location.href = "/login";
        return;
      }

      // Se deu certo (200 OK)
      if (resposta.ok) {
        const json = await resposta.json();

        // O backend retorna { "favorito": true/false }
        if (json.favorito) {
          btn.classList.add("favorito");
        } else {
          btn.classList.remove("favorito");
        }
      } else {
        console.error("Erro no servidor ao favoritar.");
      }
    } catch (error) {
      console.error("Erro de conexão:", error);
    }
  }

  // Adiciona o evento de clique em CADA botão de coração
  botoesFavorito.forEach(botao => {
    botao.addEventListener("click", () => {
      const card = botao.closest(".music-card");
      if (!card) return;

      // Coleta os dados do HTML do card
      const titulo = card.querySelector("h3").innerText;

      // Tenta pegar a imagem (se não tiver, usa vazio)
      const imgTag = card.querySelector("img");
      const img = imgTag ? imgTag.src : "";

      // Busca o botão de Play para pegar o SRC e o ID
      const playBtn = card.querySelector(".play-btn");
      const src = playBtn ? playBtn.getAttribute("data-src") : "";

      // Pega o ID da música (se for música do banco)
      const idRaw = playBtn ? playBtn.getAttribute("data-id") : null;
      // Se idRaw existir, converte para número, senão envia null
      const idMusica = idRaw ? parseInt(idRaw) : null;

      // Chama a função que fala com o Java
      alternarFavoritoBackend(idMusica, titulo, img, src, botao);
    });
  });

  // ======================================================
  // 3. 🎵 PLAYER DE MÚSICA (Play/Pause, Barra e Tempo)
  // ======================================================
  const musicCards = document.querySelectorAll(".music-card");
  let audioAtual = null; // Guarda qual áudio está tocando agora
  let botaoAtual = null; // Guarda qual botão está com o ícone de Pause

  musicCards.forEach(card => {
    const btn = card.querySelector(".play-btn");
    const audio = card.querySelector("audio");
    const progressBar = card.querySelector(".progress-bar");
    const timer = card.querySelector(".timer");

    // Pega o link da música
    const src = btn.getAttribute("data-src");

    // --- LÓGICA DO CLICK NO PLAY ---
    btn.addEventListener("click", () => {
      // Se já tem uma música tocando e é diferente dessa, pausa a anterior
      if (audioAtual && audioAtual !== audio) {
        audioAtual.pause();
        if (botaoAtual) botaoAtual.classList.remove("tocando");
      }

      // Se o áudio ainda não tem fonte carregada, carrega agora
      // Verifica também se o src mudou (caso dinâmico)
      if (!audio.src || !audio.src.includes(src)) {
        audio.src = src;
      }

      // Alterna entre Tocar e Pausar
      if (audio.paused) {
        audio.play()
            .then(() => {
              btn.classList.add("tocando"); // Muda ícone para Pause
              audioAtual = audio;
              botaoAtual = btn;
            })
            .catch(erro => console.error("Erro ao dar play:", erro));
      } else {
        audio.pause();
        btn.classList.remove("tocando"); // Muda ícone para Play
        audioAtual = null;
        botaoAtual = null;
      }
    });

    // --- ATUALIZA A BARRA E O TEMPO (Enquanto toca) ---
    audio.addEventListener("timeupdate", () => {
      if (audio.duration) {
        // Porcentagem da barra
        const progress = (audio.currentTime / audio.duration) * 100;
        progressBar.value = progress;

        // Formatação do tempo (00:00)
        const minutes = Math.floor(audio.currentTime / 60);
        const seconds = Math.floor(audio.currentTime % 60);
        timer.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
      }
    });

    // --- ARRASTAR A BARRA (Seek) ---
    // Permite pular para uma parte da música movendo a barra
    progressBar.addEventListener("input", () => {
      if (audio.duration) {
        const seekTime = (progressBar.value / 100) * audio.duration;
        audio.currentTime = seekTime;
      }
    });

    // --- QUANDO A MÚSICA ACABA ---
    audio.addEventListener("ended", () => {
      btn.classList.remove("tocando");
      progressBar.value = 0;
      timer.textContent = "00:00";
      audioAtual = null;
      botaoAtual = null;
    });
  });

});