# 🚀 Comandos Prontos para Usar - cURL

## Setup Inicial

```bash
# Configure seu token uma vez
export TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
export BASE_URL="http://localhost:8080"

# Ou para produção
export BASE_URL="https://api.upsaude.com.br"
```

---

## 🔍 Monitoramento (Executar Regularmente)

### Ver Situação Geral (Dashboard)

```bash
curl -X GET "$BASE_URL/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

### Ver Queries Ativas há Mais de 1 Minuto

```bash
curl -X GET "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=1" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

### Ver Queries MUITO Lentas (>10 minutos)

```bash
curl -X GET "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=10" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.queries[] | {pid, duracao, estado, wait_event, query: (.query | .[0:100])}'
```

---

### Ver Dead Tuples Problemáticos (>20%)

```bash
curl -X GET "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

### Ver Top 10 Maiores Tabelas

```bash
curl -X GET "$BASE_URL/api/v1/admin/database/health/largest-tables?limite=10" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.tabelas[] | {tabela, tamanho, registros_vivos, dead_ratio_pct}'
```

---

### Ver Locks Ativos

```bash
curl -X GET "$BASE_URL/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.locks[] | {pid, tabela, modo, concedido, duracao}'
```

---

## ❌ Matar Queries (Emergência)

### Matar Query Específica

```bash
# Substitua 12345 pelo PID da query
PID=12345

curl -X DELETE "$BASE_URL/api/v1/admin/database/queries/$PID" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

### Matar TODAS Queries Represadas (EMERGÊNCIA!)

```bash
# ⚠️  ATENÇÃO: Use apenas em emergências!
curl -X POST "$BASE_URL/api/v1/admin/database/queries/kill-all" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

## 🧹 Manutenção

### VACUUM Normal em Tabela Específica

```bash
# Substitua TABELA pelo nome da tabela
TABELA="estados"

curl -X POST "$BASE_URL/api/v1/admin/database/maintenance/vacuum/public/$TABELA" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

### VACUUM FULL (Apenas Madrugada!)

```bash
# ⚠️  BLOQUEIA A TABELA!
TABELA="competencia_financeira"

curl -X POST "$BASE_URL/api/v1/admin/database/maintenance/vacuum/public/$TABELA?full=true" \
  -H "Authorization: Bearer $TOKEN" | jq
```

---

### VACUUM em Todas Tabelas com >20% Dead Tuples

```bash
# Script completo
curl -s -X GET "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" \
  -H "Authorization: Bearer $TOKEN" \
  | jq -r '.tabelas[].tabela' \
  | while read tabela; do
      echo "VACUUM em $tabela..."
      curl -X POST "$BASE_URL/api/v1/admin/database/maintenance/vacuum/public/$tabela" \
        -H "Authorization: Bearer $TOKEN" | jq '.sucesso'
      sleep 30
    done
```

---

## 📊 Monitoramento Automático (Agendar)

### Script de Monitoramento Diário

```bash
#!/bin/bash
# Salvar como: check_database_health.sh
# Executar: cron "0 9 * * * /path/to/check_database_health.sh"

export TOKEN="seu_token"
export BASE_URL="http://localhost:8080"

echo "=== Database Health Check - $(date) ===" >> /var/log/db_health.log

# 1. Ver queries longas
QUERIES=$(curl -s -X GET "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=10" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.total')

echo "Queries longas (>10min): $QUERIES" >> /var/log/db_health.log

if [ "$QUERIES" -gt 0 ]; then
  echo "⚠️  ALERTA: Queries longas detectadas!" >> /var/log/db_health.log
  # Enviar alerta (Slack, email, etc)
fi

# 2. Ver dead tuples críticos
DEAD=$(curl -s -X GET "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=50" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.total')

echo "Tabelas com >50% dead tuples: $DEAD" >> /var/log/db_health.log

if [ "$DEAD" -gt 0 ]; then
  echo "⚠️  ALERTA: Dead tuples críticos!" >> /var/log/db_health.log
fi

# 3. Ver idle in transaction
IDLE_TX=$(curl -s -X GET "$BASE_URL/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.conexoes.idle_in_transaction')

echo "Conexões idle in transaction: $IDLE_TX" >> /var/log/db_health.log

if [ "$IDLE_TX" -gt 0 ]; then
  echo "🔴 CRÍTICO: Idle in transaction detectado!" >> /var/log/db_health.log
fi

echo "===========================================\n" >> /var/log/db_health.log
```

---

### Script de VACUUM Semanal

```bash
#!/bin/bash
# Salvar como: weekly_vacuum.sh
# Executar: cron "0 2 * * 1 /path/to/weekly_vacuum.sh"  # Segunda 2h

export TOKEN="seu_token"
export BASE_URL="http://localhost:8080"

echo "=== Weekly VACUUM - $(date) ===" >> /var/log/db_vacuum.log

# Buscar tabelas com >15% dead tuples
TABELAS=$(curl -s -X GET "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=15" \
  -H "Authorization: Bearer $TOKEN" \
  | jq -r '.tabelas[].tabela')

# VACUUM em cada uma
for tabela in $TABELAS; do
  echo "VACUUM em $tabela..." >> /var/log/db_vacuum.log
  
  RESULTADO=$(curl -s -X POST "$BASE_URL/api/v1/admin/database/maintenance/vacuum/public/$tabela" \
    -H "Authorization: Bearer $TOKEN" \
    | jq -r '.sucesso')
  
  echo "  Resultado: $RESULTADO" >> /var/log/db_vacuum.log
  
  sleep 30
done

echo "VACUUM semanal concluído\n" >> /var/log/db_vacuum.log
```

---

## 🆘 Comandos de Emergência

### Sistema Completamente Travado

```bash
# PASSO 1: Ver situação
curl -X GET "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=1" \
  -H "Authorization: Bearer $TOKEN" | jq '.total'

# PASSO 2: Matar todas (se > 10 queries)
curl -X POST "$BASE_URL/api/v1/admin/database/queries/kill-all" \
  -H "Authorization: Bearer $TOKEN" | jq

# PASSO 3: Verificar recuperação
curl -X GET "$BASE_URL/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq '.conexoes'
```

---

### Query Específica Travando Tudo

```bash
# PASSO 1: Identificar culpado
curl -X GET "$BASE_URL/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.locks[] | select(.concedido==true and .duracao > "00:10:00")'

# PASSO 2: Matar o culpado (substitua PID)
PID=12345
curl -X DELETE "$BASE_URL/api/v1/admin/database/queries/$PID" \
  -H "Authorization: Bearer $TOKEN" | jq

# PASSO 3: Verificar locks liberados
curl -X GET "$BASE_URL/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" | jq '.total'
```

---

## 📊 Scripts Úteis

### Monitorar Query Específica em Loop

```bash
# Monitorar se query ainda está rodando
PID=12345

while true; do
  STATUS=$(curl -s -X GET "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=0" \
    -H "Authorization: Bearer $TOKEN" \
    | jq ".queries[] | select(.pid==$PID) | .duracao")
  
  if [ -z "$STATUS" ]; then
    echo "Query $PID finalizou!"
    break
  fi
  
  echo "$(date): Query $PID ainda rodando - Duração: $STATUS"
  sleep 30
done
```

---

### Gerar Relatório de Saúde Completo

```bash
#!/bin/bash
# Gerar relatório HTML

REPORT_FILE="db_health_$(date +%Y%m%d_%H%M%S).html"

cat > $REPORT_FILE <<EOF
<!DOCTYPE html>
<html>
<head>
  <title>Database Health Report</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    table { border-collapse: collapse; width: 100%; margin: 20px 0; }
    th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    th { background-color: #4CAF50; color: white; }
    .critical { background-color: #ffcccc; }
    .warning { background-color: #ffffcc; }
    .ok { background-color: #ccffcc; }
  </style>
</head>
<body>
  <h1>Database Health Report - $(date)</h1>
  
  <h2>Estatísticas Gerais</h2>
  <pre>
$(curl -s -X GET "$BASE_URL/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq)
  </pre>
  
  <h2>Queries Ativas (>5min)</h2>
  <pre>
$(curl -s -X GET "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN" | jq)
  </pre>
  
  <h2>Dead Tuples (>20%)</h2>
  <pre>
$(curl -s -X GET "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" \
  -H "Authorization: Bearer $TOKEN" | jq)
  </pre>
  
  <h2>Top 10 Maiores Tabelas</h2>
  <pre>
$(curl -s -X GET "$BASE_URL/api/v1/admin/database/health/largest-tables?limite=10" \
  -H "Authorization: Bearer $TOKEN" | jq)
  </pre>
  
  <h2>Locks Ativos</h2>
  <pre>
$(curl -s -X GET "$BASE_URL/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" | jq)
  </pre>
</body>
</html>
EOF

echo "Relatório gerado: $REPORT_FILE"
open $REPORT_FILE  # macOS
# xdg-open $REPORT_FILE  # Linux
```

---

## 🔄 Rotinas de Manutenção

### Rotina Diária (9h)

```bash
#!/bin/bash
# daily_check.sh

echo "=== Checagem Diária - $(date) ==="

# 1. Queries longas?
LONG_QUERIES=$(curl -s "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=15" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.total')

echo "Queries >15min: $LONG_QUERIES"

# 2. idle in transaction?
IDLE_TX=$(curl -s "$BASE_URL/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.conexoes.idle_in_transaction')

echo "Idle in transaction: $IDLE_TX"

if [ "$IDLE_TX" -gt 0 ]; then
  echo "🔴 AÇÃO: Matando idle in transaction..."
  curl -X POST "$BASE_URL/api/v1/admin/database/queries/kill-all" \
    -H "Authorization: Bearer $TOKEN" | jq -r '.sucessos'
fi

# 3. Dead tuples críticos?
CRITICAL=$(curl -s "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=70" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.total')

echo "Tabelas críticas (>70%): $CRITICAL"

if [ "$CRITICAL" -gt 0 ]; then
  echo "⚠️  ALERTA: Tabelas precisam VACUUM urgente!"
fi
```

---

### Rotina Semanal (Segunda 2h)

```bash
#!/bin/bash
# weekly_maintenance.sh

echo "=== Manutenção Semanal - $(date) ==="

# 1. Listar tabelas que precisam VACUUM
TABELAS=$(curl -s "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=15" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.tabelas[].tabela')

echo "Tabelas para VACUUM: $(echo $TABELAS | wc -w)"

# 2. VACUUM em cada uma
for tabela in $TABELAS; do
  echo "VACUUM em $tabela..."
  
  START=$(date +%s)
  RESULTADO=$(curl -s -X POST "$BASE_URL/api/v1/admin/database/maintenance/vacuum/public/$tabela" \
    -H "Authorization: Bearer $TOKEN" | jq -r '.sucesso')
  END=$(date +%s)
  DURATION=$((END - START))
  
  echo "  Sucesso: $RESULTADO (${DURATION}s)"
  
  sleep 30
done

echo "Manutenção semanal concluída"
```

---

## 🔍 Investigação de Problemas

### Investigar Query Lenta Específica

```bash
# Encontrar query que está demorando
PID=12345

# Ver detalhes completos
curl -X GET "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=0" \
  -H "Authorization: Bearer $TOKEN" \
  | jq ".queries[] | select(.pid==$PID)"

# Ver se está esperando lock
curl -X GET "$BASE_URL/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" \
  | jq ".locks[] | select(.pid==$PID)"

# Se estiver esperando, ver quem está segurando
curl -X GET "$BASE_URL/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" \
  | jq '.locks[] | select(.concedido==true and .tabela!="null") | {pid, tabela, modo, duracao}'
```

---

### Encontrar Causa de Sistema Lento

```bash
echo "=== Diagnóstico de Lentidão ==="

# 1. Queries ativas
echo "1. Queries ativas (>5min):"
curl -s "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=5" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.total'

# 2. Dead tuples
echo "2. Dead tuples altos (>30%):"
curl -s "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=30" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.total'

# 3. Locks
echo "3. Locks ativos:"
curl -s "$BASE_URL/api/v1/admin/database/locks" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.total'

# 4. Conexões
echo "4. Conexões:"
curl -s "$BASE_URL/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq '.conexoes'
```

---

## 💾 Exportar Dados para Análise

### Exportar Queries Ativas para JSON

```bash
curl -X GET "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=1" \
  -H "Authorization: Bearer $TOKEN" \
  > queries_$(date +%Y%m%d_%H%M%S).json

echo "Queries exportadas para queries_*.json"
```

---

### Exportar Dead Tuples para CSV

```bash
curl -s -X GET "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=0" \
  -H "Authorization: Bearer $TOKEN" \
  | jq -r '.tabelas[] | [.tabela, .dead_tuples, .live_tuples, .dead_ratio_pct, .tamanho] | @csv' \
  > dead_tuples_$(date +%Y%m%d).csv

echo "Dead tuples exportados para dead_tuples_*.csv"
```

---

## 🎯 One-Liners Úteis

### Ver Apenas Total de Queries Longas

```bash
curl -s "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=10" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.total'
```

---

### Ver Tabela com Mais Dead Tuples

```bash
curl -s "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=0" \
  -H "Authorization: Bearer $TOKEN" \
  | jq -r '.tabelas[0] | "\(.tabela): \(.dead_ratio_pct)%"'
```

---

### Ver Se Tem idle in transaction

```bash
curl -s "$BASE_URL/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" \
  | jq -r '.conexoes.idle_in_transaction'
```

---

### Ver Maior Tabela

```bash
curl -s "$BASE_URL/api/v1/admin/database/health/largest-tables?limite=1" \
  -H "Authorization: Bearer $TOKEN" \
  | jq -r '.tabelas[0] | "\(.tabela): \(.tamanho)"'
```

---

## 📱 Integração com Slack/Discord

### Enviar Alerta para Slack

```bash
# Quando detectar problema
WEBHOOK_URL="https://hooks.slack.com/services/..."

IDLE_TX=$(curl -s "$BASE_URL/api/v1/admin/database/health/stats" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.conexoes.idle_in_transaction')

if [ "$IDLE_TX" -gt 0 ]; then
  curl -X POST $WEBHOOK_URL \
    -H 'Content-Type: application/json' \
    -d "{\"text\":\"🔴 ALERTA: $IDLE_TX conexões idle in transaction detectadas!\"}"
fi
```

---

## 🔄 Aliases Úteis (Adicionar no .bashrc/.zshrc)

```bash
# Adicionar no seu .bashrc ou .zshrc
alias dbq='curl -s "$BASE_URL/api/v1/admin/database/queries?minutosMinimoLenta=5" -H "Authorization: Bearer $TOKEN" | jq'
alias dbstats='curl -s "$BASE_URL/api/v1/admin/database/health/stats" -H "Authorization: Bearer $TOKEN" | jq'
alias dbdead='curl -s "$BASE_URL/api/v1/admin/database/health/dead-tuples?percentualMinimo=20" -H "Authorization: Bearer $TOKEN" | jq'
alias dbkillall='curl -X POST "$BASE_URL/api/v1/admin/database/queries/kill-all" -H "Authorization: Bearer $TOKEN" | jq'
alias dbtables='curl -s "$BASE_URL/api/v1/admin/database/health/largest-tables?limite=10" -H "Authorization: Bearer $TOKEN" | jq'
```

**Uso**:
```bash
# Depois de configurar os aliases
dbq          # Ver queries ativas
dbstats      # Ver estatísticas
dbdead       # Ver dead tuples
dbkillall    # Matar todas (CUIDADO!)
dbtables     # Ver maiores tabelas
```

---

**Fim dos Comandos Prontos**

Para documentação completa, veja [README.md](README.md)
