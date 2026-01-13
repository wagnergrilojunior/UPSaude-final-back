# SIA-SUS (PA) — API de Relatórios, KPIs, Analytics e Anomalias

Base URL (por padrão):

- `/api` (context-path)
- Endpoints abaixo já estão no padrão `/api/v1/...`

Autenticação:

- Mesma autenticação do restante do sistema.
- Tenant é resolvido via usuário autenticado (quando necessário).

## 0) Importação (SIA-PA)

> Esses endpoints já existiam no módulo de importação, mas são parte do fluxo operacional do SIA-PA.

### 0.1 Upload assíncrono (cria job)

- `POST /api/v1/sia/import/upload` (multipart/form-data)
  - `file`: CSV do SIA-PA
  - `competenciaAno`: `YYYY`
  - `competenciaMes`: `MM`
  - `uf`: `MG`, `SP`, etc

Exemplo (cURL):

```bash
curl -X POST 'http://localhost:8080/api/v1/sia/import/upload' \
  -H 'Authorization: Bearer <token>' \
  -F 'file=@PAMG202501.csv' \
  -F 'competenciaAno=2025' \
  -F 'competenciaMes=01' \
  -F 'uf=MG'
```

Resposta (202):

```json
{
  "jobId": "uuid",
  "status": "ENFILEIRADO",
  "mensagem": "Upload concluído. Processamento será executado em background.",
  "statusUrl": "/api/v1/import-jobs/<jobId>/status",
  "errorsUrl": "/api/v1/import-jobs/<jobId>/errors"
}
```

## 0.2 Acompanhar job (polling)

- `GET /api/v1/import-jobs/{jobId}/status`
- `GET /api/v1/import-jobs/{jobId}/errors?page=0&size=50`

## 1) KPIs

### 1.1 KPIs gerais

- `GET /api/v1/sia/kpi/geral?competencia=AAAAMM&uf=UF`

Parâmetros:
- `competencia` (obrigatório)
- `uf` (opcional): se omitido, tenta inferir pelo tenant (endereço/estado)

Exemplo:

```bash
curl 'http://localhost:8080/api/v1/sia/kpi/geral?competencia=202501&uf=MG' \
  -H 'Authorization: Bearer <token>'
```

### 1.2 KPIs por estabelecimento (CNES)

- `GET /api/v1/sia/kpi/estabelecimento/{cnes}?competencia=AAAAMM&uf=UF`

### 1.3 KPIs por procedimento

- `GET /api/v1/sia/kpi/procedimento/{codigo}?competencia=AAAAMM&uf=UF`

## 2) Relatórios

### 2.1 Produção mensal (tendência)

- `GET /api/v1/sia/relatorios/producao-mensal?uf=UF&competenciaInicio=AAAAMM&competenciaFim=AAAAMM`

Exemplo:

```bash
curl 'http://localhost:8080/api/v1/sia/relatorios/producao-mensal?uf=MG&competenciaInicio=202401&competenciaFim=202501' \
  -H 'Authorization: Bearer <token>'
```

### 2.2 Top procedimentos

- `GET /api/v1/sia/relatorios/top-procedimentos?uf=UF&competencia=AAAAMM&limit=10`

### 2.3 Top CID

- `GET /api/v1/sia/relatorios/top-cid?uf=UF&competencia=AAAAMM&limit=10`

## 3) Analytics

### 3.1 Tendência (série temporal)

- `GET /api/v1/sia/analytics/tendencia?uf=UF&competenciaInicio=AAAAMM&competenciaFim=AAAAMM`

### 3.2 Comparação entre competências

- `GET /api/v1/sia/analytics/comparacao?uf=UF&competenciaBase=AAAAMM&competenciaComparacao=AAAAMM`

### 3.3 Sazonalidade (média por mês)

- `GET /api/v1/sia/analytics/sazonalidade?uf=UF&competenciaInicio=AAAAMM&competenciaFim=AAAAMM`

### 3.4 Ranking de estabelecimentos

- `GET /api/v1/sia/analytics/ranking?uf=UF&competencia=AAAAMM&limit=20`

## 4) Anomalias

### 4.1 Listar anomalias

- `GET /api/v1/sia/anomalias?competencia=AAAAMM&uf=UF&page=0&size=20`

Exemplo:

```bash
curl 'http://localhost:8080/api/v1/sia/anomalias?competencia=202501&uf=MG&page=0&size=20' \
  -H 'Authorization: Bearer <token>'
```

### 4.2 Detalhar anomalia

- `GET /api/v1/sia/anomalias/{id}`

### 4.3 Disparar detecção manual

- `POST /api/v1/sia/anomalias/detectar?competencia=AAAAMM&uf=UF`

## 5) Integração com Financeiro/Faturamento

### 5.1 Conciliação por competência (tenant)

- `GET /api/v1/sia/financeiro/conciliacao?competencia=AAAAMM&uf=UF&limitNaoFaturados=50`

O que faz:
- Agrupa a produção SIA (por procedimento) apenas nos CNES do tenant.
- Agrupa o faturamento interno (`documento_faturamento_item`) por procedimento (SIGTAP).
- Retorna divergências e lista de “procedimentos não faturados”.

### 5.2 Receita por competência (série temporal)

- `GET /api/v1/sia/financeiro/receita-por-competencia?uf=UF&competenciaInicio=AAAAMM&competenciaFim=AAAAMM`

### 5.3 Procedimentos não faturados

- `GET /api/v1/sia/financeiro/procedimentos-nao-faturados?competencia=AAAAMM&uf=UF&limit=50`

## 6) Dashboard (endpoint consolidado)

- `GET /api/v1/sia/dashboard?uf=UF&competencia=AAAAMM&competenciaInicio=AAAAMM&competenciaFim=AAAAMM&incluirFinanceiro=false`

Retorna:
- KPIs gerais
- Top procedimentos / Top CID
- Tendência (série)
- Anomalias recentes
- (Opcional) conciliação com faturamento

Exemplo:

```bash
curl 'http://localhost:8080/api/v1/sia/dashboard?uf=MG&competencia=202501&competenciaInicio=202401&competenciaFim=202501&incluirFinanceiro=true' \
  -H 'Authorization: Bearer <token>'
```
