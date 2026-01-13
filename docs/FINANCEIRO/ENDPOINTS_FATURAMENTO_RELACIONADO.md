# Faturamento — Endpoints Relacionados ao Financeiro

Mesmo sendo um módulo separado, o faturamento se integra diretamente ao financeiro (documentos podem originar títulos, lançamentos e fechamento).

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` aceita `page`, `size`, `sort`

---

## 1) Documento de Faturamento

**Base path:** `/v1/faturamento/documentos`

### Endpoints

- `POST /v1/faturamento/documentos`
- `GET /v1/faturamento/documentos`
- `GET /v1/faturamento/documentos/{id}`
- `PUT /v1/faturamento/documentos/{id}`
- `DELETE /v1/faturamento/documentos/{id}`
- `PUT /v1/faturamento/documentos/{id}/inativar`

### Exemplo de criação (DocumentoFaturamentoRequest)

```json
{
  "competencia": "<UUID_COMPETENCIA>",
  "convenio": "<UUID_CONVENIO>",
  "agendamento": "<UUID_AGENDAMENTO>",
  "atendimento": "<UUID_ATENDIMENTO>",
  "guiaAmbulatorial": "<UUID_GUIA>",
  "tipo": "BPA",
  "numero": "BPA-2026-00001",
  "serie": "A",
  "status": "ABERTO",
  "pagadorTipo": "SUS",
  "payloadLayout": "{\"layout\":\"BPA\"}",
  "itens": [
    {
      "sigtapProcedimento": "<UUID_SIGTAP_PROC>",
      "quantidade": 1,
      "valorUnitario": 120.50,
      "valorTotal": 120.50,
      "origemTipo": "ATENDIMENTO",
      "origemId": "<UUID_ATENDIMENTO>",
      "payloadLayoutItem": "{\"linha\":1}"
    }
  ]
}
```

---

## 2) Itens de Documento de Faturamento

**Base path:** `/v1/faturamento/documentos-itens`

### Endpoints

- `POST /v1/faturamento/documentos-itens`
- `GET /v1/faturamento/documentos-itens`
- `GET /v1/faturamento/documentos-itens/{id}`
- `PUT /v1/faturamento/documentos-itens/{id}`
- `DELETE /v1/faturamento/documentos-itens/{id}`
- `PUT /v1/faturamento/documentos-itens/{id}/inativar`

Observação:

- Normalmente itens são criados dentro do `DocumentoFaturamentoRequest.itens`.

---

## 3) Glosas

**Base path:** `/v1/faturamento/glosas`

### Endpoints

- `POST /v1/faturamento/glosas`
- `GET /v1/faturamento/glosas`
- `GET /v1/faturamento/glosas/{id}`
- `PUT /v1/faturamento/glosas/{id}`
- `DELETE /v1/faturamento/glosas/{id}`
- `PUT /v1/faturamento/glosas/{id}/inativar`

### Exemplo de criação (GlosaRequest)

```json
{
  "documento": "<UUID_DOCUMENTO>",
  "item": "<UUID_ITEM>",
  "tipo": "PARCIAL",
  "valorGlosado": 20.00,
  "motivoCodigo": "G001",
  "motivoDescricao": "Procedimento não autorizado",
  "status": "ABERTA"
}
```

