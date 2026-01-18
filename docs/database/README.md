# 🔍 API de Monitoramento do Banco de Dados - Documentação Completa

**Versão**: 1.0.0  
**Data**: 2026-01-18  
**Última Atualização**: 2026-01-18

---

## 📚 Índice da Documentação

Esta documentação está organizada em 6 arquivos detalhados:

### 1. [Visão Geral](01-OVERVIEW.md) - **COMECE AQUI** ⭐
- Introdução e propósito da API
- Casos de uso reais
- Matriz de decisão: quando matar queries?
- Boas práticas e o que NÃO fazer
- Quando escalar para DBA/Desenvolvimento

**👉 Leia primeiro se é sua primeira vez**

---

### 2. [Listar Queries](02-ENDPOINTS-QUERIES.md)
- `GET /api/v1/admin/database/queries`
- Explicação de todos os campos retornados
- Estados de query (active, idle, idle in transaction)
- Wait events e o que significam
- Exemplos práticos de interpretação

**👉 Use para identificar queries problemáticas**

---

### 3. [Matar Queries](03-KILL-QUERIES.md)
- `DELETE /api/v1/admin/database/queries/{pid}`
- `POST /api/v1/admin/database/queries/kill-all`
- Quando é seguro matar queries
- Checklist de segurança
- Tempos recomendados por tipo de operação
- Exemplos de incidentes reais

**👉 Use quando precisar encerrar queries**

---

### 4. [Monitoramento de Saúde](04-HEALTH-MONITORING.md)
- `GET /api/v1/admin/database/health/dead-tuples`
- `GET /api/v1/admin/database/health/stats`
- `GET /api/v1/admin/database/health/largest-tables`
- Interpretação de dead tuples (saudável, atenção, crítico)
- Análise de estatísticas
- Checklist de monitoramento regular

**👉 Use para monitoramento proativo**

---

### 5. [Manutenção e VACUUM](05-MAINTENANCE-VACUUM.md)
- `POST /api/v1/admin/database/maintenance/vacuum/{schema}/{tabela}`
- `GET /api/v1/admin/database/locks`
- VACUUM vs VACUUM FULL (diferenças críticas)
- Tempos esperados por tamanho de tabela
- Alternativas ao VACUUM FULL
- Checklist antes de executar

**👉 Use para manutenção preventiva**

---

### 6. [Troubleshooting](06-TROUBLESHOOTING.md)
- Problemas comuns e soluções
- Sistema travado: diagnóstico e correção
- Queries lentas após import
- Disco cheio
- Import travando
- Fluxograma de troubleshooting
- Comandos de emergência (copiar/colar)

**👉 Use quando algo der errado**

---

## 🚀 Quick Start

### Cenário 1: Sistema Lento - Diagnóstico Rápido

```bash
# 1. Ver estatísticas gerais
curl -X GET "http://localhost:8080/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq

# 2. Ver queries longas (>5min)
curl -X GET "http://localhost:8080/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN" | jq

# 3. Ver dead tuples altos (>20%)
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

### Cenário 2: Sistema Travado - Solução de Emergência

```bash
# Matar TODAS queries represadas
curl -X POST "http://localhost:8080/api/v1/admin/database/queries/kill-all" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

### Cenário 3: Manutenção Semanal

```bash
# 1. Ver tabelas que precisam VACUUM
curl -X GET "http://localhost:8080/api/v1/admin/database/health/dead-tuples?percentualMinimo=15" \
  -H "Authorization: Bearer $TOKEN" | jq

# 2. Executar VACUUM em cada uma
curl -X POST "http://localhost:8080/api/v1/admin/database/maintenance/vacuum/public/sua_tabela" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

## 📋 Todos os Endpoints

| Método | Endpoint | Descrição | Arquivo Doc |
|--------|----------|-----------|-------------|
| GET | `/api/v1/admin/database/queries` | Listar queries ativas | [02-ENDPOINTS-QUERIES.md](02-ENDPOINTS-QUERIES.md) |
| DELETE | `/api/v1/admin/database/queries/{pid}` | Matar query específica | [03-KILL-QUERIES.md](03-KILL-QUERIES.md) |
| POST | `/api/v1/admin/database/queries/kill-all` | Matar todas represadas | [03-KILL-QUERIES.md](03-KILL-QUERIES.md) |
| GET | `/api/v1/admin/database/health/dead-tuples` | Dead tuples por tabela | [04-HEALTH-MONITORING.md](04-HEALTH-MONITORING.md) |
| GET | `/api/v1/admin/database/health/stats` | Estatísticas gerais | [04-HEALTH-MONITORING.md](04-HEALTH-MONITORING.md) |
| GET | `/api/v1/admin/database/health/largest-tables` | Maiores tabelas | [04-HEALTH-MONITORING.md](04-HEALTH-MONITORING.md) |
| POST | `/api/v1/admin/database/maintenance/vacuum/{schema}/{tabela}` | Executar VACUUM | [05-MAINTENANCE-VACUUM.md](05-MAINTENANCE-VACUUM.md) |
| GET | `/api/v1/admin/database/locks` | Listar locks ativos | [05-MAINTENANCE-VACUUM.md](05-MAINTENANCE-VACUUM.md) |

---

## 🎯 Guia por Situação

| Situação | Arquivo Recomendado |
|----------|---------------------|
| Sistema lento, não sei por quê | [01-OVERVIEW.md](01-OVERVIEW.md) |
| Preciso ver o que está rodando | [02-ENDPOINTS-QUERIES.md](02-ENDPOINTS-QUERIES.md) |
| Preciso matar queries | [03-KILL-QUERIES.md](03-KILL-QUERIES.md) |
| Monitoramento semanal | [04-HEALTH-MONITORING.md](04-HEALTH-MONITORING.md) |
| Executar VACUUM | [05-MAINTENANCE-VACUUM.md](05-MAINTENANCE-VACUUM.md) |
| Algo deu errado | [06-TROUBLESHOOTING.md](06-TROUBLESHOOTING.md) |

---

## 🔐 Segurança

**⚠️ IMPORTANTE**: Esta API tem restrição de acesso temporariamente desabilitada para testes.

**Antes de ir para Produção**:

1. Reativar segurança no arquivo:
   `src/main/java/com/upsaude/controller/admin/DatabaseHealthController.java`

2. Descomentar linha:
   ```java
   @PreAuthorize("hasRole('ADMIN_SISTEMA')")
   ```

3. Garantir que apenas administradores têm acesso.

---

## 📊 Estatísticas da Documentação

- **Total de Arquivos**: 6
- **Total de Linhas**: 2,525
- **Tamanho**: 72 KB
- **Exemplos Práticos**: 30+
- **Comandos Prontos**: 50+

---

## 🛠️ Ferramentas Complementares

### Swagger UI
- **URL**: http://localhost:8080/swagger-ui.html
- **Seção**: "Database Health"
- **Funcionalidade**: Testar endpoints interativamente

### Postman Collection
- **Localização**: `collections/v1/` (se disponível)
- **Funcionalidade**: Coleção pronta com todos os endpoints

---

## 🔗 Links Úteis

- **Código Fonte**: [DatabaseHealthController.java](../../src/main/java/com/upsaude/controller/admin/DatabaseHealthController.java)
- **Serviço de Monitoramento**: [DatabaseMaintenanceService.java](../../src/main/java/com/upsaude/service/impl/maintenance/DatabaseMaintenanceService.java)
- **Script SQL de Manutenção**: [vacuum_aggressive_config.sql](../../src/main/resources/db/maintenance/vacuum_aggressive_config.sql)
- **Relatório de Queries Travadas**: [RELATORIO_QUERIES_TRAVADAS_ANALISE_CORRECOES.md](../../RELATORIO_QUERIES_TRAVADAS_ANALISE_CORRECOES.md)

---

## 📞 Suporte

**Para Dúvidas**:
- Time de Infraestrutura
- DBA PostgreSQL

**Para Bugs**:
- Criar issue no repositório
- Tag: `database`, `monitoring`

---

**Desenvolvido com ❤️ pelo Time UPSaude**
