# Financeiro — Endpoints Assistenciais (Reservas / Estornos / Guias)

Este conjunto de endpoints representa os registros financeiros vinculados ao fluxo assistencial (agendamento/atendimento).

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` aceita `page`, `size`, `sort`

---

## 1) Reserva Orçamentária Assistencial

**Base path:** `/v1/financeiro/reservas-orcamentarias`

### Endpoints

- `POST /v1/financeiro/reservas-orcamentarias`
- `GET /v1/financeiro/reservas-orcamentarias`
- `GET /v1/financeiro/reservas-orcamentarias/{id}`
- `PUT /v1/financeiro/reservas-orcamentarias/{id}`
- `DELETE /v1/financeiro/reservas-orcamentarias/{id}`
- `PUT /v1/financeiro/reservas-orcamentarias/{id}/inativar`

### Exemplo de criação (ReservaOrcamentariaAssistencialRequest)

```json
{
  "competencia": "<UUID_COMPETENCIA>",
  "agendamento": "<UUID_AGENDAMENTO>",
  "guiaAmbulatorial": null,
  "documentoFaturamento": null,
  "prestadorId": "<UUID_PRESTADOR>",
  "prestadorTipo": "ESTABELECIMENTO",
  "valorReservadoTotal": 120.50,
  "status": "ATIVA",
  "grupoReserva": null
}
```

Status esperado:

- `ATIVA` → reserva vigente
- `CONSUMIDA` → consolidada por atendimento
- `LIBERADA` → estornada/liberada (não apagada, para auditoria)

---

## 2) Estorno Financeiro

**Base path:** `/v1/financeiro/estornos`

### Endpoints

- `POST /v1/financeiro/estornos`
- `GET /v1/financeiro/estornos`
- `GET /v1/financeiro/estornos/{id}`
- `PUT /v1/financeiro/estornos/{id}`
- `DELETE /v1/financeiro/estornos/{id}`
- `PUT /v1/financeiro/estornos/{id}/inativar`

### Exemplo de criação (EstornoFinanceiroRequest)

```json
{
  "competencia": "<UUID_COMPETENCIA>",
  "agendamento": "<UUID_AGENDAMENTO>",
  "atendimento": null,
  "guiaAmbulatorial": null,
  "paciente": "<UUID_PACIENTE>",
  "atendimentoProcedimento": null,
  "motivo": "CANCELAMENTO",
  "valorEstornado": 120.50,
  "procedimentoCodigo": null,
  "procedimentoNome": null,
  "dataEstorno": "2026-01-10T10:30:00-03:00",
  "observacoes": "Estorno automático"
}
```

---

## 3) Guia de Atendimento Ambulatorial (GAA)

**Base path:** `/v1/financeiro/guias-ambulatoriais`

### Endpoints

- `POST /v1/financeiro/guias-ambulatoriais`
- `GET /v1/financeiro/guias-ambulatoriais`
- `GET /v1/financeiro/guias-ambulatoriais/{id}`
- `PUT /v1/financeiro/guias-ambulatoriais/{id}`
- `DELETE /v1/financeiro/guias-ambulatoriais/{id}`
- `PUT /v1/financeiro/guias-ambulatoriais/{id}/inativar`

### Exemplo de criação (GuiaAtendimentoAmbulatorialRequest)

```json
{
  "competencia": "<UUID_COMPETENCIA>",
  "agendamento": "<UUID_AGENDAMENTO>",
  "atendimento": "<UUID_ATENDIMENTO>",
  "paciente": "<UUID_PACIENTE>",
  "estabelecimento": "<UUID_ESTABELECIMENTO>",
  "documentoFaturamento": null,
  "numero": "GAA-2026-000001",
  "status": "EMITIDA",
  "observacoes": "Emitida automaticamente"
}
```

Observação importante:

- GAA é o ponto “assistencial → faturamento/produção”. O módulo já tem o cadastro e vínculo com documento de faturamento, mas a geração completa de BPA é evolução.

