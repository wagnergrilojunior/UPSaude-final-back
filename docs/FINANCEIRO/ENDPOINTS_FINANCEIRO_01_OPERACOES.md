# Financeiro — Endpoints de Operações (Ações Explícitas)

## Quando usar (regra prática para o Front)

Na maior parte do tempo, o financeiro é **automático** e você só precisa:

- **mudar status do agendamento** (CONFIRMADO/CANCELADO/FALTA/REAGENDADO)
- **encerrar/cancelar atendimento** (CONCLUIR/CANCELAR/FALTA_PACIENTE)

Use estes endpoints de **operação explícita** quando:

- precisa **reprocessar** reserva/consumo/estorno por ajuste operacional
- precisa disparar um processo (ex.: fechamento) fora do fluxo normal
- está integrando uma rotina batch/importação

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Retorno**: todos retornam `204 No Content` quando a operação é aceita/executada com sucesso

---

## 1) Reservar orçamento para agendamento

- **POST** `/v1/financeiro/operacoes/agendamentos/{agendamentoId}/reservar`

Exemplo:

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/operacoes/agendamentos/<AGENDAMENTO_ID>/reservar" \
  -H "Authorization: Bearer <TOKEN>"
```

Observações importantes:

- A reserva depende do agendamento ter **competência** e **valor estimado**.
- A implementação atual tem **idempotência simples**: se a reserva já existir para o agendamento, não cria outra.

---

## 2) Estornar (liberar) reserva de agendamento

- **POST** `/v1/financeiro/operacoes/agendamentos/{agendamentoId}/estornar`

Body:

```json
{ "motivo": "CANCELAMENTO" }
```

Exemplo:

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/operacoes/agendamentos/<AGENDAMENTO_ID>/estornar" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{ "motivo": "CANCELAMENTO" }'
```

Observações:

- O estorno **não apaga** a reserva; marca como **LIBERADA** (para auditoria).
- Quando possível, registra um **EstornoFinanceiro** com paciente/motivo/valor.

---

## 3) Consumir reserva no atendimento

- **POST** `/v1/financeiro/operacoes/atendimentos/{atendimentoId}/consumir`

Exemplo:

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/operacoes/atendimentos/<ATENDIMENTO_ID>/consumir" \
  -H "Authorization: Bearer <TOKEN>"
```

Observações:

- O consumo atual procura o **agendamento vinculado ao atendimento** e consome a reserva desse agendamento (modelo híbrido).

---

## 4) Estornar consumo de atendimento

- **POST** `/v1/financeiro/operacoes/atendimentos/{atendimentoId}/estornar`

Body:

```json
{ "motivo": "CANCELAMENTO_ATENDIMENTO" }
```

Exemplo:

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/operacoes/atendimentos/<ATENDIMENTO_ID>/estornar" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{ "motivo": "CANCELAMENTO_ATENDIMENTO" }'
```

---

## 5) Fechar competência financeira

- **POST** `/v1/financeiro/operacoes/competencias/{competenciaFinanceiraId}/fechar`

Exemplo:

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/operacoes/competencias/<COMPETENCIA_ID>/fechar" \
  -H "Authorization: Bearer <TOKEN>"
```

Estado atual:

- Existe o endpoint e o método no serviço, mas o fechamento completo (BPA, hash/snapshot, travas) ainda é uma evolução.

