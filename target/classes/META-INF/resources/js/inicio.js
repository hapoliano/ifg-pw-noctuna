<script>
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

} else {
  console.error('Elementos #perfil ou #menuPerfil não encontrados!');
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
  // 🎵 PLAYER DE MÚSICAS
  // ===============================
  const botoesPlay = document.querySelectorAll(".play-btn");
  let audioAtual = null;

  botoesPlay.forEach(botao => {
  botao.addEventListener("click", () => {
  const card = botao.closest(".music-card");
  if (!card) return;

  const audio = card.querySelector("audio");
  const src = botao.getAttribute("data-src");

  if (!audio || !src) return;

  // Pausa áudio anterior
  if (audioAtual && audioAtual !== audio) {
  audioAtual.pause();
  const botaoAnterior = audioAtual.closest(".music-card").querySelector(".play-btn");
  if (botaoAnterior) botaoAnterior.classList.remove("tocando");
}

  // Troca a música
  if (audio.src !== src) {
  audio.src = src;
}

  // Play/Pause
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

  atualizarEstadoFavorito();

}); // ← FECHAMENTO DO DOMContentLoaded
</script>
