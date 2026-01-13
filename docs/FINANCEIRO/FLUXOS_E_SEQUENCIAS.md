# Financeiro — Fluxos e Sequências (Guia para Integração)

Este documento descreve a **ordem** e os **gatilhos** que o front deve respeitar para o financeiro funcionar de forma automática e auditável.

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`

---

## Fluxo 0 — Pré-configuração (Backoffice)

Sem isso, o fluxo ambulatorial pode não reservar:

1) Criar **Competência Financeira**
   - `POST /v1/financeiro/competencias`
2) Criar **Orçamento por Competência (tenant)**
   - `POST /v1/financeiro/orcamentos-competencia`
3) Criar **Créditos Orçamentários** (se aplicável)
   - `POST /v1/financeiro/creditos-orcamentarios`

Recomendação de UI:

- tela “Setup Financeiro do Município” com checklist (competência → orçamento → créditos).

---

## Fluxo 1 — Reserva automática (Agendamento)

Objetivo:

- Ao confirmar agendamento, o sistema cria uma reserva e marca o status financeiro.

### Sequência (happy path)

1) Front cria agendamento já confirmado:
   - `POST /v1/agendamentos` com:
     - `status = CONFIRMADO`
     - `competenciaFinanceira = <UUID>`
     - `valorEstimadoTotal > 0`
2) Backend cria o agendamento
3) Backend chama integração financeira:
   - cria `ReservaOrcamentariaAssistencial` (status `ATIVA`)
   - atualiza `Agendamento.statusFinanceiro = RESERVADO`

### Como validar no front

- Recarregue o agendamento (`GET /v1/agendamentos/{id}`) e verifique `statusFinanceiro`.
- Para auditoria, liste reservas:
  - `GET /v1/financeiro/reservas-orcamentarias?page=0&size=20`

### Fluxo alternativo: confirmar depois

1) Front cria agendamento como `AGENDADO`
2) Front atualiza para `CONFIRMADO`:
   - `PUT /v1/agendamentos/{id}` com `status=CONFIRMADO`
3) O backend tenta reservar.

---

## Fluxo 2 — Estorno automático (Agendamento)

Objetivo:

- Cancelamento/no-show/reagendamento liberam a reserva e registram estorno.

### Sequência

1) Front muda agendamento para:
   - `CANCELADO` ou `FALTA` ou `REAGENDADO`
2) Backend tenta:
   - criar `EstornoFinanceiro` (quando possível)
   - marcar `ReservaOrcamentariaAssistencial.status = LIBERADA`
   - marcar `Agendamento.statusFinanceiro = ESTORNADO`

### Como validar

- `GET /v1/financeiro/estornos`
- `GET /v1/financeiro/reservas-orcamentarias` (a reserva deve ficar `LIBERADA`)

---

## Fluxo 3 — Consumo automático (Atendimento)

Objetivo:

- Ao encerrar atendimento, consumir reserva do agendamento vinculado (modelo híbrido).

### Sequência

1) Front encerra atendimento:
   - `PUT /v1/atendimentos/{id}/encerrar`
2) Backend muda o status do atendimento para `CONCLUIDO`
3) Backend localiza o agendamento vinculado ao atendimento
4) Backend marca:
   - `ReservaOrcamentariaAssistencial.status = CONSUMIDA`
   - `Agendamento.statusFinanceiro = CONSUMIDO`

### Observação crítica

- Se o atendimento não tiver agendamento vinculado, **não há reserva a consumir** (o front deve garantir o vínculo quando for um fluxo derivado de agendamento).

---

## Fluxo 4 — Operações explícitas (reprocessamento)

Quando o financeiro automático não é suficiente (correção operacional), use:

- Reservar: `POST /v1/financeiro/operacoes/agendamentos/{agendamentoId}/reservar`
- Estornar: `POST /v1/financeiro/operacoes/agendamentos/{agendamentoId}/estornar`
- Consumir: `POST /v1/financeiro/operacoes/atendimentos/{atendimentoId}/consumir`
- Estornar consumo: `POST /v1/financeiro/operacoes/atendimentos/{atendimentoId}/estornar`

---

## Fluxo 5 — Faturamento (BPA e documentos)

Hoje o sistema tem:

- cadastro de documento de faturamento
- vínculo com competência/agendamento/atendimento/guia

O “processo completo de BPA” (geração de arquivo + fechamento com hash/snapshot) está planejado como evolução.

