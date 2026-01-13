# Assistencial — Endpoints Relacionados ao Financeiro (Gatilhos)

O financeiro “acontece” automaticamente quando o front executa certas transições no **Assistencial**.

Este documento lista os endpoints do Assistencial que impactam diretamente:

- reserva
- consumo
- estorno

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`

---

## 1) Agendamentos (disparam reserva/estorno)

**Base path:** `/v1/agendamentos`

### Endpoints

- `POST /v1/agendamentos`
- `GET /v1/agendamentos`
- `GET /v1/agendamentos/{id}`
- `PUT /v1/agendamentos/{id}`
- `DELETE /v1/agendamentos/{id}`
- `PUT /v1/agendamentos/{id}/inativar`

### Campos financeiros relevantes (AgendamentoRequest)

- `competenciaFinanceira` (UUID)
- `valorEstimadoTotal` (BigDecimal)
- `statusFinanceiro` (string — o backend atualiza conforme a integração)

### Transições de status que disparam eventos financeiros

Quando o front muda `status` do agendamento para:

- `CONFIRMADO` → o backend tenta **reservar orçamento**
- `CANCELADO` → o backend tenta **estornar reserva**
- `FALTA` → o backend tenta **estornar reserva**
- `REAGENDADO` → o backend tenta **estornar reserva**

Exemplo (confirmar):

```json
{
  "status": "CONFIRMADO",
  "competenciaFinanceira": "<UUID_COMPETENCIA>",
  "valorEstimadoTotal": 120.50
}
```

---

## 2) Atendimentos (disparam consumo/estorno)

**Base path:** `/v1/atendimentos`

### Endpoints principais

- `POST /v1/atendimentos`
- `PUT /v1/atendimentos/{id}/iniciar`
- `PUT /v1/atendimentos/{id}/triagem`
- `PUT /v1/atendimentos/{id}/classificacao-risco`
- `PUT /v1/atendimentos/{id}/encerrar`
- `GET /v1/atendimentos`
- `GET /v1/atendimentos/pacientes/{pacienteId}/atendimentos`
- `GET /v1/atendimentos/{id}`

### Gatilhos financeiros no atendimento

Na implementação atual:

- ao **encerrar** o atendimento (status vira `CONCLUIDO`) → o backend tenta **consumir a reserva**
- se o atendimento virar `CANCELADO` ou `FALTA_PACIENTE` → o backend tenta **estornar** (via agendamento vinculado)

> Importante: o consumo/estorno busca o agendamento ligado ao atendimento (modelo híbrido).

