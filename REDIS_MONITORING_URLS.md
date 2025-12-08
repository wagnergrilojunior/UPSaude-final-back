# URLs de Monitoramento do Redis no Spring Boot Actuator

## URLs Principais para Observabilidade do Redis

Com o `context-path=/api`, todas as URLs devem incluir o prefixo `/api`:

### 1. **Health Check do Redis**
```
GET /api/actuator/health/redis
```
**Descrição**: Verifica se o Redis está disponível e respondendo.  
**Resposta esperada**: `{"status":"UP"}` ou `{"status":"DOWN"}`  
**Status HTTP**: 200 (UP) ou 503 (DOWN)

### 2. **Lista Todas as Métricas Disponíveis**
```
GET /api/actuator/metrics
```
**Descrição**: Retorna lista de todas as métricas disponíveis (incluindo Redis).  
**Use isto para**: Descobrir quais métricas de Redis estão disponíveis.

### 3. **Métricas Específicas do Redis/Lettuce**

#### Métricas de Comandos Lettuce:
```
GET /api/actuator/metrics/lettuce.command
GET /api/actuator/metrics/lettuce.commands
GET /api/actuator/metrics/lettuce.commands.active
GET /api/actuator/metrics/lettuce.commands.completed
GET /api/actuator/metrics/lettuce.commands.rejected
```

#### Métricas de Latência:
```
GET /api/actuator/metrics/lettuce.command.duration
```

#### Métricas de Pool de Conexões:
```
GET /api/actuator/metrics/lettuce.pool.active
GET /api/actuator/metrics/lettuce.pool.idle
GET /api/actuator/metrics/lettuce.pool.pending
```

### 4. **Métricas de Cache Spring**

#### Lista todos os caches:
```
GET /api/actuator/metrics/cache.gets
GET /api/actuator/metrics/cache.puts
GET /api/actuator/metrics/cache.evictions
GET /api/actuator/metrics/cache.size
```

#### Métricas específicas por nome de cache:
```
GET /api/actuator/metrics/cache.gets?tag=cache:SEU_NOME_CACHE
GET /api/actuator/metrics/cache.puts?tag=cache:SEU_NOME_CACHE
```

**Nota**: Métricas de cache só aparecem após o cache ser usado pela primeira vez.  
O `CacheInitializer` foi criado para inicializar os caches durante startup.

### 5. **Prometheus (Formato Exportável)**
```
GET /api/actuator/prometheus
```
**Descrição**: Retorna todas as métricas no formato Prometheus.  
**Use isto para**: Integração com Prometheus/Grafana.

## URLs que Podem Retornar 404

Os seguintes endpoints podem retornar 404 até que sejam usados pela primeira vez:

- `/api/actuator/metrics/cache.*` - Se nenhum cache foi usado ainda
- `/api/actuator/metrics/lettuce.*` - Se nenhum comando Redis foi executado ainda
- Métricas específicas de cache por nome - Se aquele cache específico não foi usado

**Solução**: O `CacheInitializer` inicializa os caches durante startup para evitar 404s.

## Verificação Rápida de Funcionamento

### 1. Verificar se Redis está conectado:
```bash
curl https://api.upsaude.wgbsolucoes.com.br/api/actuator/health/redis
```

### 2. Verificar métricas disponíveis:
```bash
curl https://api.upsaude.wgbsolucoes.com.br/api/actuator/metrics | grep -i redis
curl https://api.upsaude.wgbsolucoes.com.br/api/actuator/metrics | grep -i lettuce
curl https://api.upsaude.wgbsolucoes.com.br/api/actuator/metrics | grep -i cache
```

### 3. Verificar métricas específicas:
```bash
# Métricas de comandos Lettuce
curl https://api.upsaude.wgbsolucoes.com.br/api/actuator/metrics/lettuce.command

# Métricas de cache
curl https://api.upsaude.wgbsolucoes.com.br/api/actuator/metrics/cache.gets
```

## Troubleshooting

### Se `/api/actuator/health/redis` retornar DOWN:
1. Verificar variáveis de ambiente no Render:
   - `REDIS_HOST`
   - `REDIS_PORT`
   - `REDIS_PASSWORD`
2. Verificar logs da aplicação para erros de conexão
3. Verificar se o Redis/Valkey está rodando no Render

### Se métricas retornarem 404:
1. Verificar se `management.metrics.enable.redis=true` está configurado
2. Verificar se `management.metrics.enable.lettuce=true` está configurado
3. Verificar se `management.metrics.enable.cache=true` está configurado
4. Verificar se o `CacheInitializer` está executando durante startup
5. Verificar logs para mensagens de inicialização de cache

## Configurações Necessárias

No `application-prod.properties`:
```properties
# Habilitar métricas
management.metrics.enable.redis=true
management.metrics.enable.lettuce=true
management.metrics.enable.cache=true
management.metrics.enable.spring-data=true

# Health check do Redis
management.health.redis.enabled=true
management.health.redis.timeout=1000ms
```
