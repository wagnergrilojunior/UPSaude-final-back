#!/bin/bash

# Script de teste do cache Redis
# Uso: ./test_cache.sh [token] [id]

set -e

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configurações
API_URL="http://localhost:8080/api"
TOKEN="${1:-seu-token-aqui}"
ID="${2:-um-id-valido}"

echo -e "${GREEN}=== Teste de Cache Redis ===${NC}\n"

# Verificar se Redis está rodando
echo -e "${YELLOW}1. Verificando conexão com Redis...${NC}"
if redis-cli ping > /dev/null 2>&1; then
    echo -e "${GREEN}✅ Redis está rodando${NC}\n"
else
    echo -e "${RED}❌ Redis não está rodando!${NC}"
    echo "Inicie o Redis com: docker run -d -p 6379:6379 --name upsaude-redis redis:alpine"
    exit 1
fi

# Limpar cache antes do teste
echo -e "${YELLOW}2. Limpando cache existente...${NC}"
redis-cli FLUSHDB > /dev/null 2>&1
echo -e "${GREEN}✅ Cache limpo${NC}\n"

# Primeira requisição (cache miss)
echo -e "${YELLOW}3. Primeira requisição (deve criar cache)...${NC}"
TIME1=$(curl -s -o /dev/null -w "%{time_total}" -X GET "$API_URL/tenants/$ID" \
    -H "Authorization: Bearer $TOKEN" 2>/dev/null || echo "0")
echo -e "Tempo de resposta: ${GREEN}${TIME1}s${NC}"

# Verificar se chave foi criada
KEYS=$(redis-cli KEYS "upsaude::tenants::*" 2>/dev/null | wc -l | tr -d ' ')
if [ "$KEYS" -gt 0 ]; then
    echo -e "${GREEN}✅ Cache criado (encontradas $KEYS chave(s))${NC}\n"
else
    echo -e "${RED}❌ Cache não foi criado${NC}\n"
fi

# Segunda requisição (cache hit)
echo -e "${YELLOW}4. Segunda requisição (deve usar cache)...${NC}"
TIME2=$(curl -s -o /dev/null -w "%{time_total}" -X GET "$API_URL/tenants/$ID" \
    -H "Authorization: Bearer $TOKEN" 2>/dev/null || echo "0")
echo -e "Tempo de resposta: ${GREEN}${TIME2}s${NC}"

# Comparar tempos
if (( $(echo "$TIME2 < $TIME1" | bc -l) )); then
    SPEEDUP=$(echo "scale=2; $TIME1 / $TIME2" | bc)
    echo -e "${GREEN}✅ Cache funcionando! ${SPEEDUP}x mais rápido${NC}\n"
else
    echo -e "${YELLOW}⚠️  Tempo similar - verifique os logs${NC}\n"
fi

# Verificar TTL
echo -e "${YELLOW}5. Verificando TTL do cache...${NC}"
TTL=$(redis-cli TTL "upsaude::tenants::$ID" 2>/dev/null || echo "-1")
if [ "$TTL" -gt 0 ]; then
    MINUTES=$((TTL / 60))
    SECONDS=$((TTL % 60))
    echo -e "${GREEN}✅ TTL: ${MINUTES}m ${SECONDS}s${NC}\n"
else
    echo -e "${RED}❌ TTL não encontrado${NC}\n"
fi

# Teste de invalidação (update)
echo -e "${YELLOW}6. Testando invalidação de cache (update)...${NC}"
curl -s -o /dev/null -X PUT "$API_URL/tenants/$ID" \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d '{"nome": "Teste Cache"}' > /dev/null 2>&1 || true

# Verificar se cache foi invalidado
KEYS_AFTER=$(redis-cli KEYS "upsaude::tenants::$ID" 2>/dev/null | wc -l | tr -d ' ')
if [ "$KEYS_AFTER" -eq 0 ]; then
    echo -e "${GREEN}✅ Cache invalidado corretamente${NC}\n"
else
    echo -e "${RED}❌ Cache não foi invalidado${NC}\n"
fi

# Resumo
echo -e "${GREEN}=== Resumo do Teste ===${NC}"
echo "Primeira requisição: ${TIME1}s"
echo "Segunda requisição: ${TIME2}s"
if (( $(echo "$TIME2 < $TIME1" | bc -l) )); then
    echo -e "${GREEN}Status: Cache funcionando corretamente!${NC}"
else
    echo -e "${YELLOW}Status: Verifique os logs da aplicação${NC}"
fi

