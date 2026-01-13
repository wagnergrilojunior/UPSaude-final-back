# Financeiro — Endpoints Core (Competências)

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` aceita `page`, `size`, `sort`
- **CRUD padrão**: `POST`, `GET`, `GET/{id}`, `PUT/{id}`, `DELETE/{id}`, `PUT/{id}/inativar`

---

## 1) Competência Financeira (global)

**Base path:** `/v1/financeiro/competencias`

### Endpoints

- `POST /v1/financeiro/competencias`
- `GET /v1/financeiro/competencias`
- `GET /v1/financeiro/competencias/{id}`
- `PUT /v1/financeiro/competencias/{id}`
- `DELETE /v1/financeiro/competencias/{id}`
- `PUT /v1/financeiro/competencias/{id}/inativar`

### Exemplo de criação (CompetenciaFinanceiraRequest)

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/competencias" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "2026-01",
    "tipo": "MENSAL",
    "dataInicio": "2026-01-01",
    "dataFim": "2026-01-31",
    "descricao": "Competência janeiro/2026"
  }'
```

Campos relevantes:

- `codigo`: string (ex.: `2026-01` ou `202601` — defina um padrão e mantenha)
- `tipo`: `MENSAL` | `CUSTOM`
- `dataInicio` / `dataFim`

---

## 2) Competência Financeira por Tenant (Município)

**Base path:** `/v1/financeiro/competencias-tenant`

Objetivo:

- controlar a competência **para o município logado** (ABERTA/FECHADA, motivo etc.)

### Endpoints

- `POST /v1/financeiro/competencias-tenant`
- `GET /v1/financeiro/competencias-tenant`
- `GET /v1/financeiro/competencias-tenant/{id}`
- `PUT /v1/financeiro/competencias-tenant/{id}`
- `DELETE /v1/financeiro/competencias-tenant/{id}`
- `PUT /v1/financeiro/competencias-tenant/{id}/inativar`

### Exemplo de criação (CompetenciaFinanceiraTenantRequest)

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/competencias-tenant" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "competencia": "<UUID_COMPETENCIA>",
    "status": "ABERTA",
    "motivoFechamento": null
  }'
```

Observações para o Front:

- O **tenant é inferido** do usuário autenticado; você não envia `tenantId` no payload.
- Use este recurso quando precisar de UI para “competência do município”: abrir/fechar e auditar.

