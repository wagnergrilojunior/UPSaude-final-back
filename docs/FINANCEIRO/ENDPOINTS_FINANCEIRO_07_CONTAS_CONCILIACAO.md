# Financeiro — Endpoints de Contas, Movimentação e Conciliação Bancária

Este bloco cobre caixa/banco (conta financeira), movimentações, transferências, extratos importados e conciliações.

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` aceita `page`, `size`, `sort`

---

## 1) Conta Financeira (Caixa/Banco)

**Base path:** `/v1/financeiro/contas-financeiras`

### Endpoints

- `POST /v1/financeiro/contas-financeiras`
- `GET /v1/financeiro/contas-financeiras`
- `GET /v1/financeiro/contas-financeiras/{id}`
- `PUT /v1/financeiro/contas-financeiras/{id}`
- `DELETE /v1/financeiro/contas-financeiras/{id}`
- `PUT /v1/financeiro/contas-financeiras/{id}/inativar`

### Exemplo de criação (ContaFinanceiraRequest)

```json
{
  "tipo": "BANCO",
  "nome": "Banco do Brasil - 001",
  "moeda": "BRL",
  "bancoCodigo": "001",
  "agencia": "1234-5",
  "numeroConta": "99999-9",
  "pixChave": "financeiro@upsaude.com",
  "ativo": true
}
```

---

## 2) Movimentação de Conta

**Base path:** `/v1/financeiro/movimentacoes`

### Endpoints

- `POST /v1/financeiro/movimentacoes`
- `GET /v1/financeiro/movimentacoes`
- `GET /v1/financeiro/movimentacoes/{id}`
- `PUT /v1/financeiro/movimentacoes/{id}`
- `DELETE /v1/financeiro/movimentacoes/{id}`
- `PUT /v1/financeiro/movimentacoes/{id}/inativar`

### Exemplo de criação (MovimentacaoContaRequest)

```json
{
  "contaFinanceira": "<UUID_CONTA_FINANCEIRA>",
  "baixaReceber": "<UUID_BAIXA_RECEBER>",
  "pagamentoPagar": null,
  "transferencia": null,
  "lancamentoFinanceiro": "<UUID_LANCAMENTO>",
  "tipo": "ENTRADA",
  "valor": 120.50,
  "dataMovimento": "2026-01-15T10:00:00-03:00",
  "status": "EFETIVADO"
}
```

---

## 3) Transferência Entre Contas

**Base path:** `/v1/financeiro/transferencias`

### Endpoints

- `POST /v1/financeiro/transferencias`
- `GET /v1/financeiro/transferencias`
- `GET /v1/financeiro/transferencias/{id}`
- `PUT /v1/financeiro/transferencias/{id}`
- `DELETE /v1/financeiro/transferencias/{id}`
- `PUT /v1/financeiro/transferencias/{id}/inativar`

### Exemplo de criação (TransferenciaEntreContasRequest)

```json
{
  "contaOrigem": "<UUID_CONTA_ORIGEM>",
  "contaDestino": "<UUID_CONTA_DESTINO>",
  "valor": 500.00,
  "data": "2026-01-20T12:00:00-03:00",
  "status": "PENDENTE"
}
```

---

## 4) Extrato Bancário Importado

**Base path:** `/v1/financeiro/extratos-importados`

### Endpoints

- `POST /v1/financeiro/extratos-importados`
- `GET /v1/financeiro/extratos-importados`
- `GET /v1/financeiro/extratos-importados/{id}`
- `PUT /v1/financeiro/extratos-importados/{id}`
- `DELETE /v1/financeiro/extratos-importados/{id}`
- `PUT /v1/financeiro/extratos-importados/{id}/inativar`

### Exemplo de criação (ExtratoBancarioImportadoRequest)

```json
{
  "contaFinanceira": "<UUID_CONTA_FINANCEIRA>",
  "hashLinha": "hash_unico_da_linha",
  "descricao": "PIX RECEBIDO",
  "valor": 120.50,
  "data": "2026-01-15",
  "documento": "E2E123...",
  "saldoApos": 1500.00,
  "statusConciliacao": "NAO_CONCILIADO"
}
```

---

## 5) Conciliação Bancária

**Base path:** `/v1/financeiro/conciliacoes`

### Endpoints

- `POST /v1/financeiro/conciliacoes`
- `GET /v1/financeiro/conciliacoes`
- `GET /v1/financeiro/conciliacoes/{id}`
- `PUT /v1/financeiro/conciliacoes/{id}`
- `DELETE /v1/financeiro/conciliacoes/{id}`
- `PUT /v1/financeiro/conciliacoes/{id}/inativar`

### Exemplo de criação (ConciliacaoBancariaRequest)

```json
{
  "contaFinanceira": "<UUID_CONTA_FINANCEIRA>",
  "periodoInicio": "2026-01-01",
  "periodoFim": "2026-01-31",
  "status": "ABERTA"
}
```

---

## 6) Itens de Conciliação

**Base path:** `/v1/financeiro/conciliacoes-itens`

### Endpoints

- `POST /v1/financeiro/conciliacoes-itens`
- `GET /v1/financeiro/conciliacoes-itens`
- `GET /v1/financeiro/conciliacoes-itens/{id}`
- `PUT /v1/financeiro/conciliacoes-itens/{id}`
- `DELETE /v1/financeiro/conciliacoes-itens/{id}`
- `PUT /v1/financeiro/conciliacoes-itens/{id}/inativar`

### Exemplo de criação (ConciliacaoItemRequest)

```json
{
  "conciliacao": "<UUID_CONCILIACAO>",
  "extratoImportado": "<UUID_EXTRATO>",
  "movimentacaoConta": "<UUID_MOVIMENTACAO>",
  "tipoMatch": "MANUAL",
  "diferenca": 0
}
```

