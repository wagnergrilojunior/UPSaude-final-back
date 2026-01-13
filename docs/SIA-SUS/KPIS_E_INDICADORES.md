# SIA-SUS (PA) — KPIs e Indicadores (implementados)

Este documento descreve os KPIs expostos em `/api/v1/sia/kpi/*` e as métricas usadas em relatórios/analytics.

## Endpoints de KPIs

- `GET /api/v1/sia/kpi/geral?competencia=AAAAMM&uf=UF`
- `GET /api/v1/sia/kpi/estabelecimento/{cnes}?competencia=AAAAMM&uf=UF`
- `GET /api/v1/sia/kpi/procedimento/{codigo}?competencia=AAAAMM&uf=UF`

## Conceitos

- **Registro SIA-PA**: linha do arquivo PA (produção ambulatorial).
- **Quantidade produzida**: `quantidade_produzida`
- **Quantidade aprovada**: `quantidade_aprovada`
- **Valor produzido**: `valor_produzido`
- **Valor aprovado**: `valor_aprovado`

## KPIs principais (gerais)

### 1) Total de registros

- Definição: número de linhas do SIA-PA no recorte (competência/UF, e filtros opcionais).

### 2) Procedimentos únicos

- Definição: `COUNT(DISTINCT procedimento_codigo)`

### 3) Estabelecimentos únicos (CNES)

- Definição: `COUNT(DISTINCT codigo_cnes)`

### 4) Quantidade total produzida / aprovada

- Produzida: `SUM(quantidade_produzida)`
- Aprovada: `SUM(quantidade_aprovada)`

### 5) Valor total produzido / aprovado

- Produzido: `SUM(valor_produzido)`
- Aprovado: `SUM(valor_aprovado)`

### 6) Taxa de aprovação por valor

\[
taxaAprovacaoValor = \frac{\sum valorAprovado}{\sum valorProduzido}
\]

Observação: se `sum(valorProduzido)=0`, o KPI retorna `null`.

### 7) Registros com erro e taxa de erro

- Registros com erro: `SUM(CASE WHEN flag_erro IS NOT NULL AND flag_erro <> '0' THEN 1 ELSE 0 END)`
- Taxa de erro:

\[
taxaErroRegistros = \frac{registrosComErro}{totalRegistros}
\]

### 8) Diferença total de valor

\[
diferencaValorTotal = \sum valorProduzido - \sum valorAprovado
\]

## Produção per capita (quando possível)

O KPI tenta calcular produção per capita usando o município do **tenant**:

- População: `tenant.endereco.cidade.populacaoEstimada`
- Município (IBGE): `tenant.endereco.codigoIbgeMunicipio`
- Conversão: como o SIA frequentemente usa 6 dígitos, é usado `substring(0,6)` do código IBGE quando necessário.

\[
producaoPerCapita = \frac{\sum quantidadeProduzida(municipio)}{populacaoEstimada}
\]

Se não houver município/população no tenant, retorna `null`.

## KPIs por procedimento / CNES

Os KPIs por procedimento e por CNES usam o mesmo conjunto de métricas, alterando o filtro:

- Por CNES: `WHERE codigo_cnes = {cnes}`
- Por procedimento: `WHERE procedimento_codigo = {codigo}`

## Indicadores de apoio (analytics/relatórios)

### Tendência e crescimento

No agregado temporal (`sia_pa_agregado_temporal`):

- `valor_aprovado_prev = LAG(valor_aprovado_total)`
- `delta_valor_aprovado = valor_aprovado_total - valor_aprovado_prev`
- `crescimento_valor_aprovado_pct = (valor_aprovado_total / valor_aprovado_prev) - 1`

### Sazonalidade

Agrupa por mês (`SUBSTRING(competencia, 5, 2)`) e calcula médias no período.

