# Financeiro — Endpoints de Orçamento (Saldo por Competência)

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` aceita `page`, `size`, `sort`

---

## 1) Orçamento por Competência (por Tenant/Município)

**Base path:** `/v1/financeiro/orcamentos-competencia`

O que é:

- representa o orçamento/saldo do município para uma competência específica
- normalmente alimentado por créditos e impactado por reservas/consumos/estornos

### Endpoints

- `POST /v1/financeiro/orcamentos-competencia`
- `GET /v1/financeiro/orcamentos-competencia`
- `GET /v1/financeiro/orcamentos-competencia/{id}`
- `PUT /v1/financeiro/orcamentos-competencia/{id}`
- `DELETE /v1/financeiro/orcamentos-competencia/{id}`
- `PUT /v1/financeiro/orcamentos-competencia/{id}/inativar`

### Exemplo de criação (OrcamentoCompetenciaRequest)

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/orcamentos-competencia" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "competencia": "<UUID_COMPETENCIA>",
    "saldoAnterior": 0,
    "creditos": 0,
    "reservasAtivas": 0,
    "consumos": 0,
    "estornos": 0,
    "despesasAdmin": 0,
    "saldoFinal": 0
  }'
```

Observação:

- o response expõe também `saldoDisponivel` (campo calculado).

---

## 2) Crédito Orçamentário

**Base path:** `/v1/financeiro/creditos-orcamentarios`

O que é:

- entrada de recursos na competência do município
- usado para formar o saldo disponível (antes de reservar/consumir)

### Endpoints

- `POST /v1/financeiro/creditos-orcamentarios`
- `GET /v1/financeiro/creditos-orcamentarios`
- `GET /v1/financeiro/creditos-orcamentarios/{id}`
- `PUT /v1/financeiro/creditos-orcamentarios/{id}`
- `DELETE /v1/financeiro/creditos-orcamentarios/{id}`
- `PUT /v1/financeiro/creditos-orcamentarios/{id}/inativar`

### Exemplo de criação (CreditoOrcamentarioRequest)

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/creditos-orcamentarios" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "competencia": "<UUID_COMPETENCIA>",
    "valor": 10000,
    "fonte": "RECURSO_PUBLICO",
    "documentoReferencia": "Empenho 123/2026",
    "dataCredito": "2026-01-02"
  }'
```

Boas práticas de UI:

- sempre mostrar créditos por competência (histórico)
- quando o usuário confirmar um agendamento, exibir saldo disponível e risco de insuficiência

