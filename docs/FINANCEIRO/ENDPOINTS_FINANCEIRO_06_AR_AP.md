# Financeiro — Endpoints de Contas a Receber / Contas a Pagar (AR/AP)

Este bloco cobre “financeiro clássico”: pagadores/fornecedores, títulos, baixas/pagamentos, renegociação e recorrência.

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` aceita `page`, `size`, `sort`

---

## 1) Parte Financeira (Pagador / Fornecedor)

**Base path:** `/v1/financeiro/partes-financeiras`

### Endpoints

- `POST /v1/financeiro/partes-financeiras`
- `GET /v1/financeiro/partes-financeiras`
- `GET /v1/financeiro/partes-financeiras/{id}`
- `PUT /v1/financeiro/partes-financeiras/{id}`
- `DELETE /v1/financeiro/partes-financeiras/{id}`
- `PUT /v1/financeiro/partes-financeiras/{id}/inativar`

### Exemplo de criação (ParteFinanceiraRequest)

```json
{
  "tipo": "CONVENIO",
  "nome": "Convênio Exemplo",
  "documento": "12345678000199",
  "email": "financeiro@convenio.com",
  "telefone": "+55 11 99999-9999",
  "referenciaTipo": "CONVENIO",
  "referenciaId": "<UUID_CONVENIO>"
}
```

---

## 2) Título a Receber

**Base path:** `/v1/financeiro/titulos-receber`

### Endpoints

- `POST /v1/financeiro/titulos-receber`
- `GET /v1/financeiro/titulos-receber`
- `GET /v1/financeiro/titulos-receber/{id}`
- `PUT /v1/financeiro/titulos-receber/{id}`
- `DELETE /v1/financeiro/titulos-receber/{id}`
- `PUT /v1/financeiro/titulos-receber/{id}/inativar`

### Exemplo de criação (TituloReceberRequest)

```json
{
  "documentoFaturamento": "<UUID_DOCUMENTO_FATURAMENTO>",
  "pagador": "<UUID_PARTE_FINANCEIRA>",
  "contaContabilReceita": "<UUID_CONTA_CONTABIL>",
  "centroCusto": "<UUID_CENTRO_CUSTO>",
  "numero": "REC-2026-00001",
  "parcela": 1,
  "totalParcelas": 1,
  "valorOriginal": 120.50,
  "desconto": 0,
  "juros": 0,
  "multa": 0,
  "valorAberto": 120.50,
  "dataEmissao": "2026-01-10",
  "dataVencimento": "2026-02-10",
  "status": "ABERTO"
}
```

---

## 3) Baixa de Título a Receber

**Base path:** `/v1/financeiro/baixas-receber`

### Endpoints

- `POST /v1/financeiro/baixas-receber`
- `GET /v1/financeiro/baixas-receber`
- `GET /v1/financeiro/baixas-receber/{id}`
- `PUT /v1/financeiro/baixas-receber/{id}`
- `DELETE /v1/financeiro/baixas-receber/{id}`
- `PUT /v1/financeiro/baixas-receber/{id}/inativar`

### Exemplo de criação (BaixaReceberRequest)

```json
{
  "tituloReceber": "<UUID_TITULO_RECEBER>",
  "contaFinanceira": "<UUID_CONTA_FINANCEIRA>",
  "movimentacaoConta": "<UUID_MOVIMENTACAO_CONTA>",
  "lancamentoFinanceiro": "<UUID_LANCAMENTO_FINANCEIRO>",
  "valorPago": 120.50,
  "dataPagamento": "2026-01-15",
  "meioPagamento": "PIX",
  "status": "EFETIVADO",
  "observacao": "Pago via PIX"
}
```

---

## 4) Renegociação de Títulos a Receber

**Base path:** `/v1/financeiro/renegociacoes-receber`

### Endpoints

- `POST /v1/financeiro/renegociacoes-receber`
- `GET /v1/financeiro/renegociacoes-receber`
- `GET /v1/financeiro/renegociacoes-receber/{id}`
- `PUT /v1/financeiro/renegociacoes-receber/{id}`
- `DELETE /v1/financeiro/renegociacoes-receber/{id}`
- `PUT /v1/financeiro/renegociacoes-receber/{id}/inativar`

### Exemplo de criação (RenegociacaoReceberRequest)

```json
{
  "data": "2026-01-20",
  "motivo": "Acordo com o pagador",
  "observacao": "Renegociação em 2 parcelas",
  "titulosOriginaisIds": ["<UUID_TITULO_1>", "<UUID_TITULO_2>"]
}
```

---

## 5) Recorrência Financeira

**Base path:** `/v1/financeiro/recorrencias`

### Endpoints

- `POST /v1/financeiro/recorrencias`
- `GET /v1/financeiro/recorrencias`
- `GET /v1/financeiro/recorrencias/{id}`
- `PUT /v1/financeiro/recorrencias/{id}`
- `DELETE /v1/financeiro/recorrencias/{id}`
- `PUT /v1/financeiro/recorrencias/{id}/inativar`

### Exemplo de criação (RecorrenciaFinanceiraRequest)

```json
{
  "tipo": "PAGAR",
  "periodicidade": "MENSAL",
  "diaMes": 10,
  "diaSemana": null,
  "proximaGeracaoEm": "2026-02-10T00:00:00-03:00",
  "ativo": true
}
```

---

## 6) Título a Pagar

**Base path:** `/v1/financeiro/titulos-pagar`

### Endpoints

- `POST /v1/financeiro/titulos-pagar`
- `GET /v1/financeiro/titulos-pagar`
- `GET /v1/financeiro/titulos-pagar/{id}`
- `PUT /v1/financeiro/titulos-pagar/{id}`
- `DELETE /v1/financeiro/titulos-pagar/{id}`
- `PUT /v1/financeiro/titulos-pagar/{id}/inativar`

### Exemplo de criação (TituloPagarRequest)

```json
{
  "fornecedor": "<UUID_PARTE_FINANCEIRA>",
  "contaContabilDespesa": "<UUID_CONTA_CONTABIL>",
  "centroCusto": "<UUID_CENTRO_CUSTO>",
  "recorrenciaFinanceira": "<UUID_RECORRENCIA>",
  "numeroDocumento": "NF-123",
  "valorOriginal": 500.00,
  "valorAberto": 500.00,
  "dataEmissao": "2026-01-05",
  "dataVencimento": "2026-02-05",
  "status": "ABERTO"
}
```

---

## 7) Pagamento de Título a Pagar

**Base path:** `/v1/financeiro/pagamentos-pagar`

### Endpoints

- `POST /v1/financeiro/pagamentos-pagar`
- `GET /v1/financeiro/pagamentos-pagar`
- `GET /v1/financeiro/pagamentos-pagar/{id}`
- `PUT /v1/financeiro/pagamentos-pagar/{id}`
- `DELETE /v1/financeiro/pagamentos-pagar/{id}`
- `PUT /v1/financeiro/pagamentos-pagar/{id}/inativar`

### Exemplo de criação (PagamentoPagarRequest)

```json
{
  "tituloPagar": "<UUID_TITULO_PAGAR>",
  "contaFinanceira": "<UUID_CONTA_FINANCEIRA>",
  "movimentacaoConta": "<UUID_MOVIMENTACAO_CONTA>",
  "lancamentoFinanceiro": "<UUID_LANCAMENTO_FINANCEIRO>",
  "valorPago": 500.00,
  "dataPagamento": "2026-02-01",
  "meioPagamento": "TED",
  "status": "EFETIVADO",
  "observacao": "Pagamento via TED"
}
```

