# Financeiro — Endpoints de Lançamentos (contábil)

Os lançamentos são a camada “formal” auditável (débito/crédito) que pode ser ligada a documentos, títulos e conciliações.

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` aceita `page`, `size`, `sort`

---

## 1) Lançamento Financeiro

**Base path:** `/v1/financeiro/lancamentos`

### Endpoints

- `POST /v1/financeiro/lancamentos`
- `GET /v1/financeiro/lancamentos`
- `GET /v1/financeiro/lancamentos/{id}`
- `PUT /v1/financeiro/lancamentos/{id}`
- `DELETE /v1/financeiro/lancamentos/{id}`
- `PUT /v1/financeiro/lancamentos/{id}/inativar`

### Exemplo de criação (LancamentoFinanceiroRequest)

```json
{
  "competencia": "<UUID_COMPETENCIA>",
  "documentoFaturamento": null,
  "tituloReceber": null,
  "tituloPagar": null,
  "conciliacao": null,
  "origemTipo": "AGENDAMENTO",
  "origemId": "<UUID_AGENDAMENTO>",
  "status": "PREVISTO",
  "dataEvento": "2026-01-10T10:00:00-03:00",
  "descricao": "Reserva/consumo referente ao agendamento X",
  "motivoEstorno": null,
  "referenciaEstornoTipo": null,
  "referenciaEstornoId": null,
  "prestadorId": "<UUID_ESTABELECIMENTO>",
  "prestadorTipo": "ESTABELECIMENTO",
  "grupoLancamento": null,
  "itens": [
    {
      "contaContabil": "<UUID_CONTA_CONTABIL>",
      "centroCusto": "<UUID_CENTRO_CUSTO>",
      "tipoPartida": "DEBITO",
      "valor": 120.50,
      "historico": "Débito do orçamento do município"
    },
    {
      "contaContabil": "<UUID_CONTA_CONTABIL_CONTRAPARTIDA>",
      "centroCusto": "<UUID_CENTRO_CUSTO>",
      "tipoPartida": "CREDITO",
      "valor": 120.50,
      "historico": "Crédito na contrapartida"
    }
  ]
}
```

Status (valor esperado no request):

- `PREVISTO` | `REALIZADO` | `ESTORNADO` | `AJUSTADO` | `CANCELADO_POR_REVERSAO`

---

## 2) Itens de Lançamento Financeiro

**Base path:** `/v1/financeiro/lancamentos-itens`

### Endpoints

- `POST /v1/financeiro/lancamentos-itens`
- `GET /v1/financeiro/lancamentos-itens`
- `GET /v1/financeiro/lancamentos-itens/{id}`
- `PUT /v1/financeiro/lancamentos-itens/{id}`
- `DELETE /v1/financeiro/lancamentos-itens/{id}`
- `PUT /v1/financeiro/lancamentos-itens/{id}/inativar`

### Exemplo de criação (LancamentoFinanceiroItemRequest)

```json
{
  "contaContabil": "<UUID_CONTA_CONTABIL>",
  "centroCusto": "<UUID_CENTRO_CUSTO>",
  "tipoPartida": "DEBITO",
  "valor": 120.50,
  "historico": "Débito do orçamento"
}
```

Observação:

- Tipicamente o front cria itens **dentro** do lançamento (no `itens` do `LancamentoFinanceiroRequest`).
- O endpoint de itens existe para operações mais granulares/administrativas.

