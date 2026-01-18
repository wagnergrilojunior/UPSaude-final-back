# 🧹 Manutenção e VACUUM - Guia Completo

## Visão Geral

Guia detalhado sobre executar VACUUM e outras operações de manutenção no banco de dados.

---

## 7. Executar VACUUM

### 📋 Informações Básicas

**Método**: `POST`  
**Endpoint**: `/api/v1/admin/database/maintenance/vacuum/{schema}/{tabela}`  
**Autenticação**: Bearer Token

### 🎯 Propósito

Executa VACUUM (ou VACUUM FULL) em uma tabela específica para:
- Limpar dead tuples
- Liberar espaço em disco (VACUUM FULL)
- Atualizar estatísticas do planejador
- Melhorar performance de queries

### 📥 Parâmetros

| Nome | Tipo | Obrigatório | Padrão | Descrição |
|------|------|-------------|--------|-----------|
| `schema` | String | Sim (path) | - | Nome do schema (ex: `public`) |
| `tabela` | String | Sim (path) | - | Nome da tabela |
| `full` | Boolean | Não (query) | false | Se true, executa VACUUM FULL |

### 📤 Resposta de Sucesso (200 OK)

```json
{
  "sucesso": true,
  "schema": "public",
  "tabela": "estados",
  "tipo": "VACUUM",
  "duracao_ms": 1523,
  "timestamp": "2026-01-18T10:15:32Z"
}
```

### 📤 Resposta de Erro (500)

```json
{
  "sucesso": false,
  "erro": "Erro ao executar VACUUM",
  "mensagem": "permission denied for table estados"
}
```

---

## 🔍 VACUUM vs VACUUM FULL

### VACUUM Normal

**Características**:
- ✅ **NÃO bloqueia** a tabela (pode continuar lendo/escrevendo)
- ✅ Rápido (segundos a minutos)
- ✅ Seguro para executar em produção
- ❌ NÃO devolve espaço ao sistema operacional
- ✅ Torna espaço disponível para reutilização interna

**Quando Usar**:
- ✅ Manutenção preventiva regular
- ✅ Dead tuples entre 10-50%
- ✅ Durante horário comercial (não bloqueia)
- ✅ Tabelas grandes (GB)

**Exemplo**:
```bash
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/estados" \
  -H "Authorization: Bearer $TOKEN"
```

---

### VACUUM FULL

**Características**:
- 🔴 **BLOQUEIA** a tabela completamente
- 🔴 Lento (pode levar horas em tabelas grandes)
- 🔴 Requer 2x o espaço em disco temporariamente
- ✅ Devolve espaço ao sistema operacional
- ✅ Reorganiza fisicamente a tabela (defragmenta)

**Quando Usar**:
- ⚠️ Dead tuples > 70%
- ⚠️ Apenas em **horário de baixo tráfego** (madrugada)
- ⚠️ Quando precisa liberar espaço em disco urgentemente
- ⚠️ Tabelas pequenas (< 1GB)

**Exemplo**:
```bash
# ATENÇÃO: Executar apenas em madrugada!
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/competencia_financeira?full=true" \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📊 Exemplos Práticos

### Exemplo 1: Manutenção Preventiva Semanal

#### Cenário
Segunda-feira, 9h da manhã, executar manutenção semanal.

#### Passo 1: Identificar Tabelas

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=15" \
  -H "Authorization: Bearer $TOKEN" \
  | jq -r '.tabelas[] | .tabela'
```

**Resultado**:
```
estados
agendamentos
notificacoes
```

#### Passo 2: Executar VACUUM em Cada Tabela

```bash
for tabela in estados agendamentos notificacoes; do
  echo "VACUUM em $tabela..."
  
  curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/$tabela" \
    -H "Authorization: Bearer $TOKEN" \
    | jq '{tabela: .tabela, duracao_ms: .duracao_ms, sucesso: .sucesso}'
  
  # Aguardar entre execuções
  sleep 30
done
```

**Saída Esperada**:
```json
{"tabela": "estados", "duracao_ms": 1234, "sucesso": true}
{"tabela": "agendamentos", "duracao_ms": 2456, "sucesso": true}
{"tabela": "notificacoes", "duracao_ms": 890, "sucesso": true}
```

#### Passo 3: Verificar Resultado

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=15" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.total'
```

**Resultado Esperado**: `0` (nenhuma tabela com >15% dead tuples)

---

### Exemplo 2: VACUUM FULL de Emergência

#### Cenário
Tabela `competencia_financeira` com 92% dead tuples, precisa liberar espaço em disco.

#### ⚠️ PRÉ-REQUISITOS:

1. **Horário**: Madrugada (2h-5h)
2. **Avisar Time**: Notificar que tabela ficará bloqueada
3. **Backup Recente**: Garantir backup de hoje
4. **Espaço em Disco**: Ter 2x o tamanho da tabela livre

#### Passo 1: Verificar Situação

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=0" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.tabelas[] | select(.tabela=="competencia_financeira")'
```

**Resultado**:
```json
{
  "tabela": "competencia_financeira",
  "dead_tuples": 23,
  "live_tuples": 2,
  "dead_ratio_pct": 92.0,
  "tamanho": "144 kB"
}
```

**Análise**:
- 🔴 92% dead tuples (CRÍTICO!)
- ✅ Tabela pequena (144 kB)
- ✅ VACUUM FULL vai levar poucos segundos

#### Passo 2: Executar VACUUM FULL

```bash
# ATENÇÃO: Bloqueia a tabela!
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/competencia_financeira?full=true" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.'
```

**Resposta**:
```json
{
  "sucesso": true,
  "schema": "public",
  "tabela": "competencia_financeira",
  "tipo": "VACUUM FULL",
  "duracao_ms": 856,
  "timestamp": "2026-01-18T02:15:00Z"
}
```

#### Passo 3: Verificar Melhoria

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=0" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.tabelas[] | select(.tabela=="competencia_financeira")'
```

**Resultado**:
```json
{
  "tabela": "competencia_financeira",
  "dead_tuples": 0,
  "live_tuples": 2,
  "dead_ratio_pct": 0.0,
  "tamanho": "16 kB"  // ✅ Reduzido de 144 kB para 16 kB!
}
```

**Análise**:
- ✅ Dead tuples: 0
- ✅ Tamanho reduzido: 144 kB → 16 kB (89% de economia!)
- ✅ Performance melhorada

---

## 🚦 Matriz de Decisão: Qual VACUUM Usar?

| Critério | VACUUM Normal | VACUUM FULL |
|----------|---------------|-------------|
| **Tamanho da Tabela** | Qualquer | < 1 GB |
| **Dead Tuples %** | 10-70% | > 70% |
| **Horário** | Comercial OK | **Só madrugada** |
| **Bloqueio** | Não bloqueia | **Bloqueia tudo** |
| **Duração** | Segundos/minutos | Minutos/horas |
| **Espaço Disco** | Não precisa | **Precisa 2x** |
| **Libera Disco** | Não | Sim |
| **Frequência** | Semanal | Semestral |

---

## ⏱️ Tempos Esperados por Tamanho

### VACUUM Normal

| Tamanho | Registros | Tempo Esperado | Exemplo |
|---------|-----------|----------------|---------|
| < 10 MB | < 100k | 1-5 segundos | `estados`, `tenants` |
| 10-100 MB | 100k-1M | 5-30 segundos | `agendamentos`, `pacientes` |
| 100 MB-1 GB | 1M-10M | 30s-5 minutos | `atendimentos` |
| 1-10 GB | 10M-100M | 5-30 minutos | `sia_pa` |
| > 10 GB | > 100M | 30min-2 horas | Tabelas históricas |

### VACUUM FULL

| Tamanho | Registros | Tempo Esperado | ⚠️ Atenção |
|---------|-----------|----------------|------------|
| < 10 MB | < 100k | 1-10 segundos | OK executar |
| 10-100 MB | 100k-1M | 10s-2 minutos | OK madrugada |
| 100 MB-1 GB | 1M-10M | 2-30 minutos | Apenas emergência |
| > 1 GB | > 10M | **HORAS** | ❌ NÃO RECOMENDADO |

**⚠️ REGRA**: Nunca execute VACUUM FULL em tabelas > 1GB!

---

## 📋 Checklist Antes de VACUUM FULL

### Verificações Obrigatórias:

- [ ] É madrugada (2h-5h)?
- [ ] Avisei o time no Slack/Email?
- [ ] Tabela tem < 1 GB?
- [ ] Tenho 2x o tamanho da tabela livre em disco?
- [ ] Backup recente (últimas 24h)?
- [ ] Sei quanto tempo vai demorar?
- [ ] Ninguém está usando a tabela agora?

### Verificar Uso da Tabela:

```bash
# Ver se alguém está usando
curl -X GET "http://localhost:8080/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.locks[] | select(.tabela=="sua_tabela")'
```

**Se retornar algo**: Alguém está usando, **NÃO execute VACUUM FULL!**

---

## 🔄 Alternativas ao VACUUM FULL

### Opção 1: VACUUM Normal + Ajuste de Configuração

Ao invés de VACUUM FULL, configure autovacuum mais agressivo:

```sql
ALTER TABLE sua_tabela SET (
  autovacuum_vacuum_scale_factor = 0.05,
  autovacuum_vacuum_threshold = 50
);
```

**Vantagem**: Não precisa VACUUM FULL nunca mais.

---

### Opção 2: REINDEX CONCURRENTLY

Se o problema é índices inchados:

```sql
-- Reindexar sem bloquear
REINDEX INDEX CONCURRENTLY nome_do_indice;
```

**Vantagem**: Não bloqueia leituras/escritas.

---

### Opção 3: Recriar Tabela (Caso Extremo)

Para tabelas **muito** inchadas (>80% dead tuples em tabelas grandes):

```sql
-- 1. Criar tabela nova
CREATE TABLE nova_tabela (LIKE tabela_antiga INCLUDING ALL);

-- 2. Copiar dados vivos
INSERT INTO nova_tabela SELECT * FROM tabela_antiga;

-- 3. Trocar nomes (em transação)
BEGIN;
ALTER TABLE tabela_antiga RENAME TO tabela_antiga_backup;
ALTER TABLE nova_tabela RENAME TO tabela_antiga;
COMMIT;

-- 4. Recriar dependências (views, FKs, etc)
```

**⚠️ ATENÇÃO**: Apenas para casos extremos, requer planejamento.

---

## 8. Listar Locks Ativos

### 📋 Informações Básicas

**Método**: `GET`  
**Endpoint**: `/api/v1/admin/database/locks`  
**Autenticação**: Bearer Token

### 🎯 Propósito

Lista todos os locks ativos no banco para diagnosticar:
- Deadlocks
- Queries esperando locks
- Quem está segurando o lock
- Transações longas bloqueando outras

### 📤 Resposta de Sucesso (200 OK)

```json
{
  "total": 15,
  "locks": [
    {
      "tipo_lock": "relation",
      "database": "16384",
      "tabela": "sia_pa",
      "modo": "RowExclusiveLock",
      "concedido": true,
      "pid": 12345,
      "usuario": "postgres",
      "aplicacao": "UPSaude-API",
      "duracao": "00:05:23",
      "query_preview": "INSERT INTO sia_pa (competencia, uf, ...) VALUES (...)"
    },
    {
      "tipo_lock": "relation",
      "database": "16384",
      "tabela": "sia_pa",
      "modo": "RowExclusiveLock",
      "concedido": false,
      "pid": 12346,
      "usuario": "postgres",
      "aplicacao": "pgAdmin",
      "duracao": "00:05:18",
      "query_preview": "INSERT INTO sia_pa (competencia, uf, ...) VALUES (...)"
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

## 🔍 Interpretando Locks

### Campos Importantes

| Campo | Significado | Como Usar |
|-------|-------------|-----------|
| `concedido: true` | Processo **TEM** o lock | Este é quem está **segurando** |
| `concedido: false` | Processo **ESPERANDO** lock | Este é quem está **travado** |
| `modo` | Tipo de lock | Ver tabela de modos abaixo |
| `duracao` | Tempo segurando/esperando | Se > 5min, investigar |

---

### Modos de Lock

| Modo | Significado | Conflita Com |
|------|-------------|--------------|
| `AccessShareLock` | SELECT simples | Nada (seguro) |
| `RowShareLock` | SELECT FOR UPDATE | Exclusivo |
| `RowExclusiveLock` | INSERT/UPDATE/DELETE | Exclusivo e Share |
| `ShareLock` | CREATE INDEX | Exclusivo |
| `ExclusiveLock` | VACUUM, CREATE INDEX CONCURRENTLY | Tudo exceto AccessShare |
| `AccessExclusiveLock` | ALTER TABLE, DROP TABLE, VACUUM FULL | **TUDO** |

---

## 📊 Exemplo Prático: Diagnosticar Deadlock

### Cenário
Várias queries travadas, sistema lento.

#### Passo 1: Ver Locks

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.locks[] | {pid, tabela, modo, concedido, duracao}'
```

**Resultado**:
```json
[
  {"pid": 100, "tabela": "sia_pa", "modo": "RowExclusiveLock", "concedido": true, "duracao": "00:15:00"},
  {"pid": 101, "tabela": "sia_pa", "modo": "RowExclusiveLock", "concedido": false, "duracao": "00:14:55"},
  {"pid": 102, "tabela": "sia_pa", "modo": "RowExclusiveLock", "concedido": false, "duracao": "00:14:50"}
]
```

**Análise**:
- PID 100: **Segurando** lock há 15 minutos
- PID 101, 102: **Esperando** lock há ~15 minutos

**Culpado**: PID 100

#### Passo 2: Identificar Query do Culpado

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=1" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.queries[] | select(.pid==100)'
```

**Resultado**:
```json
{
  "pid": 100,
  "duracao": "00:15:00",
  "estado": "idle in transaction",
  "query": "BEGIN; INSERT INTO sia_pa ..."
}
```

**Análise**: 🔴 **idle in transaction** há 15 minutos!

#### Passo 3: Matar o Culpado

```bash
curl -X DELETE "http://localhost:8080/api/v1/admin/database/queries/100" \
  -H "Authorization: Bearer $TOKEN"
```

#### Passo 4: Verificar Liberação

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.total'
```

**Resultado Esperado**: Locks diminuíram (outras queries liberadas).

---

## 📋 Checklist de Manutenção Regular

### Diária (Automática)

```bash
# Monitorar dead tuples críticos
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=50" \
  -H "Authorization: Bearer $TOKEN"

# Se encontrar: Alertar
```

### Semanal (Segunda 9h)

```bash
# 1. Ver tabelas problemáticas
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=15" \
  -H "Authorization: Bearer $TOKEN" \
  | tee dead_tuples.json

# 2. VACUUM em cada uma
jq -r '.tabelas[] | .tabela' dead_tuples.json | while read tabela; do
  curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/$tabela" \
    -H "Authorization: Bearer $TOKEN"
  sleep 30
done

# 3. Verificar melhoria
```

### Mensal (Primeiro domingo 2h)

```bash
# 1. VACUUM FULL em tabelas pequenas muito sujas
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=70" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.tabelas[] | select(.tamanho_bytes < 104857600) | .tabela'  # < 100 MB

# 2. Executar VACUUM FULL (madrugada!)
```

---

**Próximo**: [Troubleshooting](06-TROUBLESHOOTING.md)
