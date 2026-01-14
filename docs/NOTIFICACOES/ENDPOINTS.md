# Catálogo de Endpoints — Módulo de Notificações

## Base URL

```
http://localhost:8080/api/v1
```

## Autenticação

Todos os endpoints requerem autenticação:

```
Authorization: Bearer <TOKEN>
```

---

## 1. Templates de Notificação

### 1.1. Criar Template

**POST** `/v1/templates-notificacao`

Cria um novo template de notificação.

#### Request

**Content-Type**: `application/json`

```json
{
  "nome": "Bem-vindo ao UPSaude",
  "descricao": "Template de boas-vindas para novos usuários",
  "tipoNotificacao": "USUARIO_CRIADO",
  "canal": "EMAIL",
  "assunto": "Bem-vindo ao UPSaude!",
  "mensagem": "Seu usuário foi criado com sucesso.",
  "brevoTemplateId": 123,
  "variaveisDisponiveis": "nome, email, dataHora",
  "enviaAutomaticamente": true,
  "active": true
}
```

#### Response

**201 Created**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "nome": "Bem-vindo ao UPSaude",
  "tipoNotificacao": "USUARIO_CRIADO",
  "canal": "EMAIL",
  "assunto": "Bem-vindo ao UPSaude!",
  "brevoTemplateId": 123,
  "enviaAutomaticamente": true,
  "active": true,
  "createdAt": "2026-01-14T10:30:00Z"
}
```

---

### 1.2. Listar Templates

**GET** `/v1/templates-notificacao`

Retorna uma lista paginada de templates.

#### Query Parameters

- `page` (opcional) - Número da página (padrão: 0)
- `size` (opcional) - Tamanho da página (padrão: 20)
- `sort` (opcional) - Ordenação (ex: `nome,asc`)
- `estabelecimentoId` (opcional) - Filtrar por estabelecimento
- `tipoNotificacao` (opcional) - Filtrar por tipo (ex: `USUARIO_CRIADO`)
- `canal` (opcional) - Filtrar por canal (ex: `EMAIL`)
- `nome` (opcional) - Buscar por nome

#### Exemplo

```bash
GET /v1/templates-notificacao?tipoNotificacao=USUARIO_CRIADO&canal=EMAIL&page=0&size=10
```

#### Response

**200 OK**

```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "nome": "Bem-vindo ao UPSaude",
      "tipoNotificacao": "USUARIO_CRIADO",
      "canal": "EMAIL",
      "brevoTemplateId": 123,
      "active": true
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0
}
```

---

### 1.3. Obter Template por ID

**GET** `/v1/templates-notificacao/{id}`

Retorna um template específico.

#### Response

**200 OK**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "nome": "Bem-vindo ao UPSaude",
  "descricao": "Template de boas-vindas",
  "tipoNotificacao": "USUARIO_CRIADO",
  "canal": "EMAIL",
  "assunto": "Bem-vindo ao UPSaude!",
  "mensagem": "Seu usuário foi criado com sucesso.",
  "brevoTemplateId": 123,
  "variaveisDisponiveis": "nome, email, dataHora",
  "enviaAutomaticamente": true,
  "active": true,
  "createdAt": "2026-01-14T10:30:00Z",
  "updatedAt": "2026-01-14T10:30:00Z"
}
```

---

### 1.4. Atualizar Template

**PUT** `/v1/templates-notificacao/{id}`

Atualiza um template existente.

#### Request

```json
{
  "nome": "Bem-vindo ao UPSaude (Atualizado)",
  "assunto": "Bem-vindo ao UPSaude - Sistema de Saúde!",
  "brevoTemplateId": 124
}
```

#### Response

**200 OK** - Retorna o template atualizado

---

### 1.5. Excluir Template

**DELETE** `/v1/templates-notificacao/{id}`

Remove permanentemente um template.

#### Response

**204 No Content**

---

### 1.6. Inativar Template

**PATCH** `/v1/templates-notificacao/{id}/inativar`

Inativa um template sem excluí-lo.

#### Response

**204 No Content**

---

## 2. Notificações

### 2.1. Criar Notificação

**POST** `/v1/notificacoes`

Cria uma nova notificação manualmente.

#### Request

```json
{
  "tipoNotificacao": "USUARIO_CRIADO",
  "canal": "EMAIL",
  "destinatario": "usuario@exemplo.com",
  "assunto": "Bem-vindo ao UPSaude",
  "mensagem": "Seu usuário foi criado com sucesso.",
  "paciente": "123e4567-e89b-12d3-a456-426614174000",
  "template": "550e8400-e29b-41d4-a716-446655440000",
  "parametrosJson": "{\"nome\":\"João Silva\",\"email\":\"joao@exemplo.com\"}",
  "dataEnvioPrevista": "2026-01-14T10:30:00-03:00"
}
```

#### Response

**201 Created**

```json
{
  "id": "789e0123-e45b-67c8-d901-234567890abc",
  "tipoNotificacao": "USUARIO_CRIADO",
  "canal": "EMAIL",
  "destinatario": "usuario@exemplo.com",
  "assunto": "Bem-vindo ao UPSaude",
  "statusEnvio": "PENDENTE",
  "dataEnvioPrevista": "2026-01-14T10:30:00-03:00",
  "createdAt": "2026-01-14T10:25:00Z"
}
```

---

### 2.2. Listar Notificações

**GET** `/v1/notificacoes`

Retorna uma lista paginada de notificações.

#### Query Parameters

- `page` (opcional) - Número da página
- `size` (opcional) - Tamanho da página
- `sort` (opcional) - Ordenação
- `estabelecimentoId` (opcional) - Filtrar por estabelecimento
- `pacienteId` (opcional) - Filtrar por paciente
- `profissionalId` (opcional) - Filtrar por profissional
- `agendamentoId` (opcional) - Filtrar por agendamento
- `statusEnvio` (opcional) - Filtrar por status (`PENDENTE`, `ENVIADO`, `FALHA`)
- `inicio` (opcional) - Data inicial (ISO 8601)
- `fim` (opcional) - Data final (ISO 8601)
- `usarPrevista` (opcional) - Usar `dataEnvioPrevista` ao invés de `dataEnvio`

#### Exemplo

```bash
GET /v1/notificacoes?statusEnvio=PENDENTE&pacienteId=123e4567-e89b-12d3-a456-426614174000&page=0&size=20
```

#### Response

**200 OK**

```json
{
  "content": [
    {
      "id": "789e0123-e45b-67c8-d901-234567890abc",
      "tipoNotificacao": "USUARIO_CRIADO",
      "canal": "EMAIL",
      "destinatario": "usuario@exemplo.com",
      "assunto": "Bem-vindo ao UPSaude",
      "statusEnvio": "PENDENTE",
      "dataEnvioPrevista": "2026-01-14T10:30:00-03:00",
      "tentativasEnvio": 0,
      "createdAt": "2026-01-14T10:25:00Z"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 20,
  "number": 0
}
```

---

### 2.3. Obter Notificação por ID

**GET** `/v1/notificacoes/{id}`

Retorna uma notificação específica.

#### Response

**200 OK**

```json
{
  "id": "789e0123-e45b-67c8-d901-234567890abc",
  "tipoNotificacao": "USUARIO_CRIADO",
  "canal": "EMAIL",
  "destinatario": "usuario@exemplo.com",
  "assunto": "Bem-vindo ao UPSaude",
  "mensagem": "Seu usuário foi criado com sucesso.",
  "statusEnvio": "ENVIADO",
  "dataEnvioPrevista": "2026-01-14T10:30:00-03:00",
  "dataEnvio": "2026-01-14T10:30:05Z",
  "idExterno": "abc123-def456-ghi789",
  "tentativasEnvio": 1,
  "parametrosJson": "{\"nome\":\"João Silva\",\"email\":\"joao@exemplo.com\"}",
  "createdAt": "2026-01-14T10:25:00Z",
  "updatedAt": "2026-01-14T10:30:05Z"
}
```

---

### 2.4. Atualizar Notificação

**PUT** `/v1/notificacoes/{id}`

Atualiza uma notificação existente.

#### Request

```json
{
  "statusEnvio": "ENVIADO",
  "dataEnvio": "2026-01-14T10:30:05Z",
  "idExterno": "abc123-def456-ghi789"
}
```

#### Response

**200 OK** - Retorna a notificação atualizada

---

### 2.5. Excluir Notificação

**DELETE** `/v1/notificacoes/{id}`

Remove permanentemente uma notificação.

#### Response

**204 No Content**

---

### 2.6. Inativar Notificação

**PATCH** `/v1/notificacoes/{id}/inativar`

Inativa uma notificação sem excluí-la.

#### Response

**204 No Content**

---

## Códigos de Status HTTP

- **200 OK** - Operação bem-sucedida
- **201 Created** - Recurso criado com sucesso
- **204 No Content** - Operação bem-sucedida sem conteúdo
- **400 Bad Request** - Dados inválidos
- **401 Unauthorized** - Não autenticado
- **403 Forbidden** - Acesso negado
- **404 Not Found** - Recurso não encontrado
- **500 Internal Server Error** - Erro interno do servidor

---

## Exemplos de Uso

### Criar Template e Notificação

```bash
# 1. Criar template
curl -X POST "http://localhost:8080/api/v1/templates-notificacao" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Bem-vindo ao UPSaude",
    "tipoNotificacao": "USUARIO_CRIADO",
    "canal": "EMAIL",
    "assunto": "Bem-vindo ao UPSaude!",
    "mensagem": "Seu usuário foi criado.",
    "brevoTemplateId": 123,
    "enviaAutomaticamente": true
  }'

# 2. Criar notificação manual
curl -X POST "http://localhost:8080/api/v1/notificacoes" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "tipoNotificacao": "USUARIO_CRIADO",
    "canal": "EMAIL",
    "destinatario": "usuario@exemplo.com",
    "assunto": "Bem-vindo ao UPSaude",
    "mensagem": "Seu usuário foi criado.",
    "template": "550e8400-e29b-41d4-a716-446655440000",
    "parametrosJson": "{\"nome\":\"João\",\"email\":\"joao@exemplo.com\"}"
  }'

# 3. Verificar status
curl -X GET "http://localhost:8080/api/v1/notificacoes?statusEnvio=PENDENTE" \
  -H "Authorization: Bearer <TOKEN>"
```

---

## Validações

### TemplateNotificacaoRequest

- `nome` - Obrigatório, máximo 255 caracteres
- `tipoNotificacao` - Obrigatório, enum válido
- `canal` - Obrigatório, enum válido
- `mensagem` - Obrigatório
- `assunto` - Máximo 500 caracteres
- `brevoTemplateId` - Opcional, inteiro positivo

### NotificacaoRequest

- `tipoNotificacao` - Obrigatório
- `canal` - Obrigatório
- `destinatario` - Obrigatório, máximo 255 caracteres
- `mensagem` - Obrigatório
- `assunto` - Máximo 500 caracteres

---

## Multitenancy

Todos os endpoints respeitam o contexto de tenant:

- O tenant é resolvido automaticamente via usuário autenticado
- Templates e notificações são isolados por tenant
- Não é necessário passar `tenantId` explicitamente

---

## Paginação

Todos os endpoints de listagem suportam paginação:

- `page` - Número da página (começa em 0)
- `size` - Tamanho da página (padrão: 20)
- `sort` - Ordenação (ex: `nome,asc` ou `createdAt,desc`)

Exemplo:
```
GET /v1/notificacoes?page=0&size=10&sort=createdAt,desc
```

---

Para mais detalhes, consulte:
- [EXEMPLOS.md](./EXEMPLOS.md) - Exemplos práticos completos
- [TECNICO.md](./TECNICO.md) - Detalhes técnicos
