# Guia de Teste do Cache Redis

Este documento fornece instruções práticas para testar se o cache Redis está funcionando corretamente na aplicação.

## 📋 Pré-requisitos

### 1. Redis Instalado e Rodando

**Opção A: Docker (Recomendado para desenvolvimento local)**
```bash
docker run -d -p 6379:6379 --name upsaude-redis redis:alpine
```

**Opção B: Instalação Local**

**macOS:**
```bash
brew install redis
brew services start redis
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt-get update
sudo apt-get install redis-server
sudo systemctl start redis-server
```

**Verificar se Redis está rodando:**
```bash
redis-cli ping
# Deve retornar: PONG
```

### 2. Configurar Variáveis de Ambiente (Opcional)

Se você quiser usar configurações diferentes do padrão (`localhost:6379`), configure:

```bash
export REDIS_HOST=localhost
export REDIS_PORT=6379
export REDIS_PASSWORD=
export REDIS_DATABASE=0
```

## 🚀 Iniciar a Aplicação

```bash
# Usando Maven
mvn spring-boot:run -Dspring-boot.run.profiles=local

# Ou usando o JAR compilado
java -jar target/upsaude-back-1.0.0.jar --spring.profiles.active=local
```

## ✅ Testes Práticos

### Teste 1: Verificar Conexão com Redis

**1.1. Verificar logs na inicialização**

Ao iniciar a aplicação, procure por mensagens relacionadas ao Redis nos logs:

```
✅ Procurar por: "Redis" ou "Cache" nos logs
```

Se houver erro de conexão, você verá:
```
❌ Unable to connect to Redis
```

**1.2. Verificar via Redis CLI**

Em outro terminal, conecte-se ao Redis e verifique se há chaves:

```bash
redis-cli

# Listar todas as chaves do cache
KEYS upsaude::*

# Se houver chaves, o cache está funcionando!
```

### Teste 2: Testar Cache em Busca por ID

**2.1. Primeira Requisição (Cache Miss)**

Faça uma requisição GET para buscar um tenant por ID:

```bash
# Substitua {id} por um ID válido do seu banco
curl -X GET "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**Verificar nos logs:**
```
✅ Deve aparecer: "Buscando tenant por ID: {id} (cache miss)"
```

**2.2. Segunda Requisição (Cache Hit)**

Faça a mesma requisição novamente:

```bash
curl -X GET "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**Verificar nos logs:**
```
✅ NÃO deve aparecer: "(cache miss)"
✅ A resposta deve ser mais rápida
```

**2.3. Verificar no Redis**

```bash
redis-cli

# Verificar se a chave foi criada
KEYS upsaude::tenants::*

# Ver o conteúdo da chave
GET upsaude::tenants::{id}

# Ver o TTL (tempo restante)
TTL upsaude::tenants::{id}
```

### Teste 3: Testar Invalidação de Cache (Update)

**3.1. Buscar um registro**
```bash
curl -X GET "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**3.2. Atualizar o registro**
```bash
curl -X PUT "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Novo Nome"}'
```

**3.3. Verificar no Redis**
```bash
redis-cli

# A chave deve ter sido removida após o update
KEYS upsaude::tenants::{id}
# Deve retornar: (empty list or set)
```

**3.4. Buscar novamente**
```bash
curl -X GET "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**Verificar nos logs:**
```
✅ Deve aparecer: "(cache miss)" novamente
✅ O cache foi invalidado e recriado com os novos dados
```

### Teste 4: Testar Invalidação de Cache (Delete)

**4.1. Buscar um registro**
```bash
curl -X GET "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**4.2. Excluir o registro**
```bash
curl -X DELETE "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**4.3. Verificar no Redis**
```bash
redis-cli

# A chave deve ter sido removida após o delete
KEYS upsaude::tenants::{id}
# Deve retornar: (empty list or set)
```

### Teste 5: Testar TTL (Time To Live)

**5.1. Criar uma chave no cache**
```bash
curl -X GET "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**5.2. Verificar TTL**
```bash
redis-cli

# Ver o TTL restante (em segundos)
TTL upsaude::tenants::{id}

# Deve retornar um número próximo de 300 (5 minutos = 300 segundos)
```

**5.3. Aguardar expiração (opcional)**

Se quiser testar a expiração automática, aguarde 5 minutos e verifique:

```bash
redis-cli

# Após 5 minutos, a chave deve ter sido removida automaticamente
KEYS upsaude::tenants::{id}
# Deve retornar: (empty list or set)
```

### Teste 6: Testar Múltiplos Services

Teste o cache em diferentes services:

```bash
# Estados
curl -X GET "http://localhost:8080/api/estados/{id}" \
  -H "Authorization: Bearer {seu-token}"

# Cidades
curl -X GET "http://localhost:8080/api/cidades/{id}" \
  -H "Authorization: Bearer {seu-token}"

# Verificar no Redis
redis-cli
KEYS upsaude::*
# Deve mostrar chaves de diferentes caches
```

## 📊 Monitoramento de Performance

### Comparar Tempos de Resposta

**Sem Cache (primeira requisição):**
```bash
time curl -X GET "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**Com Cache (segunda requisição):**
```bash
time curl -X GET "http://localhost:8080/api/tenants/{id}" \
  -H "Authorization: Bearer {seu-token}"
```

**Resultado esperado:**
- Primeira requisição: ~100-500ms (dependendo do banco)
- Segunda requisição: ~10-50ms (do cache)

### Verificar Logs de Performance

Ative logs mais detalhados no `application-local.properties`:

```properties
logging.level.com.upsaude.service=DEBUG
logging.level.org.springframework.cache=DEBUG
```

## 🔍 Comandos Úteis do Redis

```bash
# Conectar ao Redis
redis-cli

# Listar todas as chaves do cache
KEYS upsaude::*

# Ver valor de uma chave específica
GET upsaude::tenants::{id}

# Ver TTL de uma chave
TTL upsaude::tenants::{id}

# Ver informações do servidor
INFO

# Limpar todo o cache (cuidado!)
FLUSHDB

# Contar número de chaves
DBSIZE

# Ver memória usada
INFO memory
```

## 🐛 Troubleshooting

### Problema: Cache não está funcionando

**Sintomas:**
- Sempre aparece "(cache miss)" nos logs
- Não há chaves no Redis
- Tempo de resposta não melhora

**Soluções:**

1. **Verificar se Redis está rodando:**
   ```bash
   redis-cli ping
   # Deve retornar: PONG
   ```

2. **Verificar configuração:**
   ```bash
   # Verificar variáveis de ambiente
   echo $REDIS_HOST
   echo $REDIS_PORT
   ```

3. **Verificar logs de erro:**
   ```
   Procure por: "Unable to connect to Redis" ou "Connection refused"
   ```

4. **Verificar se @EnableCaching está habilitado:**
   ```java
   // Deve estar em UpSaudeApplication.java
   @EnableCaching
   ```

### Problema: Dados desatualizados no cache

**Sintomas:**
- Após atualizar, ainda retorna dados antigos

**Soluções:**

1. **Verificar se @CacheEvict está nos métodos de update:**
   ```java
   @CacheEvict(value = "tenants", key = "#id")
   public TenantResponse atualizar(UUID id, TenantRequest request)
   ```

2. **Limpar cache manualmente:**
   ```bash
   redis-cli FLUSHDB
   ```

### Problema: Erro de conexão com Redis

**Sintomas:**
```
Unable to connect to Redis at localhost:6379
```

**Soluções:**

1. **Verificar se Redis está rodando:**
   ```bash
   redis-cli ping
   ```

2. **Verificar porta:**
   ```bash
   # Verificar se a porta 6379 está em uso
   lsof -i :6379
   ```

3. **Verificar firewall:**
   ```bash
   # Se estiver usando Docker, verificar se a porta está exposta
   docker ps | grep redis
   ```

## ✅ Checklist de Validação

- [ ] Redis está rodando e acessível
- [ ] Aplicação inicia sem erros relacionados ao Redis
- [ ] Primeira requisição mostra "(cache miss)" nos logs
- [ ] Segunda requisição NÃO mostra "(cache miss)"
- [ ] Chaves aparecem no Redis após primeira requisição
- [ ] Update invalida o cache (chave é removida)
- [ ] Delete invalida o cache (chave é removida)
- [ ] TTL está configurado corretamente (300 segundos)
- [ ] Tempo de resposta melhora após cache hit

## 📝 Exemplo de Teste Completo

```bash
# 1. Iniciar Redis (se não estiver rodando)
docker start upsaude-redis || docker run -d -p 6379:6379 --name upsaude-redis redis:alpine

# 2. Iniciar aplicação
mvn spring-boot:run -Dspring-boot.run.profiles=local

# 3. Em outro terminal, fazer requisições
export TOKEN="seu-token-aqui"
export ID="um-id-valido-do-banco"

# Primeira requisição (cache miss)
echo "=== Primeira Requisição (Cache Miss) ==="
time curl -X GET "http://localhost:8080/api/tenants/$ID" \
  -H "Authorization: Bearer $TOKEN" -w "\nTempo: %{time_total}s\n"

# Verificar no Redis
redis-cli KEYS "upsaude::tenants::*"

# Segunda requisição (cache hit)
echo "=== Segunda Requisição (Cache Hit) ==="
time curl -X GET "http://localhost:8080/api/tenants/$ID" \
  -H "Authorization: Bearer $TOKEN" -w "\nTempo: %{time_total}s\n"

# Atualizar (deve invalidar cache)
echo "=== Atualizando (Invalidando Cache) ==="
curl -X PUT "http://localhost:8080/api/tenants/$ID" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Nome Atualizado"}'

# Verificar que cache foi invalidado
redis-cli KEYS "upsaude::tenants::*"

# Terceira requisição (cache miss novamente)
echo "=== Terceira Requisição (Cache Miss após Update) ==="
time curl -X GET "http://localhost:8080/api/tenants/$ID" \
  -H "Authorization: Bearer $TOKEN" -w "\nTempo: %{time_total}s\n"
```

---

**Última atualização**: Dezembro 2024

