# 🩺 Monitoramento de Saúde do Banco - Guia Completo

## Visão Geral

Endpoints para monitorar a saúde geral do banco de dados, identificar problemas de performance e planejar manutenções.

---

## 4. Dead Tuples por Tabela

### 📋 Informações Básicas

**Método**: `GET`  
**Endpoint**: `/api/v1/admin/database/health/dead-tuples`  
**Autenticação**: Bearer Token

### 🎯 Propósito

Lista tabelas com dead tuples (registros mortos) acima de um percentual específico. Dead tuples causam:
- 📉 Queries mais lentas
- 💾 Desperdício de espaço em disco
- 🔒 Problemas de lock

### 📥 Parâmetros

| Nome | Tipo | Obrigatório | Padrão | Descrição |
|------|------|-------------|--------|-----------|
| `percentualMinimo` | Integer | Não | 10 | Percentual mínimo de dead tuples (0-100) |

### 📤 Resposta de Sucesso (200 OK)

```json
{
  "total": 5,
  "filtro_percentual_minimo": 20,
  "tabelas": [
    {
      "schema": "public",
      "tabela": "competencia_financeira",
      "dead_tuples": 23,
      "live_tuples": 2,
      "dead_ratio_pct": 92.00,
      "tamanho": "144 kB",
      "ultimo_vacuum": "2026-01-15T20:57:00Z",
      "ultimo_autovacuum": "2026-01-15T20:57:00Z",
      "autovacuum_count": 3
    },
    {
      "schema": "public",
      "tabela": "estados",
      "dead_tuples": 12,
      "live_tuples": 29,
      "dead_ratio_pct": 41.00,
      "tamanho": "104 kB",
      "ultimo_vacuum": null,
      "ultimo_autovacuum": "2026-01-13T09:56:07Z",
      "autovacuum_count": 8
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

## 🚦 Interpretação de Dead Tuples

### 🟢 Saudável (0-10%)

**Exemplo**:
```json
{
  "tabela": "pacientes",
  "dead_ratio_pct": 5.0,
  "dead_tuples": 500,
  "live_tuples": 10000
}
```

**Análise**: ✅ Normal e saudável
- Dead tuples estão sendo limpos regularmente
- Autovacuum funcionando bem
- Sem necessidade de ação

---

### 🟡 Atenção (10-30%)

**Exemplo**:
```json
{
  "tabela": "agendamentos",
  "dead_ratio_pct": 25.0,
  "dead_tuples": 2500,
  "live_tuples": 7500,
  "ultimo_autovacuum": "2026-01-10T08:00:00Z"
}
```

**Análise**: ⚠️ Requer atenção
- Autovacuum pode estar lento demais
- Tabela com muitos UPDATEs/DELETEs
- **AÇÃO**: Monitorar próximos dias

**Quando Agir**:
- Se não diminuir em 48h
- Se percentual subir acima de 30%
- Se queries começarem a ficar lentas

---

### 🟠 Problema (30-70%)

**Exemplo**:
```json
{
  "tabela": "estados",
  "dead_ratio_pct": 45.0,
  "dead_tuples": 450,
  "live_tuples": 550,
  "ultimo_autovacuum": "2026-01-05T00:00:00Z",
  "autovacuum_count": 2
}
```

**Análise**: ⚠️ Problema configuracional
- Autovacuum não está rodando frequentemente
- Tabela pequena mas muito atualizada
- **AÇÃO IMEDIATA**: Executar VACUUM manual

**Como Resolver**:
```bash
# 1. Executar VACUUM agora
POST /api/v1/admin/database/maintenance/vacuum/public/estados

# 2. Ajustar configuração autovacuum
ALTER TABLE estados SET (
  autovacuum_vacuum_threshold = 10,
  autovacuum_vacuum_scale_factor = 0.05
);
```

---

### 🔴 Crítico (>70%)

**Exemplo**:
```json
{
  "tabela": "competencia_financeira",
  "dead_ratio_pct": 92.0,
  "dead_tuples": 23,
  "live_tuples": 2,
  "ultimo_autovacuum": "2026-01-15T20:57:00Z",
  "autovacuum_count": 3,
  "tamanho": "144 kB"
}
```

**Análise**: 🔴 CRÍTICO - Ação Urgente
- 92% de desperdício!
- Apenas 2 registros vivos de 25 totais
- Muitos deletes sem vacuum efetivo

**AÇÃO IMEDIATA**:

```bash
# PASSO 1: Executar VACUUM FULL (OFF-PEAK!)
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/competencia_financeira?full=true" \
  -H "Authorization: Bearer $TOKEN"

# PASSO 2: Verificar melhoria
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=0" \
  -H "Authorization: Bearer $TOKEN" | jq '.tabelas[] | select(.tabela=="competencia_financeira")'
```

**Resultado Esperado**:
```json
{
  "tabela": "competencia_financeira",
  "dead_ratio_pct": 0.0,
  "dead_tuples": 0,
  "live_tuples": 2
}
```

---

## 📊 Exemplo Prático: Monitoramento Semanal

### Rotina de Monitoramento

**Executar toda segunda-feira às 9h**:

```bash
# 1. Ver tabelas com >20% dead tuples
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.'
```

**Se encontrar problemas**:

```bash
# 2. Para cada tabela problemática, executar VACUUM
for tabela in $(jq -r '.tabelas[].tabela' response.json); do
  echo "VACUUM em $tabela..."
  curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/$tabela" \
    -H "Authorization: Bearer $TOKEN"
  sleep 10
done

# 3. Verificar após 1 hora
sleep 3600
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.total'
```

**Resultado Esperado**: `0` (nenhuma tabela com >20%)

---

## 5. Estatísticas Gerais do Banco

### 📋 Informações Básicas

**Método**: `GET`  
**Endpoint**: `/api/v1/admin/database/health/stats`  
**Autenticação**: Bearer Token

### 🎯 Propósito

Dashboard completo com estatísticas agregadas do banco de dados.

### 📤 Resposta de Sucesso (200 OK)

```json
{
  "total_tabelas": 150,
  "tamanho_total": "8.5 GB",
  "total_registros_vivos": 18500000,
  "total_dead_tuples": 15000,
  "media_dead_ratio_pct": 0.08,
  "total_inserts": 25000000,
  "total_updates": 250000,
  "total_deletes": 15000,
  "conexoes": {
    "total": 25,
    "ativas": 5,
    "idle": 18,
    "idle_in_transaction": 2
  },
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

## 📊 Interpretação das Estatísticas

### Conexões

```json
{
  "conexoes": {
    "total": 25,
    "ativas": 5,
    "idle": 18,
    "idle_in_transaction": 2
  }
}
```

**Análise por Campo**:

| Campo | Valor Saudável | Preocupante | Ação |
|-------|----------------|-------------|------|
| `total` | < 50 | > 80 | Verificar pool de conexões |
| `ativas` | 10-30% do total | > 50% | Investigar queries lentas |
| `idle` | 60-80% do total | > 90% | Connection pool mal configurado |
| `idle_in_transaction` | **0** | > 2 | 🔴 Matar imediatamente |

**Exemplo Problemático**:
```json
{
  "conexoes": {
    "total": 95,
    "ativas": 3,
    "idle": 5,
    "idle_in_transaction": 87  // 🔴 CRÍTICO!
  }
}
```

**AÇÃO IMEDIATA**:
```bash
POST /api/v1/admin/database/queries/kill-all
```

---

### Dead Tuples Geral

```json
{
  "total_registros_vivos": 18500000,
  "total_dead_tuples": 15000,
  "media_dead_ratio_pct": 0.08
}
```

**Interpretação**:
- ✅ 0.08% média é **excelente**
- ✅ 15k dead tuples em 18.5M registros é **normal**

**Limites Recomendados**:
- 🟢 < 1%: Excelente
- 🟡 1-5%: Aceitável
- 🟠 5-10%: Requer atenção
- 🔴 > 10%: Problema crítico

---

### Operações (Inserts/Updates/Deletes)

```json
{
  "total_inserts": 25000000,
  "total_updates": 250000,
  "total_deletes": 15000
}
```

**Análise de Padrão**:
- Inserts: 25M
- Updates: 250k (1% dos inserts)
- Deletes: 15k (0.06% dos inserts)

**Interpretação**: ✅ **Padrão Saudável**
- Sistema majoritariamente append-only (muitos inserts)
- Poucos updates (bom para performance)
- Pouquíssimos deletes (menos dead tuples)

**Padrão Problemático**:
```json
{
  "total_inserts": 1000000,
  "total_updates": 5000000,  // 🔴 5x mais updates que inserts!
  "total_deletes": 500000    // 🔴 50% de deletes
}
```

**Indica**:
- Schema mal projetado (muitos updates)
- Possível uso incorreto de ORM
- Necessidade de revisão arquitetural

---

## 6. Maiores Tabelas

### 📋 Informações Básicas

**Método**: `GET`  
**Endpoint**: `/api/v1/admin/database/health/largest-tables`  
**Autenticação**: Bearer Token

### 🎯 Propósito

Identifica as maiores tabelas para planejar:
- Particionamento
- Arquivamento
- Otimização de índices
- Capacidade de disco

### 📥 Parâmetros

| Nome | Tipo | Obrigatório | Padrão | Descrição |
|------|------|-------------|--------|-----------|
| `limite` | Integer | Não | 20 | Número de tabelas a retornar |

### 📤 Resposta de Sucesso (200 OK)

```json
{
  "total": 20,
  "limite": 20,
  "tabelas": [
    {
      "schema": "public",
      "tabela": "sia_pa",
      "tamanho": "7.6 GB",
      "tamanho_bytes": 7593271296,
      "registros_vivos": 17560843,
      "dead_tuples": 1218,
      "dead_ratio_pct": 0.01
    },
    {
      "schema": "public",
      "tabela": "pacientes",
      "tamanho": "512 MB",
      "tamanho_bytes": 536870912,
      "registros_vivos": 125000,
      "dead_tuples": 250,
      "dead_ratio_pct": 0.20
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

## 📊 Análise de Crescimento

### Quando uma Tabela Precisa de Atenção?

#### Tabela Grande com Alto Crescimento

**Exemplo**:
```json
{
  "tabela": "sia_pa",
  "tamanho": "7.6 GB",
  "registros_vivos": 17560843,
  "dead_ratio_pct": 0.01
}
```

**Análise**: ⚠️ Atenção
- Tabela grande (7.6 GB)
- Crescendo rapidamente (17M registros)
- Dead tuples baixos (OK)

**AÇÕES RECOMENDADAS**:

1. **Particionamento por Data**:
```sql
-- Criar partições mensais
CREATE TABLE sia_pa_202401 PARTITION OF sia_pa
  FOR VALUES FROM ('202401') TO ('202402');
```

2. **Arquivamento**:
```sql
-- Mover dados antigos (>2 anos) para tabela de arquivo
INSERT INTO sia_pa_archive 
SELECT * FROM sia_pa WHERE competencia < '202201';

DELETE FROM sia_pa WHERE competencia < '202201';
```

3. **Monitorar Crescimento**:
```bash
# Executar mensalmente
curl -X GET "http://localhost:8080/api/v1/admin/database/health/largest-tables?limite=5" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.tabelas[] | {tabela, tamanho, registros_vivos}'
```

---

## 📋 Checklist de Monitoramento Regular

### Diário (10 minutos)

```bash
# 1. Verificar conexões
curl -X GET "http://localhost:8080/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.conexoes'

# Se idle_in_transaction > 0: Investigar
```

### Semanal (30 minutos)

```bash
# 1. Dead tuples problemáticos
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" \
  -H "Authorization: Bearer $TOKEN"

# 2. Executar VACUUM nas tabelas encontradas
# 3. Verificar queries lentas (>5min)
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN"
```

### Mensal (2 horas)

```bash
# 1. Crescimento de tabelas
curl -X GET "http://localhost:8080/api/v1/admin/database/health/largest-tables?limite=10" \
  -H "Authorization: Bearer $TOKEN"

# 2. Planejar arquivamento se necessário
# 3. Revisar configurações de autovacuum
# 4. Analisar padrões de insert/update/delete
```

---

**Próximo**: [Manutenção e VACUUM](05-MAINTENANCE.md)
