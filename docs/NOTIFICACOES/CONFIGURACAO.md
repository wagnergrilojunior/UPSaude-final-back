# Configuração — Módulo de Notificações Brevo

Este documento fornece um **passo a passo completo** para configurar o módulo de notificações por e-mail via Brevo no UPSaúde.

---

## Índice

1. [Pré-requisitos](#pré-requisitos)
2. [Configuração do Brevo](#configuração-do-brevo)
3. [Configuração do Backend](#configuração-do-backend)
4. [Migration do Banco de Dados](#migration-do-banco-de-dados)
5. [Configuração de Variáveis de Ambiente](#configuração-de-variáveis-de-ambiente)
6. [Criação de Templates](#criação-de-templates)
7. [Validação da Configuração](#validação-da-configuração)
8. [Troubleshooting](#troubleshooting)

---

## Pré-requisitos

Antes de começar, certifique-se de ter:

- ✅ Conta ativa no Brevo (https://www.brevo.com)
- ✅ Acesso ao banco de dados PostgreSQL
- ✅ Acesso ao servidor/ambiente onde a aplicação roda
- ✅ Permissões para configurar variáveis de ambiente
- ✅ Acesso à API do sistema (para cadastrar templates)

---

## Configuração do Brevo

### Passo 1: Criar Conta no Brevo

1. Acesse https://www.brevo.com
2. Crie uma conta (plano gratuito disponível)
3. Confirme seu e-mail

### Passo 2: Obter API Key

1. Faça login no painel do Brevo
2. Vá em **Settings** > **SMTP & API**
3. Clique em **Generate a new API key**
4. Dê um nome descritivo (ex: "UPSaude Production")
5. Selecione permissões: **Send emails**
6. Copie a chave API gerada

⚠️ **IMPORTANTE**: A chave API será exibida apenas uma vez. Guarde-a com segurança.

**Chave API fornecida** (para referência):
```
SUA_CHAVE_API_BREVO_AQUI
```

⚠️ **Recomendação**: Rotacione esta chave após a primeira configuração.

### Passo 3: Verificar Remetentes

Os seguintes e-mails devem estar configurados como remetentes válidos no Brevo:

- `noreply@wgbsolucoes.com.br`
- `notificacoes@wgbsolucoes.com.br`
- `suporte@wgbsolucoes.com.br`

**Como verificar/validar remetentes:**

1. No Brevo, vá em **Settings** > **Senders & IP**
2. Clique em **Add a sender**
3. Adicione cada e-mail
4. Valide o domínio seguindo as instruções do Brevo
5. Aguarde aprovação (pode levar algumas horas)

---

## Configuração do Backend

### Passo 1: Arquivo de Propriedades

O arquivo de propriedades já está criado em:
```
src/main/resources/config/common/integrations/application-brevo.properties
```

**Conteúdo padrão:**

```properties
# Habilitar/desabilitar integração Brevo
brevo.enabled=${BREVO_ENABLED:true}

# Chave API do Brevo (obrigatória se enabled=true)
brevo.api-key=${BREVO_API_KEY:}

# URL base da API Brevo
brevo.base-url=https://api.brevo.com/v3

# Timeout em segundos
brevo.timeout-seconds=30

# Configurações de retry
brevo.retry.max-attempts=3
brevo.retry.backoff-millis=5000

# Intervalo do dispatcher de notificações (em segundos)
brevo.dispatcher.interval-seconds=30

# Intervalo do scheduler de lembretes (em minutos)
brevo.reminder-scheduler.interval-minutes=15

# Remetentes padrão
brevo.sender.noreply.email=noreply@wgbsolucoes.com.br
brevo.sender.noreply.name=UPSaude - Não Responda

brevo.sender.notificacoes.email=notificacoes@wgbsolucoes.com.br
brevo.sender.notificacoes.name=UPSaude - Notificações

brevo.sender.suporte.email=suporte@wgbsolucoes.com.br
brevo.sender.suporte.name=UPSaude - Suporte
```

### Passo 2: Verificar Importação

O arquivo já está importado no `application.properties`:

```properties
spring.config.import=\
  ...
  optional:classpath:config/common/integrations/application-brevo.properties,\
  ...
```

---

## Migration do Banco de Dados

### Passo 1: Executar Migration

Execute a migration SQL para adicionar suporte a `brevo_template_id`:

```bash
# Via psql
psql -U postgres -d upsaude -f migrations/add_brevo_template_id_to_templates_notificacao.sql

# Ou via cliente SQL de sua preferência
```

**Arquivo de migration:**
```
migrations/add_brevo_template_id_to_templates_notificacao.sql
```

**Conteúdo da migration:**

```sql
-- Adicionar coluna brevo_template_id
ALTER TABLE public.templates_notificacao
ADD COLUMN IF NOT EXISTS brevo_template_id INTEGER;

-- Adicionar comentário na coluna
COMMENT ON COLUMN public.templates_notificacao.brevo_template_id IS 'ID do template no Brevo (Sendinblue) para envio de e-mails transacionais';

-- Criar índice para busca por template do Brevo
CREATE INDEX IF NOT EXISTS idx_template_brevo_id 
ON public.templates_notificacao(brevo_template_id) 
WHERE brevo_template_id IS NOT NULL;
```

### Passo 2: Verificar Migration

```sql
-- Verificar se a coluna foi criada
SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name = 'templates_notificacao' 
  AND column_name = 'brevo_template_id';

-- Verificar índice
SELECT indexname 
FROM pg_indexes 
WHERE tablename = 'templates_notificacao' 
  AND indexname = 'idx_template_brevo_id';
```

---

## Configuração de Variáveis de Ambiente

### Ambiente Local (Desenvolvimento)

**Linux/Mac:**

```bash
export BREVO_API_KEY="SUA_CHAVE_API_BREVO_AQUI"
export BREVO_ENABLED="true"
```

**Windows (PowerShell):**

```powershell
$env:BREVO_API_KEY="SUA_CHAVE_API_BREVO_AQUI"
$env:BREVO_ENABLED="true"
```

**Windows (CMD):**

```cmd
set BREVO_API_KEY=SUA_CHAVE_API_BREVO_AQUI
set BREVO_ENABLED=true
```

### Ambiente Docker

**docker-compose.yml:**

```yaml
services:
  upsaude-back:
    environment:
      - BREVO_API_KEY=${BREVO_API_KEY}
      - BREVO_ENABLED=true
```

**.env:**

```env
BREVO_API_KEY=SUA_CHAVE_API_BREVO_AQUI
BREVO_ENABLED=true
```

### Ambiente de Produção

**Opções recomendadas:**

1. **Kubernetes Secrets:**
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: brevo-secret
type: Opaque
stringData:
  BREVO_API_KEY: "sua-chave-aqui"
```

2. **AWS Secrets Manager / Azure Key Vault:**
   - Configure o secret manager
   - Referencie no deployment

3. **Variáveis de Ambiente do Servidor:**
   - Configure no sistema operacional
   - Ou no sistema de orquestração (systemd, supervisor, etc.)

---

## Criação de Templates

### Passo 1: Criar Templates no Brevo

Para cada tipo de notificação, você precisa criar um template no Brevo:

1. Acesse https://app.brevo.com
2. Vá em **Transactional** > **Templates**
3. Clique em **Create a new template**
4. Escolha **Blank template** ou **Start from a design**

**Templates necessários:**

| Tipo de Notificação | ID do Enum | Nome Sugerido |
|---------------------|------------|---------------|
| Usuário Criado | USUARIO_CRIADO (15) | "Bem-vindo ao UPSaude" |
| Senha Alterada | SENHA_ALTERADA (16) | "Senha alterada com sucesso" |
| Dados Pessoais Atualizados | DADOS_PESSOAIS_ATUALIZADOS (17) | "Dados atualizados" |
| Agendamento Confirmado | AGENDAMENTO_CONFIRMADO (2) | "Agendamento confirmado" |
| Agendamento Cancelado | AGENDAMENTO_CANCELADO (3) | "Agendamento cancelado" |
| Lembrete 24h | LEMBRETE_24H (5) | "Lembrete: Agendamento em 24h" |
| Lembrete 1h | LEMBRETE_1H (6) | "Lembrete: Agendamento em 1h" |

### Passo 2: Configurar Variáveis no Template

Cada template pode usar variáveis dinâmicas. Veja [TEMPLATES.md](./TEMPLATES.md) para detalhes completos.

**Variáveis disponíveis por tipo:**

**Usuário Criado / Senha Alterada:**
- `{{params.nome}}` - Nome do usuário
- `{{params.email}}` - E-mail do usuário
- `{{params.dataHora}}` - Data e hora do evento

**Agendamento:**
- `{{params.pacienteNome}}` - Nome do paciente
- `{{params.dataHora}}` - Data e hora do agendamento
- `{{params.estabelecimentoNome}}` - Nome do estabelecimento
- `{{params.profissionalNome}}` - Nome do profissional
- `{{params.medicoNome}}` - Nome do médico
- `{{params.statusAgendamento}}` - Status do agendamento
- `{{params.motivoCancelamento}}` - Motivo do cancelamento (se aplicável)

### Passo 3: Anotar ID do Template

Após criar cada template no Brevo:

1. Abra o template
2. Veja a URL: `https://app.brevo.com/template/123/edit`
3. O número `123` é o **ID do template**
4. Anote este ID

### Passo 4: Cadastrar Template no Sistema

Use a API de `TemplateNotificacao` para cadastrar:

```bash
curl -X POST "http://localhost:8080/api/v1/templates-notificacao" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Bem-vindo ao UPSaude",
    "tipoNotificacao": "USUARIO_CRIADO",
    "canal": "EMAIL",
    "assunto": "Bem-vindo ao UPSaude",
    "mensagem": "Seu usuário foi criado com sucesso.",
    "brevoTemplateId": 123,
    "enviaAutomaticamente": true,
    "active": true
  }'
```

Veja [TEMPLATES.md](./TEMPLATES.md) para exemplos completos.

---

## Validação da Configuração

### Teste 1: Verificar Configuração Carregada

```bash
# Verificar logs na inicialização
grep -i "brevo" logs/upsaude.log

# Deve aparecer algo como:
# "BrevoConfig initialized"
```

### Teste 2: Criar Notificação Manual

```bash
curl -X POST "http://localhost:8080/api/v1/notificacoes" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "tipoNotificacao": "USUARIO_CRIADO",
    "canal": "EMAIL",
    "destinatario": "seu-email@exemplo.com",
    "assunto": "Teste",
    "mensagem": "Mensagem de teste",
    "statusEnvio": "PENDENTE"
  }'
```

### Teste 3: Verificar Dispatcher

Aguarde 30 segundos e verifique se a notificação foi enviada:

```bash
curl -X GET "http://localhost:8080/api/v1/notificacoes?statusEnvio=ENVIADO" \
  -H "Authorization: Bearer <TOKEN>"
```

### Teste 4: Criar Usuário (Teste Completo)

```bash
curl -X POST "http://localhost:8080/api/v1/usuarios-sistema" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teste@exemplo.com",
    "senha": "Senha@123",
    "dadosIdentificacao": {
      "username": "teste"
    }
  }'
```

Verifique:
1. Se o usuário foi criado
2. Se uma notificação foi criada (status=PENDENTE)
3. Após 30s, se a notificação foi enviada (status=ENVIADO)
4. Se você recebeu o e-mail

---

## Troubleshooting

### Problema: Notificações não são enviadas

**Verificações:**

1. **Variável de ambiente configurada?**
```bash
echo $BREVO_API_KEY
```

2. **Brevo habilitado?**
```bash
grep "brevo.enabled" logs/upsaude.log
```

3. **Chave API válida?**
   - Teste manualmente no Brevo
   - Verifique se não expirou

4. **Template configurado?**
```sql
SELECT nome, brevo_template_id, tipo_notificacao 
FROM templates_notificacao 
WHERE brevo_template_id IS NOT NULL;
```

5. **Dispatcher rodando?**
   - Verifique logs: `grep "NotificacaoDispatcherJob" logs/upsaude.log`
   - Deve aparecer: "Processando X notificação(ões) pendente(s)"

### Problema: Erro "Chave API do Brevo não configurada"

**Solução:**

1. Verifique se `BREVO_API_KEY` está definida:
```bash
env | grep BREVO_API_KEY
```

2. Reinicie a aplicação após definir a variável

3. Verifique se não há espaços extras na chave

### Problema: Template não encontrado

**Solução:**

1. Verifique se o `brevoTemplateId` está correto:
```sql
SELECT id, nome, brevo_template_id 
FROM templates_notificacao 
WHERE tipo_notificacao = 'USUARIO_CRIADO';
```

2. Verifique se o template existe no Brevo:
   - Acesse https://app.brevo.com
   - Vá em Templates
   - Confirme o ID

3. Verifique se o template está ativo no Brevo

### Problema: E-mails não chegam

**Verificações:**

1. **Verificar spam/lixo eletrônico**
2. **Verificar logs do Brevo:**
   - Acesse https://app.brevo.com
   - Vá em **Statistics** > **Email activity**
   - Veja status de entrega

3. **Verificar remetente validado:**
   - Remetentes devem estar validados no Brevo
   - Domínio deve estar verificado

4. **Verificar logs da aplicação:**
```bash
grep -i "brevo" logs/upsaude.log | tail -20
```

### Problema: Dispatcher não processa notificações

**Verificações:**

1. **Verificar se está habilitado:**
```properties
brevo.enabled=true
```

2. **Verificar logs:**
```bash
grep "NotificacaoDispatcherJob" logs/upsaude.log
```

3. **Verificar se há notificações pendentes:**
```sql
SELECT COUNT(*) 
FROM notificacoes 
WHERE status_envio = 'PENDENTE' 
  AND (data_envio_prevista IS NULL OR data_envio_prevista <= NOW());
```

4. **Verificar intervalo configurado:**
```properties
brevo.dispatcher.interval-seconds=30
```

---

## Configurações Avançadas

### Ajustar Intervalo do Dispatcher

```properties
# Processar notificações a cada 10 segundos (mais rápido)
brevo.dispatcher.interval-seconds=10

# Processar notificações a cada 60 segundos (mais lento)
brevo.dispatcher.interval-seconds=60
```

### Ajustar Intervalo de Lembretes

```properties
# Verificar lembretes a cada 5 minutos (mais frequente)
brevo.reminder-scheduler.interval-minutes=5

# Verificar lembretes a cada 30 minutos (menos frequente)
brevo.reminder-scheduler.interval-minutes=30
```

### Configurar Retry

```properties
# Máximo de tentativas
brevo.retry.max-attempts=5

# Backoff entre tentativas (em milissegundos)
brevo.retry.backoff-millis=10000
```

### Desabilitar Temporariamente

```properties
# Desabilitar integração Brevo (notificações não serão enviadas)
brevo.enabled=false
```

---

## Checklist de Configuração

- [ ] Conta Brevo criada e ativa
- [ ] API Key obtida e configurada em variável de ambiente
- [ ] Remetentes validados no Brevo
- [ ] Migration executada no banco de dados
- [ ] Variável `BREVO_API_KEY` configurada no ambiente
- [ ] Templates criados no Brevo para todos os tipos
- [ ] Templates cadastrados no sistema com `brevoTemplateId`
- [ ] Aplicação reiniciada após configurações
- [ ] Teste de criação de usuário realizado
- [ ] E-mail recebido com sucesso
- [ ] Logs verificados sem erros

---

## Próximos Passos

Após concluir a configuração:

1. Leia [TEMPLATES.md](./TEMPLATES.md) para criar templates completos
2. Consulte [NEGOCIO.md](./NEGOCIO.md) para entender regras de negócio
3. Veja [EXEMPLOS.md](./EXEMPLOS.md) para exemplos práticos
4. Integre no frontend seguindo [INTEGRACAO_FRONT.md](./INTEGRACAO_FRONT.md)
