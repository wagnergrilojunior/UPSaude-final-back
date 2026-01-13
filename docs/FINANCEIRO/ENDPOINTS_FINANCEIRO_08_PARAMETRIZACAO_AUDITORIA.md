# Financeiro — Endpoints de Parametrização Contábil e Auditoria

Este bloco cobre plano de contas, contas contábeis, centro de custo, regras de classificação e logs.

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` aceita `page`, `size`, `sort`

---

## 1) Plano de Contas

**Base path:** `/v1/financeiro/planos-contas`

### Endpoints

- `POST /v1/financeiro/planos-contas`
- `GET /v1/financeiro/planos-contas`
- `GET /v1/financeiro/planos-contas/{id}`
- `PUT /v1/financeiro/planos-contas/{id}`
- `DELETE /v1/financeiro/planos-contas/{id}`
- `PUT /v1/financeiro/planos-contas/{id}/inativar`

### Exemplo de criação (PlanoContasRequest)

```json
{
  "nome": "Plano Padrão",
  "versao": "1.0",
  "padrao": true,
  "ativo": true
}
```

---

## 2) Conta Contábil

**Base path:** `/v1/financeiro/contas-contabeis`

### Endpoints

- `POST /v1/financeiro/contas-contabeis`
- `GET /v1/financeiro/contas-contabeis`
- `GET /v1/financeiro/contas-contabeis/{id}`
- `PUT /v1/financeiro/contas-contabeis/{id}`
- `DELETE /v1/financeiro/contas-contabeis/{id}`
- `PUT /v1/financeiro/contas-contabeis/{id}/inativar`

### Exemplo de criação (ContaContabilRequest)

```json
{
  "planoContas": "<UUID_PLANO>",
  "contaPai": null,
  "codigo": "3.01.01",
  "nome": "Receita Ambulatorial",
  "natureza": "RECEITA",
  "aceitaLancamento": true,
  "nivel": 3,
  "ordem": 10
}
```

---

## 3) Centro de Custo

**Base path:** `/v1/financeiro/centros-custo`

### Endpoints

- `POST /v1/financeiro/centros-custo`
- `GET /v1/financeiro/centros-custo`
- `GET /v1/financeiro/centros-custo/{id}`
- `PUT /v1/financeiro/centros-custo/{id}`
- `DELETE /v1/financeiro/centros-custo/{id}`
- `PUT /v1/financeiro/centros-custo/{id}/inativar`

### Exemplo de criação (CentroCustoRequest)

```json
{
  "pai": null,
  "codigo": "ADM",
  "nome": "Administrativo",
  "ativo": true,
  "ordem": 1
}
```

---

## 4) Regras de Classificação Contábil

**Base path:** `/v1/financeiro/regras-classificacao`

### Endpoints

- `POST /v1/financeiro/regras-classificacao`
- `GET /v1/financeiro/regras-classificacao`
- `GET /v1/financeiro/regras-classificacao/{id}`
- `PUT /v1/financeiro/regras-classificacao/{id}`
- `DELETE /v1/financeiro/regras-classificacao/{id}`
- `PUT /v1/financeiro/regras-classificacao/{id}/inativar`

### Exemplo de criação (RegraClassificacaoContabilRequest)

```json
{
  "contaContabilDestino": "<UUID_CONTA_CONTABIL>",
  "escopo": "ASSISTENCIAL",
  "prioridade": 1,
  "ativo": true,
  "condicaoJsonb": "{\"procedimentoCodigo\":\"0301010010\"}"
}
```

Observação:

- A classificação automática completa (aplicação das regras em lançamentos) é uma evolução natural; hoje o CRUD permite parametrizar e manter histórico.

---

## 5) Log Financeiro (auditoria)

**Base path:** `/v1/financeiro/logs`

### Endpoints

- `POST /v1/financeiro/logs`
- `GET /v1/financeiro/logs`
- `GET /v1/financeiro/logs/{id}`
- `PUT /v1/financeiro/logs/{id}`
- `DELETE /v1/financeiro/logs/{id}`
- `PUT /v1/financeiro/logs/{id}/inativar`

### Exemplo de criação (LogFinanceiroRequest)

```json
{
  "entidadeTipo": "RESERVA_ORCAMENTARIA",
  "entidadeId": "<UUID_RESERVA>",
  "acao": "CRIAR",
  "usuarioId": "<UUID_USUARIO>",
  "correlationId": "req-123",
  "payloadAntes": null,
  "payloadDepois": "{\"status\":\"ATIVA\"}",
  "ip": "10.0.0.1",
  "userAgent": "Mozilla/5.0"
}
```

