
  const fileInput = document.getElementById('fileInput');
  const profilePic = document.getElementById('profilePic');

  fileInput.addEventListener('change', function(e) {
  const file = e.target.files[0];
if (file) {
  const reader = new FileReader();
  reader.onload = function(e) {
  profilePic.src = e.target.result;

  // Animação de sucesso
  profilePic.style.transform = 'scale(1.1)';
  setTimeout(() => {
  profilePic.style.transform = 'scale(1)';
}, 300);

  // Feedback visual
  showNotification('Foto atualizada com sucesso! ✨');
};
  reader.readAsDataURL(file);
}
});

  profilePic.style.transition = 'transform 0.3s ease';

  // Função para mostrar notificação
  function showNotification(message) {
  const notification = document.createElement('div');
  notification.textContent = message;
  notification.style.cssText = `
                position: fixed;
                top: 20px;
                right: 20px;
                background: linear-gradient(135deg, #d946ef, #a855f7);
                color: white;
                padding: 15px 25px;
                border-radius: 10px;
                box-shadow: 0 10px 30px rgba(168, 85, 247, 0.5);
                z-index: 1000;
                animation: slideIn 0.3s ease;
            `;
  document.body.appendChild(notification);

  setTimeout(() => {
  notification.style.animation = 'slideOut 0.3s ease';
  setTimeout(() => notification.remove(), 300);
}, 3000);
}

  // Adicionar animações CSS
  const style = document.createElement('style');
  style.textContent = `
  @keyframes slideIn {
  from{
  transform: translateX(400px);
  opacity: 0;
}
  to {
  transform: translateX(0);
  opacity: 1;
}
}
  @keyframes slideOut {
  from {
  transform: translateX(0);
  opacity: 1;
}
  to {
  transform: translateX(400px);
  opacity: 0;
}
}
  `;
  document.head.appendChild(style);

  // Adicionar funcionalidade aos botões de editar
  document.querySelectorAll('.edit-btn').forEach(btn => {
  btn.addEventListener('click', function() {
    showNotification('Função de edição em desenvolvimento! 🚀');

  });
});
