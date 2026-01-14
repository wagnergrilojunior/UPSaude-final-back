# Templates — Guia Completo de Templates do Brevo

Este documento fornece um **guia completo** para criar e configurar templates de e-mail no Brevo para o módulo de notificações do UPSaúde.

---

## Índice

1. [Visão Geral](#visão-geral)
2. [Tipos de Templates](#tipos-de-templates)
3. [Criação de Templates no Brevo](#criação-de-templates-no-brevo)
4. [Variáveis Disponíveis](#variáveis-disponíveis)
5. [Cadastro no Sistema](#cadastro-no-sistema)
6. [Exemplos Completos](#exemplos-completos)
7. [Boas Práticas](#boas-práticas)

---

## Visão Geral

Os templates do Brevo permitem criar e-mails transacionais profissionais e personalizados. O sistema UPSaúde envia os dados dinâmicos como **parâmetros** que são substituídos no template.

### Como Funciona

1. **Template criado no Brevo** com variáveis (ex: `{{params.nome}}`)
2. **Template cadastrado no sistema** com `brevoTemplateId`
3. **Evento ocorre** no sistema (ex: usuário criado)
4. **Sistema cria notificação** com parâmetros JSON
5. **Dispatcher envia** para Brevo com `templateId` e `params`
6. **Brevo substitui variáveis** e envia o e-mail

---

## Tipos de Templates

### 1. Usuário Criado (`USUARIO_CRIADO`)

**Quando dispara:** Quando um novo usuário é criado no sistema.

**Variáveis disponíveis:**
- `{{params.nome}}` - Nome do usuário
- `{{params.email}}` - E-mail do usuário
- `{{params.dataHora}}` - Data e hora da criação

**Remetente sugerido:** `noreply@wgbsolucoes.com.br`

**Exemplo de assunto:** "Bem-vindo ao UPSaude!"

---

### 2. Senha Alterada (`SENHA_ALTERADA`)

**Quando dispara:** Quando a senha do usuário é alterada.

**Variáveis disponíveis:**
- `{{params.nome}}` - Nome do usuário
- `{{params.email}}` - E-mail do usuário
- `{{params.dataHora}}` - Data e hora da alteração

**Remetente sugerido:** `noreply@wgbsolucoes.com.br`

**Exemplo de assunto:** "Senha alterada com sucesso"

---

### 3. Dados Pessoais Atualizados (`DADOS_PESSOAIS_ATUALIZADOS`)

**Quando dispara:** Quando dados pessoais do usuário ou paciente são atualizados.

**Variáveis disponíveis:**
- `{{params.nome}}` - Nome do usuário/paciente
- `{{params.email}}` - E-mail
- `{{params.dataHora}}` - Data e hora da atualização

**Remetente sugerido:** `notificacoes@wgbsolucoes.com.br`

**Exemplo de assunto:** "Seus dados foram atualizados"

---

### 4. Agendamento Confirmado (`AGENDAMENTO_CONFIRMADO`)

**Quando dispara:** Quando um agendamento é confirmado.

**Variáveis disponíveis:**
- `{{params.pacienteNome}}` - Nome do paciente
- `{{params.dataHora}}` - Data e hora do agendamento
- `{{params.estabelecimentoNome}}` - Nome do estabelecimento
- `{{params.profissionalNome}}` - Nome do profissional (se houver)
- `{{params.medicoNome}}` - Nome do médico (se houver)
- `{{params.statusAgendamento}}` - Status do agendamento

**Remetente sugerido:** `notificacoes@wgbsolucoes.com.br`

**Exemplo de assunto:** "Seu agendamento foi confirmado"

---

### 5. Agendamento Cancelado (`AGENDAMENTO_CANCELADO`)

**Quando dispara:** Quando um agendamento é cancelado.

**Variáveis disponíveis:**
- `{{params.pacienteNome}}` - Nome do paciente
- `{{params.dataHora}}` - Data e hora do agendamento cancelado
- `{{params.estabelecimentoNome}}` - Nome do estabelecimento
- `{{params.motivoCancelamento}}` - Motivo do cancelamento

**Remetente sugerido:** `notificacoes@wgbsolucoes.com.br`

**Exemplo de assunto:** "Seu agendamento foi cancelado"

---

### 6. Lembrete 24 Horas (`LEMBRETE_24H`)

**Quando dispara:** 24 horas antes do agendamento.

**Variáveis disponíveis:**
- `{{params.pacienteNome}}` - Nome do paciente
- `{{params.dataHora}}` - Data e hora do agendamento
- `{{params.estabelecimentoNome}}` - Nome do estabelecimento
- `{{params.profissionalNome}}` - Nome do profissional
- `{{params.medicoNome}}` - Nome do médico

**Remetente sugerido:** `notificacoes@wgbsolucoes.com.br`

**Exemplo de assunto:** "Lembrete: Você tem um agendamento amanhã"

---

### 7. Lembrete 1 Hora (`LEMBRETE_1H`)

**Quando dispara:** 1 hora antes do agendamento.

**Variáveis disponíveis:**
- `{{params.pacienteNome}}` - Nome do paciente
- `{{params.dataHora}}` - Data e hora do agendamento
- `{{params.estabelecimentoNome}}` - Nome do estabelecimento
- `{{params.profissionalNome}}` - Nome do profissional
- `{{params.medicoNome}}` - Nome do médico

**Remetente sugerido:** `notificacoes@wgbsolucoes.com.br`

**Exemplo de assunto:** "Lembrete: Você tem um agendamento em 1 hora"

---

## Criação de Templates no Brevo

### Passo 1: Acessar Templates

1. Faça login em https://app.brevo.com
2. Vá em **Transactional** > **Templates**
3. Clique em **Create a new template**

### Passo 2: Escolher Tipo

- **Blank template**: Template em branco (recomendado)
- **Start from a design**: Usar design pré-definido

### Passo 3: Configurar Template

**Campos obrigatórios:**

- **Template name**: Nome descritivo (ex: "Bem-vindo ao UPSaude")
- **Subject**: Assunto do e-mail (pode usar variáveis)

**Campos opcionais:**

- **Preheader**: Texto pré-cabeçalho (aparece após assunto)
- **Design**: HTML/CSS personalizado

### Passo 4: Adicionar Variáveis

No corpo do template, use a sintaxe do Brevo:

```
Olá {{params.nome}},

Seu e-mail é: {{params.email}}
```

**Importante:** O Brevo usa `{{params.VARIAVEL}}` para acessar os parâmetros enviados.

### Passo 5: Salvar e Publicar

1. Clique em **Save**
2. Clique em **Publish** (torna o template ativo)
3. Anote o **ID do template** (aparece na URL ou na lista)

---

## Variáveis Disponíveis

### Formato no Brevo

Todas as variáveis devem ser acessadas como `{{params.NOME_DA_VARIAVEL}}`.

### Variáveis por Tipo de Notificação

#### Usuário Criado / Senha Alterada / Dados Pessoais

```html
{{params.nome}}        <!-- Nome do usuário -->
{{params.email}}       <!-- E-mail do usuário -->
{{params.dataHora}}    <!-- Data/hora no formato ISO (ex: 2026-01-14T10:30:00-03:00) -->
```

#### Agendamentos

```html
{{params.pacienteNome}}           <!-- Nome completo do paciente -->
{{params.dataHora}}                <!-- Data/hora do agendamento -->
{{params.estabelecimentoNome}}     <!-- Nome do estabelecimento -->
{{params.profissionalNome}}        <!-- Nome do profissional (pode estar vazio) -->
{{params.medicoNome}}              <!-- Nome do médico (pode estar vazio) -->
{{params.statusAgendamento}}       <!-- Status (ex: "Confirmado") -->
{{params.motivoCancelamento}}      <!-- Motivo do cancelamento (apenas para cancelamento) -->
```

---

## Cadastro no Sistema

Após criar o template no Brevo, você precisa cadastrá-lo no sistema via API.

### Endpoint

```
POST /api/v1/templates-notificacao
```

### Exemplo: Template de Usuário Criado

```bash
curl -X POST "http://localhost:8080/api/v1/templates-notificacao" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Bem-vindo ao UPSaude",
    "descricao": "Template de boas-vindas para novos usuários",
    "tipoNotificacao": "USUARIO_CRIADO",
    "canal": "EMAIL",
    "assunto": "Bem-vindo ao UPSaude!",
    "mensagem": "Seu usuário foi criado com sucesso. Use as variáveis {{params.nome}} e {{params.email}} no template do Brevo.",
    "brevoTemplateId": 123,
    "variaveisDisponiveis": "nome, email, dataHora",
    "enviaAutomaticamente": true,
    "active": true
  }'
```

### Exemplo: Template de Agendamento Confirmado

```bash
curl -X POST "http://localhost:8080/api/v1/templates-notificacao" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Agendamento Confirmado",
    "descricao": "Notificação de confirmação de agendamento",
    "tipoNotificacao": "AGENDAMENTO_CONFIRMADO",
    "canal": "EMAIL",
    "assunto": "Seu agendamento foi confirmado",
    "mensagem": "Seu agendamento foi confirmado. Use as variáveis do template do Brevo.",
    "brevoTemplateId": 124,
    "variaveisDisponiveis": "pacienteNome, dataHora, estabelecimentoNome, profissionalNome, medicoNome",
    "enviaAutomaticamente": true,
    "active": true
  }'
```

---

## Exemplos Completos

### Exemplo 1: Template "Bem-vindo ao UPSaude"

**No Brevo:**

**Assunto:**
```
Bem-vindo ao UPSaude, {{params.nome}}!
```

**Corpo (HTML):**
```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
        .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }
        .content { padding: 20px; background-color: #f9f9f9; }
        .footer { text-align: center; padding: 20px; font-size: 12px; color: #666; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Bem-vindo ao UPSaude!</h1>
        </div>
        <div class="content">
            <p>Olá <strong>{{params.nome}}</strong>,</p>
            
            <p>Seu usuário foi criado com sucesso no sistema UPSaude.</p>
            
            <p><strong>E-mail cadastrado:</strong> {{params.email}}</p>
            <p><strong>Data de criação:</strong> {{params.dataHora}}</p>
            
            <p>Você já pode acessar o sistema usando suas credenciais.</p>
            
            <p>Se você não solicitou esta conta, entre em contato conosco imediatamente.</p>
        </div>
        <div class="footer">
            <p>Este é um e-mail automático, por favor não responda.</p>
            <p>UPSaude - Sistema de Gestão em Saúde</p>
        </div>
    </div>
</body>
</html>
```

**Cadastro no sistema:**
```json
{
  "nome": "Bem-vindo ao UPSaude",
  "tipoNotificacao": "USUARIO_CRIADO",
  "canal": "EMAIL",
  "assunto": "Bem-vindo ao UPSaude, {{params.nome}}!",
  "brevoTemplateId": 123,
  "enviaAutomaticamente": true
}
```

---

### Exemplo 2: Template "Agendamento Confirmado"

**No Brevo:**

**Assunto:**
```
Seu agendamento foi confirmado - {{params.estabelecimentoNome}}
```

**Corpo (HTML):**
```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
        .header { background-color: #2196F3; color: white; padding: 20px; text-align: center; }
        .content { padding: 20px; background-color: #f9f9f9; }
        .info-box { background-color: white; padding: 15px; margin: 15px 0; border-left: 4px solid #2196F3; }
        .footer { text-align: center; padding: 20px; font-size: 12px; color: #666; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Agendamento Confirmado</h1>
        </div>
        <div class="content">
            <p>Olá <strong>{{params.pacienteNome}}</strong>,</p>
            
            <p>Seu agendamento foi confirmado com sucesso!</p>
            
            <div class="info-box">
                <p><strong>Data e Hora:</strong> {{params.dataHora}}</p>
                <p><strong>Estabelecimento:</strong> {{params.estabelecimentoNome}}</p>
                {{#params.profissionalNome}}
                <p><strong>Profissional:</strong> {{params.profissionalNome}}</p>
                {{/params.profissionalNome}}
                {{#params.medicoNome}}
                <p><strong>Médico:</strong> {{params.medicoNome}}</p>
                {{/params.medicoNome}}
            </div>
            
            <p>Por favor, compareça no horário agendado. Em caso de necessidade de cancelamento ou reagendamento, entre em contato conosco com antecedência.</p>
            
            <p>Obrigado por escolher o UPSaude!</p>
        </div>
        <div class="footer">
            <p>Este é um e-mail automático, por favor não responda.</p>
            <p>UPSaude - Sistema de Gestão em Saúde</p>
        </div>
    </div>
</body>
</html>
```

---

### Exemplo 3: Template "Lembrete 24 Horas"

**No Brevo:**

**Assunto:**
```
Lembrete: Você tem um agendamento amanhã
```

**Corpo (HTML):**
```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
        .header { background-color: #FF9800; color: white; padding: 20px; text-align: center; }
        .content { padding: 20px; background-color: #f9f9f9; }
        .reminder-box { background-color: #FFF3CD; border: 2px solid #FF9800; padding: 15px; margin: 15px 0; }
        .footer { text-align: center; padding: 20px; font-size: 12px; color: #666; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>⏰ Lembrete de Agendamento</h1>
        </div>
        <div class="content">
            <p>Olá <strong>{{params.pacienteNome}}</strong>,</p>
            
            <div class="reminder-box">
                <h2>Você tem um agendamento em 24 horas!</h2>
                <p><strong>Data e Hora:</strong> {{params.dataHora}}</p>
                <p><strong>Estabelecimento:</strong> {{params.estabelecimentoNome}}</p>
            </div>
            
            <p>Por favor, confirme sua presença ou entre em contato caso precise cancelar ou reagendamento.</p>
            
            <p>Esperamos vê-lo em breve!</p>
        </div>
        <div class="footer">
            <p>Este é um e-mail automático, por favor não responda.</p>
            <p>UPSaude - Sistema de Gestão em Saúde</p>
        </div>
    </div>
</body>
</html>
```

---

## Boas Práticas

### 1. Design Responsivo

- Use tabelas para layout (melhor compatibilidade)
- Largura máxima de 600px
- Teste em diferentes clientes de e-mail

### 2. Acessibilidade

- Use contraste adequado
- Texto alternativo em imagens
- Estrutura semântica HTML

### 3. Performance

- Evite imagens muito grandes
- Use CSS inline quando possível
- Minimize código HTML

### 4. Personalização

- Use variáveis dinamicamente
- Mantenha tom profissional mas amigável
- Inclua informações de contato

### 5. Testes

- Teste todos os templates antes de publicar
- Verifique em diferentes clientes (Gmail, Outlook, etc.)
- Teste com dados reais

---

## Estrutura Recomendada de Template

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        /* Estilos aqui */
    </style>
</head>
<body>
    <div class="container">
        <!-- Cabeçalho -->
        <div class="header">
            <h1>Título Principal</h1>
        </div>
        
        <!-- Conteúdo -->
        <div class="content">
            <p>Saudação, {{params.nome}}!</p>
            
            <!-- Informações principais -->
            <div class="info-box">
                <!-- Variáveis aqui -->
            </div>
            
            <!-- Mensagem principal -->
            <p>Mensagem principal...</p>
            
            <!-- Call to action (se necessário) -->
            <div class="cta">
                <a href="#">Ação</a>
            </div>
        </div>
        
        <!-- Rodapé -->
        <div class="footer">
            <p>Informações legais</p>
            <p>Contato</p>
        </div>
    </div>
</body>
</html>
```

---

## Checklist de Template

Para cada template criado:

- [ ] Template criado no Brevo
- [ ] ID do template anotado
- [ ] Variáveis configuradas corretamente
- [ ] Design responsivo testado
- [ ] Template publicado no Brevo
- [ ] Template cadastrado no sistema via API
- [ ] `brevoTemplateId` configurado corretamente
- [ ] Teste de envio realizado
- [ ] E-mail recebido e visualizado
- [ ] Variáveis substituídas corretamente

---

## Próximos Passos

1. Crie todos os templates necessários no Brevo
2. Cadastre cada template no sistema via API
3. Teste cada tipo de notificação
4. Ajuste designs conforme necessário
5. Documente IDs dos templates para referência futura

Para mais detalhes, consulte:
- [CONFIGURACAO.md](./CONFIGURACAO.md) - Configuração completa
- [EXEMPLOS.md](./EXEMPLOS.md) - Exemplos práticos de uso
