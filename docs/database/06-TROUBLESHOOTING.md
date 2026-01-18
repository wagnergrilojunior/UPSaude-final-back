# 🔧 Troubleshooting - Problemas Comuns e Soluções

## Visão Geral

Guia de resolução de problemas comuns com o banco de dados PostgreSQL.

---

## Problema 1: Sistema Completamente Travado

### 🔴 Sintomas

- Aplicação não responde
- Timeout em todas as requisições
- Usuários reportam erro 504 Gateway Timeout
- Dashboard não carrega

### 🔍 Diagnóstico

```bash
# Passo 1: Ver quantas queries ativas
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=1" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.total'
```

**Se retornar > 15**: Sistema travado com muitas queries.

```bash
# Passo 2: Ver estado das queries
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.queries[] | {pid, estado, duracao}'
```

**Se a maioria for "idle in transaction"**: Bug no código deixando transações abertas.

### ✅ Solução

```bash
# Solução 1: Kill-all (EMERGÊNCIA)
curl -X POST "http://localhost:8080/api/v1/admin/database/queries/kill-all" \
  -H "Authorization: Bearer $TOKEN"

# Solução 2: Reiniciar connection pool da aplicação
# (executar no servidor da aplicação)
docker restart upsaude-api
```

### 📋 Prevenção

```sql
-- Adicionar timeout para idle in transaction
ALTER DATABASE upsaude 
SET idle_in_transaction_session_timeout = '5min';

-- Adicionar timeout geral
ALTER DATABASE upsaude 
SET statement_timeout = '30min';
```

---

## Problema 2: Queries Lentas Após Import

### 🟡 Sintomas

- Queries simples demorando 10-30 segundos
- SELECT em tabelas grandes muito lento
- Problema começou após import de SIA-PA

### 🔍 Diagnóstico

```bash
# Verificar dead tuples na tabela importada
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=1" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.tabelas[] | select(.tabela=="sia_pa")'
```

**Resultado**:
```json
{
  "tabela": "sia_pa",
  "dead_tuples": 175000,
  "live_tuples": 17500000,
  "dead_ratio_pct": 1.0
}
```

**Diagnóstico**: Dead tuples OK (1%), problema é falta de ANALYZE.

### ✅ Solução

```bash
# Executar VACUUM ANALYZE para atualizar estatísticas
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/sia_pa" \
  -H "Authorization: Bearer $TOKEN"
```

**Resultado Esperado**: Queries voltam ao normal em 1-2 minutos.

### 📋 Prevenção

Sempre executar VACUUM ANALYZE após imports grandes:

```bash
# Após cada import
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/sia_pa" \
  -H "Authorization: Bearer $TOKEN"
```

---

## Problema 3: Disco Cheio

### 🔴 Sintomas

- Erro: "No space left on device"
- Aplicação crashando
- Impossível fazer INSERT/UPDATE

### 🔍 Diagnóstico

```bash
# Ver maiores tabelas
curl -X GET "http://localhost:8080/api/v1/admin/database/health/largest-tables?limite=10" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.tabelas[] | {tabela, tamanho, dead_ratio_pct}'
```

**Resultado**:
```json
[
  {"tabela": "sia_pa", "tamanho": "7.6 GB", "dead_ratio_pct": 0.01},
  {"tabela": "logs_sistema", "tamanho": "2.3 GB", "dead_ratio_pct": 85.0}
]
```

**Diagnóstico**: Tabela `logs_sistema` com 85% dead tuples!

### ✅ Solução Imediata

```bash
# 1. VACUUM FULL em logs_sistema (madrugada!)
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/logs_sistema?full=true" \
  -H "Authorization: Bearer $TOKEN"

# 2. Arquivar dados antigos
# SQL direto no banco:
DELETE FROM logs_sistema WHERE created_at < NOW() - INTERVAL '90 days';
```

**Resultado Esperado**: Liberar 1-2 GB imediatamente.

### 📋 Prevenção

```sql
-- Implementar particionamento por data
CREATE TABLE logs_sistema_2026_01 PARTITION OF logs_sistema
FOR VALUES FROM ('2026-01-01') TO ('2026-02-01');

-- Criar job para dropar partições antigas
-- (todo mês, dropar partições > 6 meses)
```

---

## Problema 4: Import SIA-PA Travando

### 🟡 Sintomas

- Import começou há 2 horas
- Ainda está rodando
- Não sabe se está travado ou só processando

### 🔍 Diagnóstico

```bash
# Passo 1: Ver a query do import
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=30" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.queries[] | select(.query | contains("sia_pa"))'
```

**Resultado**:
```json
{
  "pid": 55555,
  "duracao": "02:15:00",
  "estado": "active",
  "wait_event_type": "Lock",
  "wait_event": "relation",
  "query": "INSERT INTO sia_pa ..."
}
```

**Análise**:
- ⚠️ 2h15min executando
- 🔴 wait_event = "Lock" (está travado!)
- **DIAGNÓSTICO**: Query travada esperando lock

### ✅ Solução

```bash
# Passo 1: Ver quem está segurando o lock
curl -X GET "http://localhost:8080/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.locks[] | select(.tabela=="sia_pa" and .concedido==true)'
```

**Resultado**:
```json
{
  "pid": 54321,
  "tabela": "sia_pa",
  "concedido": true,
  "duracao": "02:20:00",
  "query_preview": "VACUUM sia_pa"
}
```

**Culpado**: VACUUM rodando há 2h20min!

```bash
# Passo 2: Decidir ação
# Se VACUUM está progredindo: Deixar terminar
# Se VACUUM está travado: Matar

# Ver se está progredindo (executar 2x com intervalo de 1min)
curl -X GET "http://localhost:8080/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.conexoes.ativas'

# Se número não muda: Está travado, pode matar
curl -X DELETE "http://localhost:8080/api/v1/admin/database/queries/54321" \
  -H "Authorization: Bearer $TOKEN"
```

### 📋 Prevenção

```bash
# Nunca executar VACUUM durante imports
# Agendar imports e VACUUMs em horários diferentes
```

---

## Problema 5: Muitas Conexões Idle

### 🟡 Sintomas

- Banco com 80+ conexões
- Maioria "idle"
- Aplicação lenta

### 🔍 Diagnóstico

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.conexoes'
```

**Resultado**:
```json
{
  "total": 95,
  "ativas": 5,
  "idle": 88,
  "idle_in_transaction": 2
}
```

**Diagnóstico**: Connection pool mal configurado (95 conexões, 88 idle).

### ✅ Solução

**No código (application.properties)**:
```properties
# ANTES
spring.datasource.hikari.maximum-pool-size=100
spring.datasource.hikari.minimum-idle=50

# DEPOIS
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=10000
spring.datasource.hikari.idle-timeout=300000
```

**Reiniciar aplicação**:
```bash
docker restart upsaude-api
```

**Verificar após 5 minutos**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.conexoes'
```

**Resultado Esperado**:
```json
{
  "total": 12,
  "ativas": 3,
  "idle": 9,
  "idle_in_transaction": 0
}
```

---

## Problema 6: Dead Tuples Não Diminuem Após VACUUM

### 🟡 Sintomas

- Executou VACUUM
- Dead tuples continuam altos
- VACUUM parece não funcionar

### 🔍 Diagnóstico

```bash
# Ver dead tuples após VACUUM
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.tabelas[] | select(.tabela=="sua_tabela")'
```

**Resultado**:
```json
{
  "tabela": "sua_tabela",
  "dead_tuples": 50000,
  "live_tuples": 10000,
  "dead_ratio_pct": 83.0,
  "ultimo_vacuum": "2026-01-18T10:00:00Z"
}
```

**Diagnóstico**: VACUUM executou mas dead tuples não diminuíram!

**Causa Provável**: Transação antiga segurando snapshot.

```bash
# Ver transações antigas
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=60" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.queries[] | {pid, duracao, estado}'
```

**Resultado**:
```json
{
  "pid": 99999,
  "duracao": "05:30:00",
  "estado": "idle in transaction"
}
```

**Culpado**: Transação há 5h30min segurando snapshot!

### ✅ Solução

```bash
# Matar a transação antiga
curl -X DELETE "http://localhost:8080/api/v1/admin/database/queries/99999" \
  -H "Authorization: Bearer $TOKEN"

# Executar VACUUM novamente
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/sua_tabela" \
  -H "Authorization: Bearer $TOKEN"
```

**Resultado Esperado**: Dead tuples diminuem para < 1%.

---

## 📊 Fluxograma de Troubleshooting

```
Sistema Lento?
    │
    ├─ Ver queries ativas (/queries?minutosMinimoLenta=5)
    │   │
    │   ├─ Muitas queries (>15)?
    │   │   └─ Kill-all (/queries/kill-all)
    │   │
    │   └─ Poucas queries mas lentas?
    │       └─ Ver dead tuples (/health/dead-tuples)
    │           │
    │           ├─ Dead tuples alto (>20%)?
    │           │   └─ VACUUM (/maintenance/vacuum/...)
    │           │
    │           └─ Dead tuples OK?
    │               └─ Problema no código (otimizar queries)
    │
    └─ Ver conexões (/health/stats)
        │
        ├─ idle_in_transaction > 0?
        │   └─ Matar queries (/queries/kill-all)
        │
        └─ Muitas conexões idle (>80%)?
            └─ Ajustar connection pool
```

---

## 📋 Comandos de Emergência (Copiar/Colar)

### Sistema Travado - Kill-All

```bash
TOKEN="seu_token_aqui"
curl -X POST "http://localhost:8080/api/v1/admin/database/queries/kill-all" \
  -H "Authorization: Bearer $TOKEN"
```

### Ver Situação Geral

```bash
TOKEN="seu_token_aqui"
curl -X GET "http://localhost:8080/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### VACUUM em Tabelas Problemáticas

```bash
TOKEN="seu_token_aqui"
for tabela in $(curl -s -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=30" -H "Authorization: Bearer $TOKEN" | jq -r '.tabelas[].tabela'); do
  echo "VACUUM em $tabela"
  curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/$tabela" \
    -H "Authorization: Bearer $TOKEN"
  sleep 30
done
```

---

## 🆘 Quando Escalar para DBA

### Escalar IMEDIATAMENTE Se:

1. 🔴 Banco não responde (nem via psql)
2. 🔴 Disco em 100% e não consegue liberar
3. 🔴 Corrupção de dados (erros de checksum)
4. 🔴 Replicação quebrada
5. 🔴 Performance caiu 90% sem causa aparente

### Escalar em 1-2 Horas Se:

1. 🟠 Kill-all não resolveu travamento
2. 🟠 VACUUM não diminui dead tuples
3. 🟠 Queries lentas após todos troubleshootings
4. 🟠 Locks em cascata sem causa clara

---

**Fim da Documentação**

---

## 📚 Índice Completo

1. [Visão Geral](01-OVERVIEW.md) - Casos de uso e introdução
2. [Listar Queries](02-ENDPOINTS-QUERIES.md) - Monitorar queries ativas
3. [Matar Queries](03-KILL-QUERIES.md) - Como e quando encerrar queries
4. [Monitoramento de Saúde](04-HEALTH-MONITORING.md) - Dead tuples e estatísticas
5. [Manutenção e VACUUM](05-MAINTENANCE-VACUUM.md) - Limpeza e otimização
6. [Troubleshooting](06-TROUBLESHOOTING.md) - Este arquivo

---

**Suporte**: Time de Infraestrutura  
**Última Atualização**: 2026-01-18  
**Versão**: 1.0.0
