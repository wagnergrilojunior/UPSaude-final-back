# 🔍 API de Monitoramento e Gerenciamento do Banco de Dados

## Visão Geral

Controller REST para monitoramento da saúde do banco de dados PostgreSQL e gerenciamento de queries represadas.

**Endpoint Base**: `/api/v1/admin/database`  
**Autenticação**: Requer role `ADMIN_SISTEMA`

---

## 📋 Endpoints Disponíveis

### 1. Listar Queries Ativas/Represadas

**GET** `/api/v1/admin/database/queries?minutosMinimoLenta=1`

Lista todas as queries em execução há mais de X minutos.

**Parâmetros**:
- `minutosMinimoLenta` (opcional, padrão: 1): Filtrar queries com duração mínima em minutos

**Resposta**:
```json
{
  "total": 5,
  "filtro_minutos": 1,
  "queries": [
    {
      "pid": 12345,
      "duracao": "00:15:32.123456",
      "usuario": "postgres",
      "aplicacao": "UPSaude-API",
      "ip_cliente": "192.168.1.100",
      "estado": "active",
      "wait_event_type": "Lock",
      "wait_event": "relation",
      "inicio": "2026-01-18T10:00:00Z",
      "ultima_mudanca_estado": "2026-01-18T10:00:00Z",
      "query": "INSERT INTO sia_pa ..."
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

### 2. Matar Query Específica

**DELETE** `/api/v1/admin/database/queries/{pid}`

Encerra uma query específica pelo PID do processo.

**Parâmetros**:
- `pid` (path): PID do processo a ser encerrado

**Resposta**:
```json
{
  "pid": 12345,
  "sucesso": true,
  "mensagem": "Query encerrada com sucesso",
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

### 3. Matar TODAS as Queries Represadas

**POST** `/api/v1/admin/database/queries/kill-all`

Encerra automaticamente:
- Queries `idle in transaction` há mais de 5 minutos
- Queries ativas há mais de 30 minutos

**Resposta**:
```json
{
  "total_encontradas": 12,
  "sucessos": 10,
  "falhas": 2,
  "queries_encerradas": [
    {
      "pid": 12345,
      "duracao": "01:23:45",
      "estado": "idle in transaction",
      "query_preview": "INSERT INTO sia_pa ...",
      "encerrada": true
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

### 4. Dead Tuples por Tabela

**GET** `/api/v1/admin/database/health/dead-tuples?percentualMinimo=10`

Lista tabelas com dead tuples acima do percentual especificado.

**Parâmetros**:
- `percentualMinimo` (opcional, padrão: 10): Percentual mínimo de dead tuples

**Resposta**:
```json
{
  "total": 8,
  "filtro_percentual_minimo": 10,
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
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

### 5. Estatísticas Gerais do Banco

**GET** `/api/v1/admin/database/health/stats`

Retorna estatísticas agregadas do banco de dados.

**Resposta**:
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

### 6. Maiores Tabelas

**GET** `/api/v1/admin/database/health/largest-tables?limite=20`

Lista as maiores tabelas por tamanho em disco.

**Parâmetros**:
- `limite` (opcional, padrão: 20): Número de tabelas a listar

**Resposta**:
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
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

### 7. Executar VACUUM em Tabela

**POST** `/api/v1/admin/database/maintenance/vacuum/{schema}/{tabela}?full=false`

Executa VACUUM (ou VACUUM FULL) em uma tabela específica.

**Parâmetros**:
- `schema` (path): Nome do schema (ex: `public`)
- `tabela` (path): Nome da tabela
- `full` (opcional, padrão: false): Se true, executa VACUUM FULL (mais lento, bloqueia tabela)

**Resposta**:
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

⚠️ **ATENÇÃO**: VACUUM FULL bloqueia a tabela durante execução!

---

### 8. Listar Locks Ativos

**GET** `/api/v1/admin/database/locks`

Lista todos os locks ativos no banco de dados.

**Resposta**:
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
      "query_preview": "INSERT INTO sia_pa ..."
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

## 🎯 Casos de Uso

### Cenário 1: Identificar e Matar Queries Lentas

```bash
# 1. Listar queries ativas há mais de 5 minutos
GET /api/v1/admin/database/queries?minutosMinimoLenta=5

# 2. Matar query específica
DELETE /api/v1/admin/database/queries/12345

# 3. Verificar se foi encerrada
GET /api/v1/admin/database/queries?minutosMinimoLenta=1
```

### Cenário 2: Limpeza de Queries Represadas

```bash
# Matar TODAS as queries represadas de uma vez
POST /api/v1/admin/database/queries/kill-all
```

### Cenário 3: Monitorar Saúde do Banco

```bash
# 1. Ver estatísticas gerais
GET /api/v1/admin/database/health/stats

# 2. Ver dead tuples problemáticos
GET /api/v1/admin/database/health/dead-tuples?percentualMinimo=20

# 3. Ver maiores tabelas
GET /api/v1/admin/database/health/largest-tables?limite=10
```

### Cenário 4: Manutenção Preventiva

```bash
# 1. Identificar tabelas com dead tuples altos
GET /api/v1/admin/database/health/dead-tuples?percentualMinimo=30

# 2. Executar VACUUM na tabela problemática
POST /api/v1/admin/database/maintenance/vacuum/public/estados

# 3. Verificar melhoria
GET /api/v1/admin/database/health/dead-tuples?percentualMinimo=10
```

---

## 🔐 Segurança

- ✅ Apenas usuários com role `ADMIN_SISTEMA` podem acessar
- ✅ Validação de nomes de schema e tabela para prevenir SQL injection
- ✅ Logs detalhados de todas as operações
- ⚠️ Use VACUUM FULL com cuidado (bloqueia tabela)

---

## 📊 Swagger/OpenAPI

Acesse a documentação interativa em:
- **Local**: `http://localhost:8080/swagger-ui.html`
- **Prod**: `https://api.upsaude.com.br/swagger-ui.html`

Navegue até **Database Health** para testar os endpoints.

---

## 🚀 Exemplos com cURL

### Listar Queries Represadas
```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=1" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Matar Query Específica
```bash
curl -X DELETE "http://localhost:8080/api/v1/admin/database/queries/12345" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Matar TODAS Queries Represadas
```bash
curl -X POST "http://localhost:8080/api/v1/admin/database/queries/kill-all" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Ver Dead Tuples
```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=10" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Executar VACUUM
```bash
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/estados" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

## ⚡ Performance

- Todas as queries são otimizadas e usam índices do PostgreSQL
- Limite de 50-100 registros nas listagens para evitar sobrecarga
- Timeouts apropriados para operações longas (VACUUM)
- Cache de estatísticas quando possível

---

## 📝 Notas Importantes

1. **VACUUM FULL**: Bloqueia a tabela durante execução. Use apenas em horários de baixo tráfego.
2. **Kill All**: Encerra apenas queries idle in transaction >5min ou ativas >30min. Queries normais não são afetadas.
3. **Permissões**: Requer superuser ou permissões especiais no PostgreSQL.
4. **Monitoramento**: Use em conjunto com `DatabaseMaintenanceService` para alertas automáticos.

---

**Criado em**: 2026-01-18  
**Versão**: 1.0.0  
**Suporte**: Time de Infraestrutura
