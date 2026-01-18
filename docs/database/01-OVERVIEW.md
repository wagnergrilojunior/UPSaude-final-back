# 🔍 API de Monitoramento do Banco de Dados - Visão Geral

## Introdução

Esta API REST fornece ferramentas completas para monitorar, diagnosticar e gerenciar a saúde do banco de dados PostgreSQL do sistema UPSaude.

---

## 🎯 Propósito

### Para Que Serve?

1. **Monitoramento Proativo**: Identificar queries lentas antes que causem problemas
2. **Diagnóstico Rápido**: Entender o que está travando o banco
3. **Intervenção Imediata**: Matar queries problemáticas sem acessar o servidor
4. **Manutenção Preventiva**: Identificar e limpar dead tuples
5. **Análise de Performance**: Entender uso de recursos e crescimento de tabelas

---

## 📊 Casos de Uso Reais

### Cenário 1: Sistema Lento Durante Import de SIA-PA
**Sintoma**: Aplicação travando durante importação de arquivos grandes.

**Diagnóstico**:
```bash
GET /api/v1/admin/database/queries?minutosMinimoLenta=5
```

**Resultado**: Query INSERT em `sia_pa` há 45 minutos consumindo lock.

**Ação**:
```bash
DELETE /api/v1/admin/database/queries/12345
```

**Resultado**: Sistema volta ao normal imediatamente.

---

### Cenário 2: Dead Tuples Causando Lentidão
**Sintoma**: Queries simples demorando muito.

**Diagnóstico**:
```bash
GET /api/v1/admin/database/health/dead-tuples?percentualMinimo=30
```

**Resultado**: Tabela `competencia_financeira` com 92% de dead tuples.

**Ação**:
```bash
POST /api/v1/admin/database/maintenance/vacuum/public/competencia_financeira
```

**Resultado**: Performance melhora 80%.

---

### Cenário 3: Banco Travado com Múltiplas Queries
**Sintoma**: Sistema completamente travado, timeouts constantes.

**Diagnóstico**:
```bash
GET /api/v1/admin/database/queries?minutosMinimoLenta=10
```

**Resultado**: 15 queries idle in transaction há horas.

**Ação**:
```bash
POST /api/v1/admin/database/queries/kill-all
```

**Resultado**: 15 queries encerradas, sistema volta ao normal.

---

## 🚦 Matriz de Decisão: Quando Matar Queries?

### ⚠️ ATENÇÃO: Use com Cuidado!

Matar queries pode causar:
- ❌ Perda de dados não commitados
- ❌ Rollback de transações longas
- ❌ Interrupção de imports em andamento
- ❌ Inconsistências se a query estava fazendo update crítico

---

### 🟢 SEGURO MATAR (Baixo Risco)

| Situação | Tempo | Risco | Ação Recomendada |
|----------|-------|-------|------------------|
| `idle in transaction` | > 5 min | Baixo | ✅ Pode matar |
| `SELECT` simples | > 10 min | Baixo | ✅ Pode matar |
| Query de relatório | > 15 min | Baixo | ✅ Pode matar |
| `idle` (não in transaction) | Qualquer | Nenhum | ✅ Pode desconectar |

**Justificativa**: 
- `idle in transaction` significa que a transação está aberta mas não fazendo nada (possível bug no código)
- SELECTs não modificam dados, são seguros de matar
- Queries de relatório podem ser reexecutadas

---

### 🟡 AVALIAR COM CUIDADO (Médio Risco)

| Situação | Tempo | Risco | Ação Recomendada |
|----------|-------|-------|------------------|
| `INSERT` de import | > 30 min | Médio | ⚠️ Verificar se travado |
| `UPDATE` em lote | > 20 min | Médio | ⚠️ Verificar wait events |
| `CREATE INDEX` | > 1 hora | Médio | ⚠️ Deixar terminar se possível |
| Migração Flyway | > 10 min | Alto | ⚠️ Só matar se certeza que travou |

**Justificativa**:
- Pode estar simplesmente processando muito dado (não travado)
- Matar pode deixar dados inconsistentes
- Reexecutar pode demorar muito

**Como Decidir**:
1. Ver `wait_event`: Se for "Lock" ou "IO" por muito tempo, pode estar travado
2. Ver CPU: Se estiver 0%, provavelmente travou
3. Ver progresso: Se `pg_stat_progress_*` mostra progresso, deixar continuar

---

### 🔴 NÃO MATAR (Alto Risco)

| Situação | Tempo | Risco | Ação Recomendada |
|----------|-------|-------|------------------|
| `VACUUM FULL` | Qualquer | Alto | ❌ NUNCA matar |
| `ALTER TABLE` | Qualquer | Alto | ❌ NUNCA matar |
| Backup em andamento | Qualquer | Crítico | ❌ NUNCA matar |
| Replicação | Qualquer | Crítico | ❌ NUNCA matar |

**Justificativa**:
- Pode corromper tabelas
- Pode deixar banco em estado inconsistente
- Pode perder backup completo

---

## 📈 Tempos de Referência por Operação

### Operações Normais (Não Deve Travar)

| Operação | Tempo Normal | Tempo Preocupante | Ação |
|----------|--------------|-------------------|------|
| SELECT simples | < 1 segundo | > 30 segundos | Investigar query |
| INSERT individual | < 100ms | > 5 segundos | Verificar índices |
| UPDATE individual | < 500ms | > 10 segundos | Verificar locks |
| DELETE individual | < 500ms | > 10 segundos | Verificar locks |
| VACUUM normal | 1-5 min | > 30 min | Normal em tabelas grandes |

### Operações Pesadas (Pode Demorar)

| Operação | Tempo Normal | Tempo Preocupante | Ação |
|----------|--------------|-------------------|------|
| Batch INSERT (1000 reg) | 5-30 segundos | > 5 minutos | Verificar wait events |
| Import SIA-PA (1M reg) | 10-30 minutos | > 2 horas | Pode estar travado |
| VACUUM FULL | 10-60 minutos | > 2 horas | Deixar terminar |
| CREATE INDEX | 5-30 minutos | > 2 horas | Depende do tamanho |
| Migração Flyway | 1-10 minutos | > 30 minutos | Verificar DDL lock |

---

## 🛡️ Boas Práticas

### ✅ DO (Faça)

1. **Monitore Regularmente**: Verifique queries ativas a cada hora
2. **Documente Ações**: Sempre registre PID e query antes de matar
3. **Mate Queries Seguras Primeiro**: Comece com `idle in transaction`
4. **Use kill-all Com Critério**: Só use em emergências
5. **Execute VACUUM Fora do Horário de Pico**: Prefira madrugada
6. **Monitore Dead Tuples**: Verifique semanalmente

### ❌ DON'T (Não Faça)

1. **Não Mate Queries Cegamente**: Sempre veja o que está fazendo
2. **Não Execute VACUUM FULL em Produção**: Bloqueia a tabela
3. **Não Mate Queries de Backup**: Pode corromper backup
4. **Não Ignore Wait Events**: Eles indicam o problema real
5. **Não Mate Queries Rapidamente**: Dê tempo (1-2 min) para ver se termina
6. **Não Esqueça de Avisar o Time**: Comunique ações críticas

---

## 📞 Quando Escalar

### Escalar para DBA/Infraestrutura Se:

1. ❌ Queries continuam travando após matar
2. ❌ Dead tuples continuam altos após VACUUM
3. ❌ Banco completamente travado (não responde)
4. ❌ Múltiplas queries travadas repetidamente
5. ❌ Disk I/O em 100% constante
6. ❌ Locks não liberados após kill

### Escalar para Desenvolvimento Se:

1. 🐛 Mesma query trava repetidamente
2. 🐛 Query específica sempre lenta
3. 🐛 Queries `idle in transaction` constantes (possível bug)
4. 🐛 Locks em cascata (deadlock pattern)

---

## 📚 Estrutura da Documentação

Esta documentação está organizada em:

1. **01-OVERVIEW.md** (este arquivo) - Visão geral e casos de uso
2. **02-ENDPOINTS-QUERIES.md** - Gerenciamento de queries
3. **03-ENDPOINTS-HEALTH.md** - Monitoramento de saúde
4. **04-ENDPOINTS-MAINTENANCE.md** - Manutenção e VACUUM
5. **05-TROUBLESHOOTING.md** - Resolução de problemas comuns
6. **06-EXEMPLOS-PRATICOS.md** - Exemplos reais passo a passo

---

## 🔗 Links Rápidos

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Endpoint Base**: `/api/v1/admin/database`
- **Código Fonte**: `src/main/java/com/upsaude/controller/admin/DatabaseHealthController.java`

---

**Última Atualização**: 2026-01-18  
**Versão da API**: 1.0.0  
**Suporte**: Time de Infraestrutura
