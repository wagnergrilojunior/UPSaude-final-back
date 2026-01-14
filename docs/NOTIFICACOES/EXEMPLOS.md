# Exemplos Práticos — Módulo de Notificações Brevo

## Exemplos de Uso

### 1. Configuração Inicial Completa

**Cenário**: Configurar o sistema de notificações do zero.

```bash
# 1. Criar template no Brevo (via interface web)
# - Acesse https://app.brevo.com
# - Crie template "Bem-vindo ao UPSaude"
# - Anote o ID (ex: 123)

# 2. Cadastrar template no sistema
curl -X POST "http://localhost:8080/api/v1/templates-notificacao" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Bem-vindo ao UPSaude",
    "descricao": "Template de boas-vindas para novos usuários",
    "tipoNotificacao": "USUARIO_CRIADO",
    "canal": "EMAIL",
    "assunto": "Bem-vindo ao UPSaude!",
    "mensagem": "Seu usuário foi criado com sucesso. Use as variáveis do template do Brevo.",
    "brevoTemplateId": 123,
    "variaveisDisponiveis": "nome, email, dataHora",
    "enviaAutomaticamente": true
  }'

# Response:
# {
#   "id": "550e8400-e29b-41d4-a716-446655440000",
#   "nome": "Bem-vindo ao UPSaude",
#   "brevoTemplateId": 123,
#   ...
# }
```

---

### 2. Criar Usuário e Verificar Notificação Automática

**Cenário**: Criar um novo usuário e verificar se a notificação foi disparada automaticamente.

```bash
# 1. Criar usuário (dispara notificação automaticamente)
curl -X POST "http://localhost:8080/api/v1/usuarios-sistema" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "novo.usuario@exemplo.com",
    "senha": "Senha@123",
    "dadosIdentificacao": {
      "username": "novousuario",
      "nomeCompleto": "João Silva"
    },
    "dadosExibicao": {
      "nomeExibicao": "João Silva"
    }
  }'

# 2. Aguardar 30 segundos (dispatcher processa)

# 3. Verificar notificação criada
curl -X GET "http://localhost:8080/api/v1/notificacoes?tipoNotificacao=USUARIO_CRIADO&destinatario=novo.usuario@exemplo.com" \
  -H "Authorization: Bearer <TOKEN>"

# Response:
# {
#   "content": [
#     {
#       "id": "789e0123-e45b-67c8-d901-234567890abc",
#       "tipoNotificacao": "USUARIO_CRIADO",
#       "canal": "EMAIL",
#       "destinatario": "novo.usuario@exemplo.com",
#       "statusEnvio": "ENVIADO",
#       "dataEnvio": "2026-01-14T10:30:05Z",
#       "idExterno": "abc123-def456-ghi789",
#       "parametrosJson": "{\"nome\":\"João Silva\",\"email\":\"novo.usuario@exemplo.com\",\"dataHora\":\"2026-01-14T10:30:00-03:00\"}"
#     }
#   ]
# }
```

---

### 3. Criar Notificação Manual

**Cenário**: Enviar uma notificação manualmente para um usuário específico.

```bash
curl -X POST "http://localhost:8080/api/v1/notificacoes" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "tipoNotificacao": "USUARIO_CRIADO",
    "canal": "EMAIL",
    "destinatario": "usuario@exemplo.com",
    "assunto": "Bem-vindo ao UPSaude",
    "mensagem": "Seu usuário foi criado com sucesso.",
    "template": "550e8400-e29b-41d4-a716-446655440000",
    "parametrosJson": "{\"nome\":\"Maria Santos\",\"email\":\"usuario@exemplo.com\",\"dataHora\":\"2026-01-14T10:30:00-03:00\"}",
    "dataEnvioPrevista": "2026-01-14T10:30:00-03:00"
  }'

# Response:
# {
#   "id": "def456-ghi789-jkl012-mno345",
#   "statusEnvio": "PENDENTE",
#   "dataEnvioPrevista": "2026-01-14T10:30:00-03:00",
#   ...
# }
```

---

### 4. Listar Notificações Pendentes

**Cenário**: Verificar quantas notificações estão aguardando envio.

```bash
curl -X GET "http://localhost:8080/api/v1/notificacoes?statusEnvio=PENDENTE&page=0&size=50" \
  -H "Authorization: Bearer <TOKEN>"

# Response:
# {
#   "content": [
#     {
#       "id": "abc123-def456",
#       "tipoNotificacao": "AGENDAMENTO_CONFIRMADO",
#       "destinatario": "paciente@exemplo.com",
#       "statusEnvio": "PENDENTE",
#       "dataEnvioPrevista": "2026-01-14T11:00:00-03:00"
#     }
#   ],
#   "totalElements": 1,
#   "totalPages": 1
# }
```

---

### 5. Verificar Notificações de um Paciente

**Cenário**: Listar todas as notificações enviadas para um paciente específico.

```bash
curl -X GET "http://localhost:8080/api/v1/notificacoes?pacienteId=123e4567-e89b-12d3-a456-426614174000&sort=createdAt,desc" \
  -H "Authorization: Bearer <TOKEN>"

# Response:
# {
#   "content": [
#     {
#       "id": "notif1",
#       "tipoNotificacao": "AGENDAMENTO_CONFIRMADO",
#       "statusEnvio": "ENVIADO",
#       "dataEnvio": "2026-01-14T09:00:00Z"
#     },
#     {
#       "id": "notif2",
#       "tipoNotificacao": "LEMBRETE_24H",
#       "statusEnvio": "ENVIADO",
#       "dataEnvio": "2026-01-13T10:00:00Z"
#     }
#   ]
# }
```

---

### 6. Criar Template para Agendamento Confirmado

**Cenário**: Configurar template para notificação de agendamento confirmado.

```bash
# 1. Criar template no Brevo (ID: 124)

# 2. Cadastrar no sistema
curl -X POST "http://localhost:8080/api/v1/templates-notificacao" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Agendamento Confirmado",
    "descricao": "Notificação quando agendamento é confirmado",
    "tipoNotificacao": "AGENDAMENTO_CONFIRMADO",
    "canal": "EMAIL",
    "assunto": "Seu agendamento foi confirmado",
    "mensagem": "Seu agendamento foi confirmado. Use as variáveis do template do Brevo.",
    "brevoTemplateId": 124,
    "variaveisDisponiveis": "pacienteNome, dataHora, estabelecimentoNome, profissionalNome",
    "enviaAutomaticamente": true
  }'
```

---

### 7. Confirmar Agendamento e Verificar Notificação

**Cenário**: Confirmar um agendamento e verificar se a notificação foi criada.

```bash
# 1. Confirmar agendamento (via endpoint de agendamentos)
curl -X PUT "http://localhost:8080/api/v1/agendamentos/agendamento-id/status" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "CONFIRMADO"
  }'

# 2. Verificar notificação criada
curl -X GET "http://localhost:8080/api/v1/notificacoes?agendamentoId=agendamento-id&tipoNotificacao=AGENDAMENTO_CONFIRMADO" \
  -H "Authorization: Bearer <TOKEN>"

# Response:
# {
#   "content": [
#     {
#       "id": "notif-confirm",
#       "tipoNotificacao": "AGENDAMENTO_CONFIRMADO",
#       "statusEnvio": "ENVIADO",
#       "parametrosJson": "{\"pacienteNome\":\"João Silva\",\"dataHora\":\"2026-01-15T14:00:00-03:00\",\"estabelecimentoNome\":\"Clínica Central\",\"profissionalNome\":\"Dr. Carlos\"}"
#     }
#   ]
# }
```

---

### 8. Verificar Notificações com Falha

**Cenário**: Identificar notificações que falharam no envio.

```bash
curl -X GET "http://localhost:8080/api/v1/notificacoes?statusEnvio=FALHA&page=0&size=20" \
  -H "Authorization: Bearer <TOKEN>"

# Response:
# {
#   "content": [
#     {
#       "id": "notif-fail",
#       "tipoNotificacao": "USUARIO_CRIADO",
#       "destinatario": "email-invalido@",
#       "statusEnvio": "FALHA",
#       "erroEnvio": "Template ou Brevo Template ID inválido/ausente.",
#       "tentativasEnvio": 3
#     }
#   ]
# }
```

---

### 9. Atualizar Template

**Cenário**: Atualizar o ID do template do Brevo após criar novo template.

```bash
curl -X PUT "http://localhost:8080/api/v1/templates-notificacao/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Bem-vindo ao UPSaude",
    "brevoTemplateId": 125,
    "assunto": "Bem-vindo ao UPSaude - Sistema de Saúde!"
  }'

# Response: Template atualizado
```

---

### 10. Inativar Template

**Cenário**: Desabilitar temporariamente um template sem excluí-lo.

```bash
curl -X PATCH "http://localhost:8080/api/v1/templates-notificacao/550e8400-e29b-41d4-a716-446655440000/inativar" \
  -H "Authorization: Bearer <TOKEN>"

# Response: 204 No Content
```

---

## Exemplos com JavaScript/TypeScript

### Criar Notificação Manual

```javascript
async function criarNotificacao(destinatario, nome) {
  const response = await fetch('http://localhost:8080/api/v1/notificacoes', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      tipoNotificacao: 'USUARIO_CRIADO',
      canal: 'EMAIL',
      destinatario: destinatario,
      assunto: 'Bem-vindo ao UPSaude',
      mensagem: 'Seu usuário foi criado com sucesso.',
      parametrosJson: JSON.stringify({
        nome: nome,
        email: destinatario,
        dataHora: new Date().toISOString()
      })
    })
  });
  
  return await response.json();
}
```

### Listar Notificações Pendentes

```javascript
async function listarNotificacoesPendentes() {
  const response = await fetch(
    'http://localhost:8080/api/v1/notificacoes?statusEnvio=PENDENTE&page=0&size=20',
    {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    }
  );
  
  const data = await response.json();
  return data.content;
}
```

### Verificar Status de Notificação

```javascript
async function verificarStatusNotificacao(notificacaoId) {
  const response = await fetch(
    `http://localhost:8080/api/v1/notificacoes/${notificacaoId}`,
    {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    }
  );
  
  const notificacao = await response.json();
  
  if (notificacao.statusEnvio === 'ENVIADO') {
    console.log(`E-mail enviado! MessageId: ${notificacao.idExterno}`);
  } else if (notificacao.statusEnvio === 'PENDENTE') {
    console.log('Aguardando envio...');
  } else if (notificacao.statusEnvio === 'FALHA') {
    console.error(`Falha: ${notificacao.erroEnvio}`);
  }
}
```

---

## Exemplos com Python

### Criar Template

```python
import requests

def criar_template():
    url = "http://localhost:8080/api/v1/templates-notificacao"
    headers = {
        "Authorization": f"Bearer {token}",
        "Content-Type": "application/json"
    }
    data = {
        "nome": "Bem-vindo ao UPSaude",
        "tipoNotificacao": "USUARIO_CRIADO",
        "canal": "EMAIL",
        "assunto": "Bem-vindo ao UPSaude!",
        "mensagem": "Seu usuário foi criado com sucesso.",
        "brevoTemplateId": 123,
        "enviaAutomaticamente": True
    }
    
    response = requests.post(url, json=data, headers=headers)
    return response.json()
```

### Monitorar Notificações Pendentes

```python
import requests
import time

def monitorar_notificacoes():
    url = "http://localhost:8080/api/v1/notificacoes"
    headers = {"Authorization": f"Bearer {token}"}
    
    while True:
        params = {"statusEnvio": "PENDENTE", "size": 100}
        response = requests.get(url, params=params, headers=headers)
        data = response.json()
        
        pendentes = data.get("totalElements", 0)
        print(f"Notificações pendentes: {pendentes}")
        
        if pendentes == 0:
            print("Nenhuma notificação pendente!")
            break
        
        time.sleep(30)  # Aguardar 30 segundos
```

---

## Casos de Uso Completos

### Fluxo Completo: Novo Usuário

1. **Usuário é criado** → Sistema dispara evento
2. **NotificacaoOrchestrator** cria notificação com status `PENDENTE`
3. **Dispatcher** (a cada 30s) busca pendentes
4. **Dispatcher** envia via Brevo usando template
5. **Notificação** atualizada para `ENVIADO` com `idExterno`
6. **Usuário recebe e-mail** no Brevo

### Fluxo Completo: Agendamento Confirmado

1. **Agendamento confirmado** → Sistema dispara evento
2. **NotificacaoOrchestrator** cria notificação de confirmação
3. **NotificacaoOrchestrator** agenda lembretes (24h e 1h antes)
4. **Dispatcher** envia confirmação imediatamente
5. **Scheduler de lembretes** (a cada 15min) verifica agendamentos
6. **Scheduler** cria notificações de lembrete quando chegar a hora
7. **Dispatcher** envia lembretes conforme agendado

---

## Troubleshooting com Exemplos

### Problema: Notificação não foi enviada

```bash
# 1. Verificar se existe template configurado
curl -X GET "http://localhost:8080/api/v1/templates-notificacao?tipoNotificacao=USUARIO_CRIADO" \
  -H "Authorization: Bearer <TOKEN>"

# 2. Verificar se brevoTemplateId está configurado
# Se não estiver, atualizar:
curl -X PUT "http://localhost:8080/api/v1/templates-notificacao/{id}" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"brevoTemplateId": 123}'

# 3. Verificar logs do dispatcher
grep "NotificacaoDispatcherJob" logs/upsaude.log
```

### Problema: Template não encontrado no Brevo

```bash
# Verificar ID do template no sistema
curl -X GET "http://localhost:8080/api/v1/templates-notificacao/{id}" \
  -H "Authorization: Bearer <TOKEN>"

# Verificar se template existe no Brevo
# Acesse https://app.brevo.com e confirme o ID

# Atualizar com ID correto
curl -X PUT "http://localhost:8080/api/v1/templates-notificacao/{id}" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"brevoTemplateId": 123}'
```

---

Para mais detalhes, consulte:
- [CONFIGURACAO.md](./CONFIGURACAO.md) - Configuração passo a passo
- [TEMPLATES.md](./TEMPLATES.md) - Guia de templates
- [ENDPOINTS.md](./ENDPOINTS.md) - Documentação completa de endpoints
