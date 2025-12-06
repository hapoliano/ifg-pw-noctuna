// Seleciona o botão de perfil e o menu
const perfilBtn = document.querySelector('.perfil');
const menuPerfil = document.querySelector('.menu-perfil');

// Alterna o menu ao clicar no botão de perfil
perfilBtn.addEventListener('click', (event) => {
    event.stopPropagation(); // evita que o clique feche o menu
    menuPerfil.classList.toggle('ativo');
});

// Fecha o menu ao clicar fora
document.addEventListener('click', () => {
    if (menuPerfil.classList.contains('ativo')) {
        menuPerfil.classList.remove('ativo');
    }
});

// Fecha o menu ao rolar a página
window.addEventListener('scroll', () => {
    if (menuPerfil.classList.contains('ativo')) {
        menuPerfil.classList.remove('ativo');
    }
});
