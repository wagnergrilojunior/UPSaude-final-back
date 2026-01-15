# FINANCEIRO — Documentação de Negócio

## 📚 Links Úteis

- **Fluxos e ordem de integração**: [FLUXOS_E_SEQUENCIAS.md](./FLUXOS_E_SEQUENCIAS.md)
- **Dados e status para UI**: [DADOS_E_STATUS.md](./DADOS_E_STATUS.md)
- **Catálogo de endpoints (por domínio)**: [ENDPOINTS.md](./ENDPOINTS.md)
- **Documentação técnica**: [TECNICO.md](./TECNICO.md)

## 🎯 Objetivo do Módulo

O módulo **Financeiro** existe para controlar **recursos públicos** destinados aos atendimentos ambulatoriais, com:

- **Saldo por município (tenant)** e por **competência financeira**
- **Rastreabilidade total** (auditoria) de créditos, reservas, consumos e estornos
- **Automação**: o usuário final não precisa "lançar financeiro" manualmente no dia a dia
- **Proibição de saldo negativo** (regra de negócio — o sistema deve bloquear operações que ultrapassem o orçamento)
- **Integração com produção ambulatorial/BPA** (fechamento por competência e consistência dos dados)

> **Observação importante**: o domínio foi desenhado para suportar um financeiro "completo" (contas, conciliação, títulos, lançamentos, plano de contas). Porém, o **fluxo de BPA/fechamento completo** ainda depende de evoluções específicas (ver [TECNICO.md](./TECNICO.md)).

## 📖 Conceitos (Linguagem de Negócio)

### Competência Financeira

Período de referência do financeiro (normalmente **mensal**, mas pode ser personalizado).

- **Exemplo**: `2026-01` (01/01/2026 a 31/01/2026)
- **Tipos**: `MENSAL`, `BIMESTRAL`, `TRIMESTRAL`, `SEMESTRAL`, `ANUAL`, `OUTRO`
- **Status**: `ABERTA` ou `FECHADA`
- **Uso**: Todas as operações financeiras devem estar vinculadas a uma competência

### Tenant = Município

Cada município é um **tenant** e possui:

- Seu próprio orçamento/saldo por competência
- Visão segregada dos seus registros financeiros
- Isolamento completo de dados entre municípios

### Orçamento por Competência

É o "espelho" do saldo do município na competência, contendo:

- **saldoAnterior**: Saldo da competência anterior (quando aplicável)
- **creditos**: Créditos liberados na competência
- **reservasAtivas**: Compromissos ainda não consumidos
- **consumos**: Execução/atendimento concluído
- **estornos**: Cancelamentos/no-shows/ajustes
- **despesasAdmin**: Despesas administrativas (quando aplicável)
- **saldoFinal**: Saldo final calculado
- **saldoDisponivel**: Saldo disponível para novas reservas (calculado)

**Fórmula de Cálculo**:
```
saldoDisponivel = saldoAnterior + creditos - reservasAtivas - consumos + estornos - despesasAdmin
```

### Reserva (Compromisso)

Uma reserva é uma "separação do saldo" para um evento futuro.

**No modelo atual (híbrido)**:

- **Reserva** ocorre quando o agendamento é **CONFIRMADO**
- **Consumo** ocorre quando o atendimento é **CONCLUIDO**
- **Estorno** ocorre quando o agendamento é **CANCELADO/FALTA/REAGENDADO** (ou quando o atendimento é CANCELADO/FALTA_PACIENTE)

**Status da Reserva**:
- `ATIVA`: Reserva criada, aguardando consumo
- `CONSUMIDA`: Reserva foi consumida (atendimento concluído)
- `LIBERADA`: Reserva foi estornada/liberada
- `PARCIAL`: Reserva parcialmente consumida (uso futuro)

### Estorno

Estorno significa "devolver saldo" (ou desfazer um consumo). Ele precisa ser:

- **Automático** quando aplicável (cancelamentos, faltas)
- **Auditável** (registro completo com motivo)
- **Com motivo** e vínculo ao evento que originou

**Motivos de Estorno**:
- `CANCELAMENTO`: Agendamento/atendimento cancelado
- `FALTA_PACIENTE`: Paciente não compareceu
- `NAO_EXECUTADO`: Procedimento não executado
- `AJUSTE`: Ajuste manual/operacional
- `OUTRO`: Outros motivos

## 🔄 Fluxos de Negócio

### 1) Preparação da Competência (Backoffice)

**Antes de operar o dia a dia, precisa existir**:

1. Uma **Competência Financeira** cadastrada
2. Um **Orçamento da Competência** (do tenant/município)
3. **Créditos** (se houver) para compor o saldo

**Sem isso, o sistema não consegue reservar automaticamente.**

**Ordem recomendada**:
1. Criar competência financeira (`POST /v1/financeiro/competencias`)
2. Criar orçamento da competência (`POST /v1/financeiro/orcamentos-competencia`)
3. Lançar créditos orçamentários (`POST /v1/financeiro/creditos-orcamentarios`)

### 2) Fluxo Ambulatorial Padrão (Modelo Híbrido)

#### 2.1 Confirmar Agendamento → Reservar Orçamento

**Quando um agendamento entra em `CONFIRMADO`**:

- O sistema cria uma **Reserva Orçamentária Assistencial**
- Marca o agendamento como `statusFinanceiro = RESERVADO`

**Pré-requisitos para a reserva automática funcionar**:

- `agendamento.competenciaFinanceira` preenchido
- `agendamento.valorEstimadoTotal > 0`
- Saldo disponível suficiente no orçamento

**Se o agendamento estiver sem competência/valor, o sistema não reserva** (o front deve evitar esse cenário).

#### 2.2 Concluir Atendimento → Consumir Reserva

**Quando um atendimento muda para `CONCLUIDO`**:

- O sistema procura o agendamento vinculado
- Marca a reserva como `CONSUMIDA`
- Marca o agendamento como `statusFinanceiro = CONSUMIDO`
- Atualiza o orçamento (diminui reservas ativas, aumenta consumos)

#### 2.3 Cancelar/No-show → Estornar

**Se o agendamento virar**:

- `CANCELADO` **ou** `FALTA` **ou** `REAGENDADO`

**O sistema**:

- Registra um **Estorno Financeiro** (quando possível, com paciente)
- Marca a reserva como `LIBERADA`
- Marca o agendamento como `statusFinanceiro = ESTORNADO`
- Atualiza o orçamento (diminui reservas ativas, aumenta estornos)

**Se o atendimento virar**:

- `CANCELADO` **ou** `FALTA_PACIENTE`

**O sistema tenta estornar via o agendamento vinculado**.

## 💰 Exemplo Completo (Com Números)

### Cenário

- Município A (tenant A)
- Competência: `2026-01`
- Saldo inicial: R$ 0,00
- Crédito liberado na competência: R$ 10.000,00

### Passo a Passo

**1) Crédito**: +R$ 10.000,00
- Saldo disponível = R$ 10.000,00

**2) Agendamento CONFIRMADO** (estimado R$ 120,50):
- Reserva: -R$ 120,50 (como "reservas ativas")
- Saldo disponível passa a R$ 9.879,50

**3) Atendimento CONCLUIDO**:
- Reserva vira consumo: "reservas ativas" diminui, "consumos" aumenta
- Saldo disponível permanece coerente, mas o uso fica registrado como realizado

**4) Agendamento CANCELADO** (antes do consumo):
- Reserva é liberada
- Estorno é registrado
- Saldo disponível volta a R$ 10.000,00

## ⚠️ Regras de Negócio Críticas

### Para UI/UX do Front

1. **Bloqueio por Saldo**
   - A UI deve impedir (ou alertar) quando não houver saldo para confirmar
   - Na prática: a confirmação pode falhar (400/409) por regras financeiras
   - **Recomendação**: Verificar saldo disponível antes de permitir confirmação

2. **Operação Sempre por Competência**
   - Agendamento/atendimento devem estar vinculados a uma competência
   - Sem competência, não há reserva automática
   - **Recomendação**: Seleção obrigatória de competência no formulário

3. **Auditabilidade**
   - A UI deve expor histórico (reservas, estornos, logs)
   - Permitir filtragem por competência
   - Exibir trilha completa de operações financeiras

4. **Sem Alteração Retroativa**
   - Correções devem acontecer via **ajustes/estornos**
   - Não permitir "editar o passado"
   - **Recomendação**: Usar operações explícitas para correções

5. **Idempotência**
   - Operações de reserva são idempotentes (não criam duplicatas)
   - Sistema previne reservas duplicadas automaticamente

6. **Validação de Valor**
   - `valorEstimadoTotal` deve ser maior que zero para reserva automática
   - Valores negativos não são permitidos

## 🖥️ Sugestão de "Mapa de Telas" (Mínimo Recomendado)

### Painel Financeiro do Município (por competência)

- **Funcionalidade**: Visão geral do orçamento
- **Dados exibidos**: `saldoDisponivel`, `creditos`, `reservasAtivas`, `consumos`, `estornos`
- **Fonte**: `GET /v1/financeiro/orcamentos-competencia`
- **Filtros**: Por competência, período

### Tela de Reservas

- **Funcionalidade**: Listagem e auditoria de reservas
- **Dados exibidos**: Reservas `ATIVA` / `CONSUMIDA` / `LIBERADA`
- **Fonte**: `GET /v1/financeiro/reservas-orcamentarias`
- **Filtros**: Por status, competência, período, agendamento

### Tela de Estornos

- **Funcionalidade**: Histórico de estornos
- **Dados exibidos**: Estornos com motivo e vínculo ao evento
- **Fonte**: `GET /v1/financeiro/estornos`
- **Filtros**: Por motivo, competência, período, paciente

### Detalhe do Agendamento

- **Funcionalidade**: Exibir informações financeiras do agendamento
- **Dados exibidos**: 
  - `competenciaFinanceira`
  - `valorEstimadoTotal`
  - `statusFinanceiro` (SEM_RESERVA | RESERVADO | CONSUMIDO | ESTORNADO | AJUSTADO)
- **Fonte**: `GET /v1/agendamentos/{id}`

### Detalhe do Atendimento

- **Funcionalidade**: Exibir informações financeiras do atendimento
- **Dados exibidos**: 
  - `competenciaFinanceira`
  - Status clínico
  - Vínculo com agendamento (se houver)
- **Fonte**: `GET /v1/atendimentos/{id}`

## 📊 Exemplos de Consultas (Para Relatórios Simples no Front)

### Orçamento por Competência (Paginado)

```bash
GET /api/v1/financeiro/orcamentos-competencia?page=0&size=20&sort=createdAt,desc
```

### Reservas (Paginado)

```bash
GET /api/v1/financeiro/reservas-orcamentarias?page=0&size=20&sort=createdAt,desc
```

### Estornos (Paginado)

```bash
GET /api/v1/financeiro/estornos?page=0&size=20&sort=dataEstorno,desc
```

> **Nota**: Filtros avançados (por prestador, procedimento, período) ainda não estão expostos como query params dedicados em todos endpoints; no MVP, o front pode filtrar client-side e depois evoluímos para endpoints de relatório.

## 📋 O que o Front Deve Exibir (Mínimo Recomendado)

### No Agendamento

- `competenciaFinanceira` (objeto com código e descrição)
- `valorEstimadoTotal` (formatado como moeda)
- `statusFinanceiro` (SEM_RESERVA | RESERVADO | CONSUMIDO | ESTORNADO | AJUSTADO)
- Link para detalhes da reserva (se houver)

### Painel Financeiro do Município

- **Orçamento por competência**:
  - Saldo anterior
  - Créditos
  - Reservas ativas
  - Consumos
  - Estornos
  - Saldo disponível (destacado)
- **Estornos com motivo** e vínculo ao evento
- **Reservas ativas** (pendências)
- **Gráficos** (opcional): evolução do saldo, distribuição de consumos, etc.

### Indicadores Visuais

- **Saldo disponível**: Verde se positivo, vermelho se negativo (não deve ocorrer)
- **Status financeiro**: Badge colorido (RESERVADO=azul, CONSUMIDO=verde, ESTORNADO=amarelo)
- **Alertas**: Quando saldo está baixo ou próximo de zero

## 🔍 Casos de Uso Especiais

### Reprocessamento

Quando é necessário reprocessar uma operação financeira:

- Usar endpoints de operações explícitas: `/v1/financeiro/operacoes/...`
- Ver [ENDPOINTS_FINANCEIRO_01_OPERACOES.md](./ENDPOINTS_FINANCEIRO_01_OPERACOES.md)

### Ajustes Manuais

Para correções operacionais:

- Criar estorno manual com motivo `AJUSTE`
- Registrar observações no campo de motivo
- Manter rastreabilidade completa

### Fechamento de Competência

- Processo de fechamento ainda em desenvolvimento
- Ver [TECNICO.md](./TECNICO.md) para limitações conhecidas

## 📖 Referências

- [README Principal](./README.md)
- [Fluxos e Sequências](./FLUXOS_E_SEQUENCIAS.md)
- [Dados e Status](./DADOS_E_STATUS.md)
- [Documentação Técnica](./TECNICO.md)
