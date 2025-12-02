# Configuração do Redis Cache

Este documento explica como configurar e usar o Redis como cache na aplicação UP Saúde.

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Configuração no Render](#configuração-no-render)
3. [Configuração Local](#configuração-local)
4. [Como Usar Cache nos Services](#como-usar-cache-nos-services)
5. [Exemplos Práticos](#exemplos-práticos)
6. [Monitoramento e Troubleshooting](#monitoramento-e-troubleshooting)

## 🎯 Visão Geral

O Redis foi integrado à aplicação para melhorar o desempenho através de cache. A configuração inclui:

- **TTL padrão**: 5 minutos (300.000 ms)
- **Serialização**: JSON com suporte a tipos Java modernos
- **Prefixo de chave**: `upsaude::`
- **Cliente**: Lettuce (assíncrono e thread-safe)

## ⚙️ Configuração no Render

### Passo 1: Criar Instância Redis no Render

1. Acesse o [Render Dashboard](https://dashboard.render.com)
2. Clique em **"New +"** → **"Redis"**
3. Configure:
   - **Name**: `upsaude-redis` (ou o nome que preferir)
   - **Plan**: Escolha o plano adequado (Free tier disponível para desenvolvimento)
   - **Region**: Escolha a mesma região da sua aplicação

### Passo 2: Obter Credenciais do Redis

Após criar a instância, você verá:
- **Internal Redis URL**: `redis://red-xxxxx:6379`
- **Redis Host**: `red-xxxxx.render.com` (ou IP interno)
- **Redis Port**: `6379`
- **Redis Password**: (se configurado)

### Passo 3: Configurar Variáveis de Ambiente no Render

Na sua aplicação Spring Boot no Render, adicione as seguintes variáveis de ambiente:

#### Para Ambiente de Desenvolvimento (dev)

```
REDIS_HOST=red-xxxxx.render.com
REDIS_PORT=6379
REDIS_PASSWORD=sua_senha_aqui
REDIS_DATABASE=0
```

#### Para Ambiente de Produção (prod)

```
REDIS_HOST=red-xxxxx.render.com
REDIS_PORT=6379
REDIS_PASSWORD=sua_senha_aqui
REDIS_DATABASE=0
```

**⚠️ IMPORTANTE**: 
- Se o Redis estiver na mesma rede privada do Render, use o **hostname interno** (ex: `red-xxxxx`)
- Se estiver usando Redis externo, use o **hostname público** ou IP
- A senha é obrigatória em produção

### Passo 4: Verificar Configuração

As propriedades já estão configuradas nos arquivos `application-dev.properties` e `application-prod.properties`:

```properties
spring.redis.host=${REDIS_HOST:localhost}
spring.redis.port=${REDIS_PORT:6379}
spring.redis.password=${REDIS_PASSWORD:}
spring.redis.database=${REDIS_DATABASE:0}
```

As variáveis de ambiente do Render sobrescreverão os valores padrão automaticamente.

## 🖥️ Configuração Local

Para desenvolvimento local, você pode usar Docker:

```bash
docker run -d -p 6379:6379 --name upsaude-redis redis:alpine
```

Ou instalar Redis diretamente:

**macOS**:
```bash
brew install redis
brew services start redis
```

**Linux (Ubuntu/Debian)**:
```bash
sudo apt-get update
sudo apt-get install redis-server
sudo systemctl start redis-server
```

**Windows**:
Use WSL2 ou Docker Desktop.

O arquivo `application-local.properties` já está configurado para usar `localhost:6379` por padrão.

## 💻 Como Usar Cache nos Services

### 1. @Cacheable - Cachear Resultados

Use `@Cacheable` em métodos que **buscam** dados e podem ser cacheados:

```java
@Override
@Transactional
@Cacheable(value = "nome-do-cache", key = "#id")
public EntidadeResponse obterPorId(UUID id) {
    log.debug("Buscando entidade por ID: {} (cache miss)", id);
    
    Entidade entidade = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
    
    return mapper.toResponse(entidade);
}
```

**Parâmetros importantes**:
- `value`: Nome do cache (ex: "tenants", "estados", "cidades")
- `key`: Chave única no cache (geralmente `#id` ou `#parametro`)

### 2. @CacheEvict - Invalidar Cache

Use `@CacheEvict` em métodos que **modificam** dados:

#### Invalidar uma chave específica:
```java
@Override
@Transactional
@CacheEvict(value = "nome-do-cache", key = "#id")
public EntidadeResponse atualizar(UUID id, EntidadeRequest request) {
    // ... lógica de atualização ...
    return mapper.toResponse(entidadeAtualizada);
}
```

#### Invalidar todo o cache:
```java
@Override
@Transactional
@CacheEvict(value = "nome-do-cache", allEntries = true)
public EntidadeResponse criar(EntidadeRequest request) {
    // ... lógica de criação ...
    return mapper.toResponse(entidadeCriada);
}
```

### 3. @CachePut - Atualizar Cache

Use `@CachePut` quando quiser atualizar o cache após uma operação:

```java
@Override
@Transactional
@CachePut(value = "nome-do-cache", key = "#result.id")
public EntidadeResponse criar(EntidadeRequest request) {
    // ... lógica de criação ...
    EntidadeResponse response = mapper.toResponse(entidadeCriada);
    return response; // O resultado será cacheado automaticamente
}
```

## 📚 Exemplos Práticos

### Exemplo 1: Busca por ID (TenantService)

```java
@Override
@Transactional
@Cacheable(value = "tenants", key = "#id")
public TenantResponse obterPorId(UUID id) {
    log.debug("Buscando tenant por ID: {} (cache miss)", id);
    
    Tenant tenant = tenantRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Tenant não encontrado"));
    
    return tenantMapper.toResponse(tenant);
}
```

### Exemplo 2: Atualização com Invalidação (EstadosService)

```java
@Override
@Transactional
@CacheEvict(value = "estados", key = "#id")
public EstadosResponse atualizar(UUID id, EstadosRequest request) {
    log.debug("Atualizando estado. ID: {}", id);
    
    Estados estado = estadosRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Estado não encontrado"));
    
    // ... atualização ...
    
    Estados estadoAtualizado = estadosRepository.save(estado);
    return estadosMapper.toResponse(estadoAtualizado);
}
```

### Exemplo 3: Exclusão com Invalidação (CidadesService)

```java
@Override
@Transactional
@CacheEvict(value = "cidades", key = "#id")
public void excluir(UUID id) {
    log.debug("Excluindo cidade. ID: {}", id);
    
    Cidades cidade = cidadesRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Cidade não encontrada"));
    
    cidade.setActive(false);
    cidadesRepository.save(cidade);
}
```

### Exemplo 4: Criação com Invalidação de Todo o Cache

```java
@Override
@Transactional
@CacheEvict(value = "estados", allEntries = true)
public EstadosResponse criar(EstadosRequest request) {
    log.debug("Criando novo estado");
    
    Estados estado = estadosMapper.fromRequest(request);
    estado.setActive(true);
    
    Estados estadoSalvo = estadosRepository.save(estado);
    return estadosMapper.toResponse(estadoSalvo);
}
```

## 🔍 Onde Aplicar Cache

### ✅ **SEGURO** aplicar cache em:

1. **Buscas por ID** - Dados raramente mudam
   ```java
   @Cacheable(value = "entidades", key = "#id")
   public EntidadeResponse obterPorId(UUID id)
   ```

2. **Tabelas de domínio estáticas** - Estados, Cidades, Especialidades Médicas
   ```java
   @Cacheable(value = "estados", key = "#id")
   public EstadosResponse obterPorId(UUID id)
   ```

3. **Configurações de tenant/empresa** - Dados que mudam raramente
   ```java
   @Cacheable(value = "tenants", key = "#id")
   public TenantResponse obterPorId(UUID id)
   ```

4. **Dados de sessão do usuário** - Se aplicável
   ```java
   @Cacheable(value = "sessoes", key = "#userId")
   public SessaoResponse obterSessao(UUID userId)
   ```

### ❌ **NÃO** aplicar cache em:

1. **Dados transacionais críticos** - Prontuários, Consultas em andamento
2. **Dados que mudam frequentemente** - Estoque em tempo real
3. **Dados sensíveis que precisam de auditoria imediata** - Logs de auditoria
4. **Queries complexas com filtros dinâmicos** - Listagens paginadas com muitos filtros

## 📊 Monitoramento e Troubleshooting

### Verificar Conexão com Redis

Adicione logs na inicialização para verificar se o Redis está conectado:

```java
@PostConstruct
public void verificarConexaoRedis() {
    try {
        redisConnectionFactory.getConnection().ping();
        log.info("✅ Redis conectado com sucesso!");
    } catch (Exception e) {
        log.error("❌ Erro ao conectar com Redis: {}", e.getMessage());
    }
}
```

### Comandos Redis Úteis

Conecte-se ao Redis via CLI:

```bash
# Se estiver usando Docker localmente
docker exec -it upsaude-redis redis-cli

# Se estiver usando Redis remoto
redis-cli -h red-xxxxx.render.com -p 6379 -a sua_senha
```

**Comandos úteis**:

```redis
# Listar todas as chaves do cache
KEYS upsaude::*

# Ver valor de uma chave específica
GET upsaude::tenants::550e8400-e29b-41d4-a716-446655440000

# Ver TTL de uma chave
TTL upsaude::tenants::550e8400-e29b-41d4-a716-446655440000

# Limpar todo o cache
FLUSHDB

# Ver informações do servidor
INFO
```

### Troubleshooting Comum

#### Problema: Cache não está funcionando

**Soluções**:
1. Verifique se `@EnableCaching` está na classe principal
2. Verifique se as variáveis de ambiente do Redis estão configuradas
3. Verifique os logs da aplicação para erros de conexão
4. Certifique-se de que o método está sendo chamado através do proxy Spring (não diretamente)

#### Problema: Dados desatualizados no cache

**Soluções**:
1. Verifique se `@CacheEvict` está nos métodos de update/delete
2. Verifique se o TTL está adequado (5 minutos por padrão)
3. Considere usar `allEntries = true` em criações se necessário

#### Problema: Erro de conexão com Redis

**Soluções**:
1. Verifique se o Redis está rodando
2. Verifique host, porta e senha
3. Verifique firewall/rede no Render
4. Use o hostname interno se estiver na mesma rede privada

## 🚀 Otimização da JVM com G1GC

A aplicação já está configurada para usar G1GC através da variável `JAVA_TOOL_OPTIONS` no Render:

```
JAVA_TOOL_OPTIONS=-XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Xmx512m -Xms256m
```

Isso ajuda a reduzir pausas de GC e melhorar o desempenho geral.

## 📝 Resumo das Configurações

| Propriedade | Valor Padrão | Variável de Ambiente |
|------------|--------------|----------------------|
| Host | localhost | `REDIS_HOST` |
| Porta | 6379 | `REDIS_PORT` |
| Senha | (vazio) | `REDIS_PASSWORD` |
| Database | 0 | `REDIS_DATABASE` |
| TTL | 300000ms (5 min) | `spring.cache.redis.time-to-live` |
| Prefixo | `upsaude::` | `spring.cache.redis.key-prefix` |

## 🔗 Referências

- [Spring Cache Abstraction](https://docs.spring.io/spring-framework/reference/integration/cache.html)
- [Spring Data Redis](https://docs.spring.io/spring-data/redis/docs/current/reference/html/)
- [Render Redis Documentation](https://render.com/docs/redis)

---

**Última atualização**: Dezembro 2024

