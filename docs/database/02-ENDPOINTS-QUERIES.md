# 🔍 Gerenciamento de Queries - Endpoints Detalhados

## Visão Geral

Endpoints para listar, monitorar e encerrar queries ativas no banco de dados.

---

## 1. Listar Queries Ativas/Represadas

### 📋 Informações Básicas

**Método**: `GET`  
**Endpoint**: `/api/v1/admin/database/queries`  
**Autenticação**: Bearer Token (qualquer usuário autenticado)

### 🎯 Propósito

Lista todas as queries em execução no banco de dados que estão rodando há mais de X minutos, incluindo informações detalhadas para diagnóstico.

### 📥 Parâmetros

| Nome | Tipo | Obrigatório | Padrão | Descrição |
|------|------|-------------|--------|-----------|
| `minutosMinimoLenta` | Integer | Não | 1 | Filtrar queries com duração mínima em minutos |

### 📤 Resposta de Sucesso (200 OK)

```json
{
  "total": 3,
  "filtro_minutos": 5,
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
      "query": "INSERT INTO sia_pa (competencia, uf, mes_movimentacao, ...) VALUES (...)"
    },
    {
      "pid": 12346,
      "duracao": "00:08:15.456789",
      "usuario": "postgres",
      "aplicacao": "pgAdmin",
      "ip_cliente": "192.168.1.105",
      "estado": "idle in transaction",
      "wait_event_type": null,
      "wait_event": null,
      "inicio": "2026-01-18T10:07:00Z",
      "ultima_mudanca_estado": "2026-01-18T10:07:05Z",
      "query": "BEGIN; UPDATE competencia_financeira SET status = 'FECHADA' WHERE id = '...'"
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

### 📊 Campos da Resposta Explicados

| Campo | Tipo | Descrição | Como Usar |
|-------|------|-----------|-----------|
| `pid` | Integer | **PID do processo** PostgreSQL | Use para matar a query específica |
| `duracao` | String | Tempo que a query está rodando | Se > 30 min, investigar |
| `usuario` | String | Usuário do banco que executou | Identificar origem |
| `aplicacao` | String | Nome da aplicação cliente | "UPSaude-API", "pgAdmin", etc |
| `ip_cliente` | String | IP do cliente conectado | Rastrear origem da conexão |
| `estado` | String | Estado da query | Ver tabela de estados abaixo |
| `wait_event_type` | String | Tipo de espera | Ver tabela de wait events abaixo |
| `wait_event` | String | Evento específico de espera | Indica o que está travando |
| `inicio` | DateTime | Quando a query começou | Para calcular tempo total |
| `ultima_mudanca_estado` | DateTime | Última vez que mudou de estado | Detectar queries paradas |
| `query` | String | Query SQL completa | Ver exatamente o que está fazendo |

---

### 🚦 Estados de Query (Campo `estado`)

| Estado | Significado | Risco | Ação |
|--------|-------------|-------|------|
| `active` | Query executando | Baixo se < 10min | Monitorar |
| `idle` | Conexão ociosa | Nenhum | Ignorar |
| `idle in transaction` | Transação aberta sem atividade | **Alto** | Matar se > 5min |
| `idle in transaction (aborted)` | Transação com erro | Médio | Matar |

**⚠️ ATENÇÃO**: `idle in transaction` é o mais perigoso! Indica possível bug no código que deixou transação aberta.

---

### 🔍 Wait Events (Campo `wait_event_type` e `wait_event`)

#### Lock (Esperando por Lock)

| wait_event | Significado | O Que Fazer |
|------------|-------------|-------------|
| `relation` | Aguardando lock na tabela | Identificar quem está segurando o lock |
| `tuple` | Aguardando lock em linha específica | UPDATE/DELETE concorrente |
| `transactionid` | Aguardando outra transação | Pode ser deadlock |

**Ação**: Ver endpoint `/locks` para identificar quem está segurando o lock.

#### IO (Esperando I/O de Disco)

| wait_event | Significado | O Que Fazer |
|------------|-------------|-------------|
| `DataFileRead` | Lendo do disco | Normal, mas se > 5min investigar |
| `WALWrite` | Escrevendo WAL | Normal em INSERTs grandes |

**Ação**: Verificar se disco está em 100% (usar monitoramento de servidor).

#### ClientRead (Esperando Cliente)

| wait_event | Significado | O Que Fazer |
|------------|-------------|-------------|
| `ClientRead` | Esperando cliente enviar dados | Cliente pode ter travado |

**Ação**: Matar query (cliente provavelmente desconectou).

---

## 📊 Exemplos Práticos

### Exemplo 1: Listar Todas Queries Ativas há Mais de 1 Minuto

**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=1" \
  -H "Authorization: Bearer eyJhbGc..." \
  -H "Content-Type: application/json"
```

**Interpretação**:
```json
{
  "total": 1,
  "queries": [{
    "pid": 23456,
    "duracao": "00:03:45",
    "estado": "active",
    "wait_event_type": null,
    "query": "SELECT * FROM sia_pa WHERE competencia = '202401'"
  }]
}
```

**Análise**:
- ✅ Query SELECT ativa há 3min45s
- ✅ Sem wait event (não está travada, apenas processando)
- ✅ **DECISÃO**: Deixar continuar, pode ser consulta legítima em tabela grande

---

### Exemplo 2: Identificar Query Travada com Lock

**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN"
```

**Resposta**:
```json
{
  "total": 2,
  "queries": [
    {
      "pid": 12345,
      "duracao": "00:45:23",
      "estado": "active",
      "wait_event_type": "Lock",
      "wait_event": "relation",
      "query": "INSERT INTO sia_pa ..."
    },
    {
      "pid": 12344,
      "duracao": "00:46:01",
      "estado": "active",
      "wait_event_type": "IO",
      "wait_event": "DataFileWrite",
      "query": "INSERT INTO sia_pa ..."
    }
  ]
}
```

**Análise**:
- ⚠️ PID 12345: Esperando lock há 45 minutos!
- ⚠️ PID 12344: Provavelmente segurando o lock
- **DECISÃO**: 
  1. Verificar locks (endpoint `/locks`)
  2. Considerar matar PID 12345 (está esperando)
  3. Avaliar se PID 12344 está progredindo

---

### Exemplo 3: Detectar Bug - idle in transaction

**Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN"
```

**Resposta**:
```json
{
  "queries": [{
    "pid": 98765,
    "duracao": "02:15:00",
    "estado": "idle in transaction",
    "aplicacao": "UPSaude-API",
    "ip_cliente": "10.0.1.50",
    "query": "BEGIN; UPDATE usuarios_sistema SET ultimo_acesso = now() WHERE id = '...'"
  }]
}
```

**Análise**:
- 🔴 **PROBLEMA CRÍTICO**: Transação aberta há 2h15min sem atividade!
- 🔴 Está segurando lock que está travando todo o sistema
- 🔴 Indica possível bug no código (faltou COMMIT ou ROLLBACK)

**DECISÃO IMEDIATA**: 
```bash
# Matar AGORA
DELETE /api/v1/admin/database/queries/98765
```

**AÇÃO PÓS-INCIDENTE**:
1. Reportar ao time de desenvolvimento
2. Investigar código que atualiza `ultimo_acesso`
3. Adicionar timeout em transações

---

## 🎯 Quando Usar Este Endpoint

### ✅ Use Para:

1. **Investigação de Lentidão**: Sistema está lento, ver o que está rodando
2. **Monitoramento Proativo**: Verificar a cada hora se há queries longas
3. **Diagnóstico de Travamento**: Sistema travou, identificar culpado
4. **Identificar Bugs**: Detectar `idle in transaction` recorrentes
5. **Análise de Padrões**: Ver quais queries mais demoram

### ⚠️ Cuidados:

1. **Não Execute a Cada Segundo**: Sobrecarrega o banco
2. **Filtro Adequado**: Use `minutosMinimoLenta >= 1` para evitar ruído
3. **Correlacione com Locks**: Use junto com `/locks` para diagnóstico completo

---

## 📈 Tempo de Decisão por Situação

| Situação | Tempo Rodando | Decisão | Prioridade |
|----------|---------------|---------|------------|
| `active` SELECT | < 5 min | ✅ Deixar | Baixa |
| `active` SELECT | 5-15 min | ⚠️ Investigar | Média |
| `active` SELECT | > 15 min | ⚠️ Considerar matar | Alta |
| `active` INSERT batch | < 30 min | ✅ Deixar | Baixa |
| `active` INSERT batch | 30-60 min | ⚠️ Verificar progresso | Média |
| `active` INSERT batch | > 60 min | 🔴 Provavelmente travado | Alta |
| `idle in transaction` | < 5 min | ⚠️ Monitorar | Média |
| `idle in transaction` | > 5 min | 🔴 Matar AGORA | Crítica |
| wait_event = Lock | > 10 min | 🔴 Investigar urgente | Crítica |

---

## 🔗 Próximos Passos

Depois de identificar queries problemáticas:

1. **Matar Query Específica**: Use `DELETE /api/v1/admin/database/queries/{pid}`
2. **Matar Todas Represadas**: Use `POST /api/v1/admin/database/queries/kill-all`
3. **Ver Locks**: Use `GET /api/v1/admin/database/locks`

---

**Próximo**: [Matar Queries](02-ENDPOINTS-QUERIES.md#matar-query-específica)
