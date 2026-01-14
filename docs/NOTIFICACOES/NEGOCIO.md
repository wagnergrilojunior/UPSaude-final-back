# Documentação de Negócio — Módulo de Notificações Brevo

## Visão Geral de Negócio

O módulo de notificações por e-mail permite que o sistema UPSaúde comunique-se automaticamente com usuários, pacientes e profissionais de saúde através de e-mails transacionais profissionais. As notificações são disparadas automaticamente em eventos importantes do sistema, garantindo que os usuários sejam informados em tempo real sobre ações e mudanças relevantes.

---

## Objetivos de Negócio

### 1. Comunicação Proativa

- **Objetivo**: Manter usuários informados sobre eventos importantes
- **Benefício**: Reduz necessidade de consultas manuais ao sistema
- **Exemplo**: Usuário recebe e-mail quando sua senha é alterada

### 2. Melhoria da Experiência do Usuário

- **Objetivo**: Prover comunicação clara e profissional
- **Benefício**: Aumenta confiança e satisfação dos usuários
- **Exemplo**: E-mail de boas-vindas personalizado para novos usuários

### 3. Redução de Falhas em Agendamentos

- **Objetivo**: Enviar lembretes automáticos de agendamentos
- **Benefício**: Reduz faltas e aumenta taxa de comparecimento
- **Exemplo**: Lembrete 24h e 1h antes do agendamento

### 4. Segurança e Auditoria

- **Objetivo**: Notificar sobre alterações de segurança
- **Benefício**: Usuário é alertado sobre mudanças em sua conta
- **Exemplo**: Notificação quando senha é alterada

### 5. Transparência

- **Objetivo**: Informar sobre atualizações de dados pessoais
- **Benefício**: Usuário tem visibilidade sobre alterações em seu cadastro
- **Exemplo**: Notificação quando dados pessoais são atualizados

---

## Casos de Uso

### 1. Criação de Novo Usuário

**Cenário**: Um administrador cria um novo usuário no sistema.

**Fluxo**:
1. Administrador cria usuário via interface ou API
2. Sistema valida dados e cria usuário
3. Sistema dispara evento de criação
4. `NotificacaoOrchestrator` cria notificação do tipo `USUARIO_CRIADO`
5. Notificação é enfileirada com status `PENDENTE`
6. `NotificacaoDispatcherJob` processa e envia via Brevo
7. Usuário recebe e-mail de boas-vindas

**Conteúdo do E-mail**:
- Saudação personalizada com nome do usuário
- Informações de acesso ao sistema
- Instruções iniciais
- Contato para suporte

**Benefícios**:
- Usuário é informado imediatamente sobre sua conta
- Reduz dúvidas sobre acesso ao sistema
- Melhora onboarding de novos usuários

---

### 2. Alteração de Senha

**Cenário**: Usuário altera sua senha no sistema.

**Fluxo**:
1. Usuário solicita alteração de senha
2. Sistema valida e atualiza senha
3. Sistema dispara evento de alteração
4. `NotificacaoOrchestrator` cria notificação do tipo `SENHA_ALTERADA`
5. Notificação é enviada imediatamente
6. Usuário recebe e-mail de confirmação

**Conteúdo do E-mail**:
- Confirmação de alteração de senha
- Data e hora da alteração
- Instruções caso não tenha sido o usuário
- Contato para suporte em caso de fraude

**Benefícios**:
- Segurança: usuário é alertado sobre mudanças
- Prevenção de fraude: usuário pode reportar alterações não autorizadas
- Auditoria: registro de todas as alterações

---

### 3. Atualização de Dados Pessoais

**Cenário**: Dados pessoais de usuário ou paciente são atualizados.

**Fluxo**:
1. Usuário ou profissional atualiza dados pessoais
2. Sistema valida e salva alterações
3. Sistema dispara evento de atualização
4. `NotificacaoOrchestrator` cria notificação do tipo `DADOS_PESSOAIS_ATUALIZADOS`
5. Notificação é enviada
6. Usuário/paciente recebe e-mail de confirmação

**Conteúdo do E-mail**:
- Confirmação de atualização
- Data e hora da alteração
- Instruções para verificar dados atualizados
- Contato para suporte

**Benefícios**:
- Transparência: usuário sabe quando seus dados são alterados
- Segurança: detecta alterações não autorizadas
- Conformidade: atende requisitos de LGPD

---

### 4. Confirmação de Agendamento

**Cenário**: Um agendamento é confirmado no sistema.

**Fluxo**:
1. Profissional ou sistema confirma agendamento
2. Sistema atualiza status para `CONFIRMADO`
3. Sistema dispara evento de confirmação
4. `NotificacaoOrchestrator` cria notificação do tipo `AGENDAMENTO_CONFIRMADO`
5. `NotificacaoOrchestrator` agenda lembretes (24h e 1h antes)
6. Notificação de confirmação é enviada imediatamente
7. Paciente recebe e-mail de confirmação

**Conteúdo do E-mail**:
- Confirmação do agendamento
- Data, hora e local
- Nome do profissional/médico
- Instruções de comparecimento
- Opções de cancelamento/reagendamento

**Benefícios**:
- Paciente tem confirmação escrita do agendamento
- Reduz dúvidas sobre data/hora
- Melhora taxa de comparecimento

---

### 5. Cancelamento de Agendamento

**Cenário**: Um agendamento é cancelado.

**Fluxo**:
1. Profissional ou paciente cancela agendamento
2. Sistema atualiza status para `CANCELADO`
3. Sistema dispara evento de cancelamento
4. `NotificacaoOrchestrator` cria notificação do tipo `AGENDAMENTO_CANCELADO`
5. Notificação é enviada
6. Paciente recebe e-mail de cancelamento

**Conteúdo do E-mail**:
- Informação sobre cancelamento
- Data e hora do agendamento cancelado
- Motivo do cancelamento (se informado)
- Instruções para reagendamento
- Contato para suporte

**Benefícios**:
- Paciente é informado sobre cancelamento
- Reduz confusão e frustração
- Facilita reagendamento

---

### 6. Lembrete de Agendamento (24 Horas)

**Cenário**: Sistema envia lembrete 24 horas antes do agendamento.

**Fluxo**:
1. `AgendamentoReminderScheduler` executa (a cada 15 minutos)
2. Busca agendamentos confirmados entre 23h e 25h no futuro
3. Verifica se lembrete 24h já foi enviado (`notificacaoEnviada24h = false`)
4. Verifica configuração do estabelecimento
5. Cria notificação do tipo `LEMBRETE_24H` com `dataEnvioPrevista` = 24h antes
6. `NotificacaoDispatcherJob` envia quando chegar a hora
7. Paciente recebe e-mail de lembrete

**Conteúdo do E-mail**:
- Lembrete do agendamento
- Data, hora e local
- Nome do profissional
- Instruções de preparação (se aplicável)
- Opções de cancelamento/reagendamento

**Benefícios**:
- Reduz faltas em agendamentos
- Dá tempo para paciente se preparar
- Melhora organização do paciente

---

### 7. Lembrete de Agendamento (1 Hora)

**Cenário**: Sistema envia lembrete 1 hora antes do agendamento.

**Fluxo**:
1. `AgendamentoReminderScheduler` executa (a cada 15 minutos)
2. Busca agendamentos confirmados entre 55min e 2h no futuro
3. Verifica se lembrete 1h já foi enviado (`notificacaoEnviada1h = false`)
4. Verifica configuração do estabelecimento
5. Cria notificação do tipo `LEMBRETE_1H` com `dataEnvioPrevista` = 1h antes
6. `NotificacaoDispatcherJob` envia quando chegar a hora
7. Paciente recebe e-mail de lembrete

**Conteúdo do E-mail**:
- Lembrete urgente do agendamento
- Data, hora e local
- Instruções de comparecimento
- Contato em caso de atraso

**Benefícios**:
- Último lembrete antes do agendamento
- Reduz atrasos
- Melhora pontualidade

---

## Regras de Negócio

### RN-001: Notificações Automáticas

**Regra**: Notificações são criadas automaticamente quando eventos ocorrem no sistema.

**Exceções**:
- Se `brevo.enabled=false`, notificações são criadas mas não enviadas
- Se template não estiver configurado (`brevoTemplateId` ausente), notificação fica pendente
- Se estabelecimento desabilitou notificações, não são criadas

**Aplicação**: Implementada em `NotificacaoOrchestrator`

---

### RN-002: Prioridade de Templates

**Regra**: Templates específicos de estabelecimento têm prioridade sobre templates globais.

**Ordem de busca**:
1. Template do estabelecimento específico
2. Template global do tenant
3. Template padrão do sistema

**Aplicação**: Implementada em `NotificacaoOrchestrator.criarNotificacao()`

---

### RN-003: Configuração por Estabelecimento

**Regra**: Estabelecimentos podem habilitar/desabilitar tipos de notificação.

**Configurações disponíveis**:
- `enviaNotificacaoEmail` - Habilitar/desabilitar todas as notificações por e-mail
- `enviaLembrete24h` - Habilitar/desabilitar lembretes 24h
- `enviaLembrete1h` - Habilitar/desabilitar lembretes 1h

**Aplicação**: Verificada em `AgendamentoReminderScheduler`

---

### RN-004: Prevenção de Duplicidade

**Regra**: Lembretes de agendamento não são enviados mais de uma vez.

**Mecanismo**:
- Flags `notificacaoEnviada24h` e `notificacaoEnviada1h` no agendamento
- Flags são marcadas quando notificação é criada
- Scheduler verifica flags antes de criar nova notificação

**Aplicação**: Implementada em `AgendamentoReminderScheduler`

---

### RN-005: Retry Automático

**Regra**: Notificações com falha são reenviadas automaticamente.

**Estratégia**:
- Tentativa 1: Imediata
- Tentativa 2: +5 minutos
- Tentativa 3: +15 minutos
- Tentativa 4+: +1 hora
- Máximo: 3 tentativas (configurável)

**Aplicação**: Implementada em `NotificacaoDispatcherJob`

---

### RN-006: Horário de Envio

**Regra**: Notificações podem ser agendadas para envio futuro.

**Uso**:
- Lembretes são agendados para 24h e 1h antes do agendamento
- `dataEnvioPrevista` controla quando notificação será enviada
- Dispatcher só processa notificações com `dataEnvioPrevista <= agora`

**Aplicação**: Implementada em `NotificacaoRepository.findPendentesParaEnvio()`

---

### RN-007: Multitenancy

**Regra**: Notificações são isoladas por tenant.

**Mecanismo**:
- Tenant é resolvido automaticamente via usuário autenticado
- Templates e notificações são filtrados por tenant
- Não é possível acessar notificações de outros tenants

**Aplicação**: Implementada em todas as queries e serviços

---

### RN-008: Validação de E-mail

**Regra**: E-mail do destinatário deve ser válido.

**Validação**:
- Formato de e-mail válido
- Não pode estar vazio
- Máximo 255 caracteres

**Aplicação**: Validação em `NotificacaoRequest` e `NotificacaoOrchestrator`

---

### RN-009: Template Obrigatório

**Regra**: Notificação só é enviada se template estiver configurado.

**Validação**:
- Template deve existir para tipo/canal
- `brevoTemplateId` deve estar configurado
- Template deve estar ativo no Brevo

**Aplicação**: Verificada em `NotificacaoDispatcherJob`

---

### RN-010: Auditoria Completa

**Regra**: Todas as notificações são registradas para auditoria.

**Informações registradas**:
- Tipo, canal, destinatário
- Status de envio, tentativas
- Data de envio, erro (se houver)
- ID externo (messageId do Brevo)
- Parâmetros enviados

**Aplicação**: Implementada em entidade `Notificacao`

---

## Fluxos de Negócio

### Fluxo 1: Novo Usuário → Notificação

```
[Usuário Criado]
    ↓
[NotificacaoOrchestrator.notificarUsuarioCriado()]
    ↓
[Buscar Template USUARIO_CRIADO/EMAIL]
    ↓
[Criar Notificacao (status=PENDENTE)]
    ↓
[Dispatcher processa (30s)]
    ↓
[Enviar via Brevo]
    ↓
[Atualizar status=ENVIADO]
    ↓
[Usuário recebe e-mail]
```

### Fluxo 2: Agendamento Confirmado → Lembretes

```
[Agendamento Confirmado]
    ↓
[NotificacaoOrchestrator.notificarAgendamentoConfirmado()]
    ↓
[Enviar confirmação imediatamente]
    ↓
[Agendar lembrete 24h]
    ↓
[Agendar lembrete 1h]
    ↓
[Scheduler verifica (15min)]
    ↓
[Quando chegar 24h antes → Criar notificação]
    ↓
[Quando chegar 1h antes → Criar notificação]
    ↓
[Dispatcher envia lembretes]
```

---

## Métricas e KPIs

### Métricas Recomendadas

1. **Taxa de Entrega**
   - Notificações enviadas / Notificações criadas
   - Meta: > 95%

2. **Taxa de Sucesso**
   - Notificações com status ENVIADO / Total
   - Meta: > 98%

3. **Tempo Médio de Processamento**
   - Tempo entre criação e envio
   - Meta: < 1 minuto

4. **Taxa de Falha**
   - Notificações com status FALHA / Total
   - Meta: < 2%

5. **Taxa de Abertura** (futuro - via webhooks Brevo)
   - E-mails abertos / E-mails enviados
   - Meta: > 20%

---

## Conformidade e Segurança

### LGPD (Lei Geral de Proteção de Dados)

- **Consentimento**: Usuário deve consentir com notificações
- **Transparência**: E-mails informam sobre uso de dados
- **Direito ao Esquecimento**: Usuário pode solicitar exclusão de dados
- **Segurança**: Dados são protegidos e criptografados

### Boas Práticas

- E-mails não contêm informações sensíveis sem criptografia
- Remetentes são verificados e validados
- Rate limiting para evitar spam
- Logs de auditoria completos

---

## Próximas Expansões

### WhatsApp (Planejado)

- Estrutura já preparada para múltiplos canais
- Integração com API do WhatsApp Business
- Templates de mensagem WhatsApp

### SMS (Planejado)

- Notificações por SMS para casos urgentes
- Integração com provedor de SMS
- Templates de SMS

### Dashboard de Métricas

- Visualização de estatísticas de envio
- Taxa de entrega e abertura
- Análise de performance

---

Para mais detalhes técnicos, consulte:
- [TECNICO.md](./TECNICO.md) - Arquitetura técnica
- [CONFIGURACAO.md](./CONFIGURACAO.md) - Configuração passo a passo
