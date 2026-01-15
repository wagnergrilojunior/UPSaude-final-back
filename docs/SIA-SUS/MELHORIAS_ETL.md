# SIA-SUS (PA) — Melhorias de ETL (Fases 1 e 2)

Este documento descreve as melhorias implementadas para transformar os dados brutos do **SIA-PA** em **informações analíticas** (KPIs, relatórios, tendências, anomalias e conciliação com faturamento).

## Objetivos (por que isso agrega valor)

- **Enriquecer** o dado bruto (procedimento/CID/estrutura) para sair do “código puro” e virar informação legível.
- **Acelerar consultas** com agregações pré-calculadas (views materializadas).
- **Expor KPIs e relatórios** prontos para gestão (produção, rankings, tendências).
- **Detectar anomalias** automaticamente (qualidade, inconsistência e outliers).
- **Conectar produção SUS (SIA)** com **faturamento interno** para conciliação (SIA vs DocumentoFaturamento).

## O que foi criado no banco (PostgreSQL)

### 1) View de enriquecimento

- `public.sia_pa_enriquecido_view`
  - Origem: `public.sia_pa`
  - Enriquecimento:
    - **SIGTAP** (nome e hierarquia do procedimento)
    - **CID10** (descrição do CID principal)
    - **Estabelecimento** (descritivo via CNES — seleção “mais recente” para evitar duplicidade)

Observação: `estabelecimentos` é multi-tenant (`cnes + tenant_id`). Para evitar duplicidade na view, o enriquecimento de estabelecimento é **descritivo** e pode ser sobrescrito pela camada de serviço com base no tenant autenticado.

### 2) Views materializadas (agregações)

- `public.sia_pa_agregado_estabelecimento`
  - Agrupamento: `competencia + uf + codigo_cnes`
  - Entrega: totais (qtd/valores) e taxa de aprovação por valor.

- `public.sia_pa_agregado_procedimento`
  - Agrupamento: `competencia + uf + procedimento_codigo`
  - Entrega: totais e dispersão por estabelecimentos/municípios.

- `public.sia_pa_agregado_cid`
  - Agrupamento: `competencia + uf + cid_principal_codigo`
  - Entrega: totais e “top” procedimento/município (para explicar concentração).

- `public.sia_pa_agregado_temporal`
  - Agrupamento: `uf + competencia`
  - Entrega: série temporal + `LAG()` para delta e crescimento percentual.

### 3) Tabela de anomalias

- `public.sia_pa_anomalia`
  - Guarda achados de qualidade/consistência e outliers
  - Índice único: `(competencia, uf, tipo_anomalia, chave)` para evitar duplicidade em reprocessamentos.

## Jobs agendados (automatização)

### Refresh de views materializadas

- Serviço: `SiaPaAggregationRefreshServiceImpl`
- Configuração:
  - `sia.aggregation.refresh.enabled=true`
  - `sia.aggregation.refresh.cron=0 0 2 * * ?` (diário às 02:00)
- Execução: `REFRESH MATERIALIZED VIEW` (sem `CONCURRENTLY`, por simplicidade/compatibilidade).

### Detecção de anomalias

- Job: `SiaPaAnomaliaDetectionJob`
- Configuração:
  - `sia.anomaly.detection.enabled=true`
  - `sia.anomaly.detection.cron=0 0 3 * * ?` (diário às 03:00)
  - `sia.anomaly.detection.threshold.production-variance=2.0`
  - `sia.anomaly.detection.threshold.value-difference=0.1`
- Estratégia: processa a **última competência disponível por UF** (consulta `MAX(competencia)`).

## Regras de negócio cobertas (Fase 2 — anomalias)

Atualmente, a detecção automática cobre:

- **VALOR_DIVERGENTE_SIGTAP**: valor aprovado médio do procedimento diverge do valor SIGTAP (ambulatorial) acima do limiar.
- **QTD_APROVADA_MAIOR_QTD_PRODUZIDA**: inconsistência de quantidade.
- **OUTLIER_ESTABELECIMENTO_VALOR_APROVADO**: estabelecimento com valor aprovado muito acima do padrão (média + N*desvio).

## Observabilidade / Operação

- KPIs, relatórios e dashboard consomem **agregações** (performance).
- Anomalias ficam persistidas e podem ser consultadas via API.
- Se a base SIA ainda não tiver dados para uma competência, endpoints retornam 0/empty conforme o caso.

## Endpoints entregues (API)

Referência completa: `docs/SIA-SUS/API_RELATORIOS.md`

### KPIs

- `GET /api/v1/sia/kpi/geral?competencia=AAAAMM&uf=UF`
- `GET /api/v1/sia/kpi/estabelecimento/{cnes}?competencia=AAAAMM&uf=UF`
- `GET /api/v1/sia/kpi/procedimento/{codigo}?competencia=AAAAMM&uf=UF`

### Relatórios

- `GET /api/v1/sia/relatorios/producao-mensal?uf=UF&competenciaInicio=AAAAMM&competenciaFim=AAAAMM`
- `GET /api/v1/sia/relatorios/top-procedimentos?uf=UF&competencia=AAAAMM&limit=10`
- `GET /api/v1/sia/relatorios/top-cid?uf=UF&competencia=AAAAMM&limit=10`

### Analytics

- `GET /api/v1/sia/analytics/tendencia?uf=UF&competenciaInicio=AAAAMM&competenciaFim=AAAAMM`
- `GET /api/v1/sia/analytics/comparacao?uf=UF&competenciaBase=AAAAMM&competenciaComparacao=AAAAMM`
- `GET /api/v1/sia/analytics/sazonalidade?uf=UF&competenciaInicio=AAAAMM&competenciaFim=AAAAMM`
- `GET /api/v1/sia/analytics/ranking?uf=UF&competencia=AAAAMM&limit=20`

### Anomalias

- `GET /api/v1/sia/anomalias?competencia=AAAAMM&uf=UF&page=0&size=20`
- `GET /api/v1/sia/anomalias/{id}`
- `POST /api/v1/sia/anomalias/detectar?competencia=AAAAMM&uf=UF`

### SIA x Financeiro/Faturamento (conciliação por tenant)

- `GET /api/v1/sia/financeiro/conciliacao?competencia=AAAAMM&uf=UF&limitNaoFaturados=50`
- `GET /api/v1/sia/financeiro/receita-por-competencia?uf=UF&competenciaInicio=AAAAMM&competenciaFim=AAAAMM`
- `GET /api/v1/sia/financeiro/procedimentos-nao-faturados?competencia=AAAAMM&uf=UF&limit=50`

### Dashboard (consolidado)

- `GET /api/v1/sia/dashboard?uf=UF&competencia=AAAAMM&competenciaInicio=AAAAMM&competenciaFim=AAAAMM&incluirFinanceiro=false`

## Arquivos/implementação (código)

- Migrations:
  - `src/main/resources/db/migration/V20260114000000__create_sia_pa_aggregations.sql`
  - `src/main/resources/db/migration/V20260114000001__create_sia_pa_anomalias.sql`
- Serviços/Controllers:
  - `com.upsaude.service.impl.api.sia.*`
  - `com.upsaude.controller.api.sia.*`

