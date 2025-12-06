  // Elementos DOM
  const volumeSlider = document.getElementById('volumeSlider');
  const volumeValue = document.getElementById('volumeValue');
  const colorOptions = document.querySelectorAll('.color-option');
  const saveBtn = document.getElementById('saveBtn');
  const logoutAllBtn = document.getElementById('logoutAll');
  const deleteAccountBtn = document.getElementById('deleteAccount');
  const checkboxes = document.querySelectorAll('input[type="checkbox"]');
  const selects = document.querySelectorAll('select');

  function showNotification(message) {
  const notification = document.createElement('div');
  notification.className = 'notification';
  notification.textContent = message;
  document.body.appendChild(notification);

  setTimeout(function() {
  notification.classList.add('hide');
  setTimeout(function() {
  document.body.removeChild(notification);
}, 300);
}, 3000);
}

  volumeSlider.addEventListener('input', function() {
  volumeValue.textContent = this.value + '%';
});

  colorOptions.forEach(function(option) {
  option.addEventListener('click', function() {
    colorOptions.forEach(function(opt) {
      opt.classList.remove('active');
    });
    this.classList.add('active');
    showNotification('🎨 Cor alterada com sucesso!');
  });
});

  saveBtn.addEventListener('click', function() {
  saveBtn.textContent = '⏳ Salvando...';
  saveBtn.style.opacity = '0.7';

  setTimeout(function() {
  saveBtn.textContent = '✅ Salvo!';
  showNotification('✨ Configurações salvas com sucesso!');

  setTimeout(function() {
  saveBtn.textContent = '💾 Salvar Alterações';
  saveBtn.style.opacity = '1';
}, 2000);
}, 1000);
});

  logoutAllBtn.addEventListener('click', function() {
  if (confirm('Deseja realmente sair de todos os dispositivos?')) {
  showNotification('🚪 Saindo de todos os dispositivos...');
}
});

  deleteAccountBtn.addEventListener('click', function() {
  if (confirm('⚠️ ATENÇÃO! Esta ação é irreversível. Deseja realmente excluir sua conta?')) {
  if (confirm('Tem certeza absoluta? Todos os seus dados serão perdidos!')) {
  showNotification('❌ Conta excluída. Redirecionando...');
}
}
});

  checkboxes.forEach(function(checkbox) {
  checkbox.addEventListener('change', function() {
    const label = this.closest('.setting-item').querySelector('.setting-label').textContent;
    const status = this.checked ? 'ativado' : 'desativado';
    showNotification(label + ' ' + status + '!');
  });
});

  selects.forEach(function(select) {
  select.addEventListener('change', function() {
    const label = this.closest('.setting-item').querySelector('.setting-label').textContent;
    showNotification(label + ' atualizado!');
  });
});

  console.log('⚙️ Noctuna Configurações - Sistema carregado!');
