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

// Função para mostrar notificação (Versão Atualizada com cores de erro/sucesso)
function showNotification(message, isError = false) {
  const notification = document.createElement('div');
  notification.textContent = message;
  // Ajusta a cor de fundo se for um erro
  const background = isError
      ? 'linear-gradient(135deg, #ef4444, #dc2626)' // Vermelho para erro
      : 'linear-gradient(135deg, #d946ef, #a855f7)'; // Roxo para sucesso
  notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            background: ${background};
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

// NOVA FUNÇÃO DE EDIÇÃO REAL
async function handleEdit(field) {
  let label = '';
  let valueElementId = '';
  let requiresValue = false; // Indica se o campo deve ser obrigatório (não pode ser vazio)
  let requiredFormat = null; // Regex para validação de formato

  switch (field) {
    case 'nome-completo':
      label = 'Novo Nome Completo';
      valueElementId = 'valor-nome-completo';
      requiresValue = true;
      break;
    case 'telefone':
      label = 'Novo Telefone (Deixe vazio para remover)';
      valueElementId = 'valor-telefone';
      break;
    case 'email':
      label = 'Novo Email';
      valueElementId = 'valor-email';
      requiresValue = true;
      break;
    case 'data-nascimento':
      label = 'Nova Data de Nascimento (Formato DD/MM/AAAA)';
      valueElementId = 'valor-data-nascimento';
      requiredFormat = /^\d{2}\/\d{2}\/\d{4}$/; // Regex para DD/MM/AAAA
      break;
    default:
      showNotification('Edição para este campo está desabilitada no momento.', true);
      return;
  }

  const currentValue = document.getElementById(valueElementId).textContent;
  // Define o valor padrão do prompt, ignorando placeholders
  const promptDefault = currentValue === 'Não informado' || currentValue === '--/--/----' ? '' : currentValue;
  const newValue = prompt(`Insira o ${label}:`, promptDefault);

  if (newValue === null) {
    // Usuário cancelou
    return;
  }

  const trimmedValue = newValue.trim();

  // **VALIDAÇÃO DE OBRIGATORIEDADE/FORMATO**
  if (requiresValue && trimmedValue.length === 0) {
    showNotification(`${label} é um campo obrigatório e não pode ser vazio.`, true);
    return;
  }

  if (requiredFormat && trimmedValue.length > 0 && !requiredFormat.test(trimmedValue)) {
    showNotification(`Formato inválido. Use o formato: DD/MM/AAAA`, true);
    return;
  }
  // **FIM DA VALIDAÇÃO**


  try {
    showNotification(`Atualizando ${field.replace('-', ' ')}...`);
    const response = await fetch(`/minha-conta/${field}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'text/plain'
      },
      body: trimmedValue // Envia o valor como texto simples
    });

    if (response.ok) {
      showNotification(`${label} atualizado com sucesso! 🎉`);

      // Define o novo valor para exibição
      let displayValue = trimmedValue;
      if (trimmedValue.length === 0) {
        displayValue = field === 'data-nascimento' ? '--/--/----' : 'Não informado';
      }
      document.getElementById(valueElementId).textContent = displayValue;

      // Recarrega o nome do perfil se for a mudança de nome
      if (field === 'nome-completo') {
        document.querySelector('.profile-info h1').textContent = displayValue;
      }

      // Lidar com a atualização de email (chave de login)
      if (field === 'email') {
        showNotification('Email atualizado! Você será redirecionado para o Login, pois o email é a sua chave de acesso.', false);
        // Redireciona após 3 segundos
        setTimeout(() => {
          window.location.href = '/login';
        }, 3000);
      }

    } else {
      const errorData = await response.json();
      // Adiciona tratamento para conflito de email
      if (response.status === 409) {
        showNotification(`Erro: ${errorData.erro || 'Email já em uso por outra conta.'}`, true);
      } else {
        showNotification(`Erro ao atualizar ${field.replace('-', ' ')}: ${errorData.erro || 'Erro desconhecido'}`, true);
      }
    }
  } catch (e) {
    console.error(e);
    showNotification('Erro de conexão com o servidor. 📡', true);
  }
}

// Adicionar funcionalidade aos botões de editar (novo listener)
document.querySelectorAll('.edit-btn').forEach(btn => {
  btn.addEventListener('click', function() {
    // Pega o valor do atributo data-field do botão
    const field = this.getAttribute('data-field');
    if (field) {
      handleEdit(field);
    } else {
      showNotification('Erro: Campo de edição não identificado.', true);
    }
  });
});