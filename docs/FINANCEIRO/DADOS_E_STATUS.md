# Financeiro — Dados, Campos-Chave e Status (para Front/BI)

Este arquivo existe para evitar “achismo” no frontend: aqui estão os campos e status que aparecem no dia a dia.

## 1) Convenções de API

- **Base URL**: `http://localhost:8080/api`
- **Autenticação**: `Authorization: Bearer <TOKEN>`
- Responses geralmente seguem o padrão:
  - `id`, `createdAt`, `updatedAt`, `active` + campos específicos

## 2) Status do Agendamento (gatilhos financeiros)

No sistema:

- `CONFIRMADO` → tenta reservar
- `CANCELADO` / `FALTA` / `REAGENDADO` → tenta estornar reserva

Status possíveis (enum `StatusAgendamentoEnum`):

- `AGENDADO`
- `CONFIRMADO`
- `AGUARDANDO`
- `EM_ATENDIMENTO`
- `CONCLUIDO`
- `CANCELADO`
- `FALTA`
- `REAGENDADO`
- `SUSPENSO`
- `ENCAIXE`

## 3) Status do Atendimento (gatilhos financeiros)

Status possíveis (enum `StatusAtendimentoEnum`):

- `AGENDADO`
- `EM_ESPERA`
- `EM_ANDAMENTO`
- `CONCLUIDO` → tenta consumir
- `CANCELADO` → tenta estornar
- `FALTA_PACIENTE` → tenta estornar
- `REMARCADO`
- `SUSPENSO`
- `INTERROMPIDO`
- `OUTRO`

## 4) Status financeiro do agendamento (campo string)

Campo: `AgendamentoResponse.statusFinanceiro`

Valores usados/esperados (documentação e implementação atual):

- `SEM_RESERVA` (quando não houve reserva)
- `RESERVADO` (reserva criada)
- `CONSUMIDO` (reserva consumida)
- `ESTORNADO` (reserva estornada/liberada)
- `AJUSTADO` (uso futuro)

> Observação: por ser string, trate como “enum aberta” no front (fallback para label genérica).

## 5) Reserva Orçamentária Assistencial

Campos-chave:

- `competencia` (UUID)
- `agendamento` (UUID) / `guiaAmbulatorial` / `documentoFaturamento`
- `valorReservadoTotal`
- `status`: `ATIVA` | `CONSUMIDA` | `LIBERADA` | `PARCIAL`

## 6) Estorno Financeiro

Campos-chave:

- `competencia`
- `agendamento` / `atendimento` / `guiaAmbulatorial`
- `paciente`
- `motivo`: `CANCELAMENTO` | `FALTA_PACIENTE` | `NAO_EXECUTADO` | `AJUSTE` | `OUTRO`
- `valorEstornado`
- `dataEstorno`

## 7) Orçamento da Competência (saldo do município)

Campos principais:

- `saldoAnterior`
- `creditos`
- `reservasAtivas`
- `consumos`
- `estornos`
- `despesasAdmin`
- `saldoFinal`
- `saldoDisponivel` (calculado)

## 8) Competência Tenant

Campos principais:

- `competencia`
- `status`: `ABERTA` | `FECHADA`
- `motivoFechamento`

## 9) Lançamento Financeiro

Campos principais:

- `competencia`
- `origemTipo` (ex.: `AGENDAMENTO`, `ATENDIMENTO`, `DOCUMENTO_FATURAMENTO`, `AJUSTE_SISTEMA`)
- `status`: `PREVISTO` | `REALIZADO` | `ESTORNADO` | `AJUSTADO` | `CANCELADO_POR_REVERSAO`
- `dataEvento`
- `itens[]` com `tipoPartida`: `DEBITO` | `CREDITO`

## 10) Faturamento (para BPA/conta a receber)

Documento:

- `tipo`: `BPA` | `APAC` | `TISS` | `FATURA` | `GUIA_AMBULATORIAL` | `NOTA`
- `status`: (string — depende do processo do cliente)
- `payloadLayout` (JSON em string — pode guardar layout/valores de exportação)

