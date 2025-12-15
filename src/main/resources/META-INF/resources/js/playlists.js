document.addEventListener("DOMContentLoaded", () => {

    // ======================================================
    // 🔘 Lógica do Menu de Perfil (Dropdown)
    // ======================================================
    const perfil = document.getElementById('perfil');
    const menuPerfil = document.getElementById('menuPerfil');

    if (perfil && menuPerfil) {
        perfil.addEventListener('click', function(e) {
            e.stopPropagation();
            menuPerfil.classList.toggle('ativo');
        });

        document.addEventListener('click', function(e) {
            if (!perfil.contains(e.target)) {
                menuPerfil.classList.remove('ativo');
            }
        });
    }
});