# ❌ Matar Queries - Guia Completo

## Visão Geral

Guia detalhado sobre como e quando encerrar queries no banco de dados de forma segura.

---

## 2. Matar Query Específica

### 📋 Informações Básicas

**Método**: `DELETE`  
**Endpoint**: `/api/v1/admin/database/queries/{pid}`  
**Autenticação**: Bearer Token

### 🎯 Propósito

Encerra uma query específica pelo PID do processo PostgreSQL.

### 📥 Parâmetros

| Nome | Tipo | Obrigatório | Descrição |
|------|------|-------------|-----------|
| `pid` | Integer | Sim (path) | PID do processo a ser encerrado |

### 📤 Resposta de Sucesso (200 OK)

```json
{
  "pid": 12345,
  "sucesso": true,
  "mensagem": "Query encerrada com sucesso",
  "timestamp": "2026-01-18T10:15:32Z"
}
```

### 📤 Resposta de Falha (200 OK com sucesso: false)

```json
{
  "pid": 99999,
  "sucesso": false,
  "mensagem": "Falha ao encerrar query (PID pode não existir)",
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

## 📊 Exemplo Prático Completo

### Cenário: Query INSERT Travada há 1 Hora

#### Passo 1: Identificar a Query

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=30" \
  -H "Authorization: Bearer $TOKEN" | jq
```

**Resposta**:
```json
{
  "queries": [{
    "pid": 54321,
    "duracao": "01:15:00",
    "estado": "active",
    "wait_event_type": "Lock",
    "wait_event": "relation",
    "usuario": "postgres",
    "aplicacao": "UPSaude-API",
    "query": "INSERT INTO sia_pa (competencia, uf, ...) VALUES (...)"
  }]
}
```

#### Passo 2: Analisar a Situação

**Checklist de Decisão**:
- ✅ Tempo: 1h15min (muito tempo para INSERT)
- ✅ Wait Event: "Lock" + "relation" (está travada esperando lock)
- ✅ Estado: "active" (não está progredindo)
- ✅ Aplicação: "UPSaude-API" (não é migração ou backup)

**DECISÃO**: ✅ **PODE MATAR**

**Justificativa**:
- Query está claramente travada (não progredindo)
- Esperando lock há mais de 1 hora
- INSERT pode ser reexecutado
- Está travando outros processos

#### Passo 3: Matar a Query

```bash
curl -X DELETE "http://localhost:8080/api/v1/admin/database/queries/54321" \
  -H "Authorization: Bearer $TOKEN" | jq
```

**Resposta**:
```json
{
  "pid": 54321,
  "sucesso": true,
  "mensagem": "Query encerrada com sucesso",
  "timestamp": "2026-01-18T11:30:00Z"
}
```

#### Passo 4: Verificar Resultado

```bash
# Verificar se outras queries liberaram
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN" | jq '.total'
```

**Resultado Esperado**: Número de queries deve ter diminuído.

#### Passo 5: Documentar

**Template de Registro**:
```
Data/Hora: 2026-01-18 11:30:00
PID Encerrado: 54321
Query: INSERT INTO sia_pa ...
Duração: 1h15min
Motivo: Travada com Lock por >1h
Responsável: [Seu Nome]
Impacto: Sistema voltou ao normal
Ação Preventiva: Investigar cause do lock
```

---

## 🎯 Matriz de Decisão Detalhada

### Cenário 1: SELECT de Relatório Lento

**Situação**:
```json
{
  "pid": 11111,
  "duracao": "00:18:30",
  "estado": "active",
  "wait_event_type": null,
  "query": "SELECT e.*, u.*, a.* FROM estabelecimentos e JOIN ... WHERE data BETWEEN ..."
}
```

**Análise**:
- ✅ Tempo: 18min30s
- ⚠️ Wait Event: Nenhum (está processando)
- ✅ Query: SELECT complexo (não modifica dados)

**DECISÃO**: ⚠️ **CONSIDERAR MATAR**

**Checklist Antes de Matar**:
1. ☐ É horário de pico? (Se sim, matar)
2. ☐ Consulta é frequente? (Se sim, otimizar query)
3. ☐ Usuário está esperando? (Se sim, avisar que vai demorar)

**Como Matar**:
```bash
# Se decidir matar
curl -X DELETE "http://localhost:8080/api/v1/admin/database/queries/11111" \
  -H "Authorization: Bearer $TOKEN"
```

**Ação Pós-Incidente**:
- Reportar ao desenvolvedor que a query precisa otimização
- Adicionar índice se necessário
- Considerar materializar view se consulta é frequente

---

### Cenário 2: idle in transaction - BUG NO CÓDIGO

**Situação**:
```json
{
  "pid": 22222,
  "duracao": "00:12:00",
  "estado": "idle in transaction",
  "aplicacao": "UPSaude-API",
  "ip_cliente": "10.0.1.100",
  "query": "BEGIN; UPDATE usuarios_sistema SET ..."
}
```

**Análise**:
- 🔴 Tempo: 12 minutos
- 🔴 Estado: **idle in transaction** (CRÍTICO!)
- 🔴 Query: UPDATE (pode estar segurando lock)

**DECISÃO**: 🔴 **MATAR IMEDIATAMENTE**

**Justificativa**:
- Transação aberta há 12 minutos sem atividade = bug no código
- Está segurando locks que estão travando outros usuários
- Dados não serão perdidos (não commitou ainda)

**Como Matar**:
```bash
curl -X DELETE "http://localhost:8080/api/v1/admin/database/queries/22222" \
  -H "Authorization: Bearer $TOKEN"
```

**Ação OBRIGATÓRIA Pós-Incidente**:
1. **Criar issue para desenvolvimento**: "Transação deixada aberta em UsuariosSistemaService"
2. **Identificar código**: IP 10.0.1.100 → qual servidor/instância?
3. **Adicionar timeout**: Configurar `idle_in_transaction_session_timeout`
4. **Monitorar**: Verificar se problema se repete

---

### Cenário 3: VACUUM FULL - NÃO MATAR!

**Situação**:
```json
{
  "pid": 33333,
  "duracao": "00:45:00",
  "estado": "active",
  "wait_event_type": "IO",
  "wait_event": "DataFileWrite",
  "query": "VACUUM FULL ANALYZE public.sia_pa"
}
```

**Análise**:
- ⚠️ Tempo: 45 minutos
- ✅ Wait Event: "IO" (escrevendo dados, progredindo)
- 🔴 Query: **VACUUM FULL** (operação crítica)

**DECISÃO**: ❌ **NÃO MATAR - DEIXAR TERMINAR**

**Justificativa**:
- VACUUM FULL é operação crítica de manutenção
- Matar pode corromper tabela
- Está progredindo (IO ativo)
- 45 minutos é normal para tabela de 7GB

**O Que Fazer**:
- ✅ Deixar terminar
- ✅ Avisar usuários que manutenção está em andamento
- ✅ Agendar para madrugada próxima vez
- ❌ NÃO MATAR!

---

## 3. Matar TODAS Queries Represadas

### 📋 Informações Básicas

**Método**: `POST`  
**Endpoint**: `/api/v1/admin/database/queries/kill-all`  
**Autenticação**: Bearer Token

### 🎯 Propósito

Encerra automaticamente TODAS as queries que atendam critérios de "represada":
- `idle in transaction` há mais de 5 minutos, OU
- `active` há mais de 30 minutos

### ⚠️ ATENÇÃO: Use com EXTREMO Cuidado!

Este endpoint é para **EMERGÊNCIAS** quando o banco está completamente travado.

### 📤 Resposta de Sucesso (200 OK)

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
      "query_preview": "BEGIN; UPDATE competencia_financeira ...",
      "encerrada": true
    },
    {
      "pid": 12346,
      "duracao": "00:45:30",
      "estado": "active",
      "query_preview": "INSERT INTO sia_pa ...",
      "encerrada": true
    }
  ],
  "timestamp": "2026-01-18T10:15:32Z"
}
```

---

## 📊 Exemplo de Uso - Emergência

### Cenário: Sistema Completamente Travado

**Sintomas**:
- Aplicação não responde
- Timeouts em todas as requisições
- Usuários reportando sistema fora do ar

#### Passo 1: Verificar Situação

```bash
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN" | jq '.total'
```

**Resultado**: `20` (20 queries represadas!)

#### Passo 2: Avaliar Criticidade

```bash
# Ver detalhes
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN" | jq '.queries[] | {pid, duracao, estado}'
```

**Resultado**:
```json
[
  {"pid": 100, "duracao": "02:30:00", "estado": "idle in transaction"},
  {"pid": 101, "duracao": "02:15:00", "estado": "idle in transaction"},
  {"pid": 102, "duracao": "01:45:00", "estado": "idle in transaction"},
  ... (17 mais)
]
```

**Análise**:
- 🔴 20 queries travadas
- 🔴 15 são `idle in transaction` (bug crítico!)
- 🔴 Maior tempo: 2h30min
- 🔴 Sistema completamente travado

**DECISÃO**: 🔴 **USO DE KILL-ALL JUSTIFICADO**

#### Passo 3: Executar Kill-All

```bash
# ATENÇÃO: Confirme que é emergência!
curl -X POST "http://localhost:8080/api/v1/admin/database/queries/kill-all" \
  -H "Authorization: Bearer $TOKEN" | jq
```

**Resposta**:
```json
{
  "total_encontradas": 20,
  "sucessos": 18,
  "falhas": 2,
  "queries_encerradas": [...]
}
```

#### Passo 4: Verificar Recuperação

```bash
# Verificar se sistema voltou
curl -X GET "http://localhost:8080/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq '.conexoes'
```

**Resultado Esperado**:
```json
{
  "total": 5,
  "ativas": 2,
  "idle": 3,
  "idle_in_transaction": 0
}
```

✅ Sistema recuperado!

#### Passo 5: Pós-Incidente

**Ações Obrigatórias**:

1. **Documentar o Incidente**:
```markdown
## Incidente: Sistema Travado - 2026-01-18 10:15

**Situação**: 20 queries represadas, 15 idle in transaction
**Ação**: Kill-all executado
**Resultado**: 18 de 20 encerradas, sistema recuperado
**Responsável**: [Seu Nome]
**Causa Raiz**: Bug no código deixando transações abertas
**Prevenção**: Adicionar timeout + revisar código
```

2. **Escalar para Desenvolvimento**:
- Reportar bug que deixa transações abertas
- Solicitar revisão de código de gerenciamento de transações

3. **Configurar Prevenção**:
```sql
-- Adicionar timeout de transações idle
ALTER DATABASE upsaude SET idle_in_transaction_session_timeout = '5min';
```

4. **Monitoramento Reforçado**:
- Configurar alerta para idle in transaction > 2 minutos
- Executar monitoramento a cada 30 minutos

---

## 🚦 Quando NÃO Usar Kill-All

### ❌ Não Use Se:

1. **Menos de 5 Queries Represadas**: Mate individualmente
2. **Queries de Manutenção**: VACUUM, reindex, backup
3. **Horário de Import**: Aguarde término do batch
4. **Sistema Respondendo**: Não é emergência real

### ⚠️ Use Com Cuidado Se:

1. **Horário Comercial**: Impacto em usuários ativos
2. **Imports em Andamento**: Pode perder progresso
3. **Sem Backup Recente**: Risco de perda de dados

---

## 📋 Checklist de Segurança Antes de Matar

### Para Query Individual:

- [ ] Identifiquei o PID correto?
- [ ] Vi qual query está rodando?
- [ ] Verifiquei o tempo de execução?
- [ ] Confirmei que não é VACUUM/backup/migração?
- [ ] Documentei a decisão?
- [ ] Avisei o time?

### Para Kill-All (EMERGÊNCIA):

- [ ] Sistema está REALMENTE travado?
- [ ] Tentei matar queries individuais primeiro?
- [ ] Avisei o time que vou executar?
- [ ] Tenho backup recente?
- [ ] Documentei a situação ANTES de executar?
- [ ] Preparei plano de rollback se der errado?

---

## 🎯 Tempos Recomendados

| Tipo de Query | Tempo Normal | Investigar Após | Matar Após |
|---------------|--------------|-----------------|------------|
| SELECT simples | < 1s | 30s | 2min |
| SELECT complexo | < 30s | 5min | 15min |
| INSERT individual | < 100ms | 5s | 30s |
| INSERT batch (1k) | 10-30s | 2min | 10min |
| UPDATE individual | < 500ms | 10s | 1min |
| UPDATE batch | 1-5min | 10min | 30min |
| idle in transaction | NUNCA | 1min | **5min** |

---

**Próximo**: [Monitoramento de Saúde](04-HEALTH-MONITORING.md)
