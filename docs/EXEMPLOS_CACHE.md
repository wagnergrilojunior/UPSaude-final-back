# Exemplos de Uso do Cache Redis

Este documento contém exemplos práticos de como usar as anotações de cache do Spring nos services da aplicação.

## 📋 Índice

1. [@Cacheable - Cachear Resultados](#cacheable---cachear-resultados)
2. [@CacheEvict - Invalidar Cache](#cacheevict---invalidar-cache)
3. [@CachePut - Atualizar Cache](#cacheput---atualizar-cache)
4. [Casos de Uso Avançados](#casos-de-uso-avançados)

## @Cacheable - Cachear Resultados

### Exemplo Básico: Busca por ID

```java
@Service
@RequiredArgsConstructor
public class MeuService {

    private final MeuRepository repository;
    private final MeuMapper mapper;

    /**
     * Cacheia o resultado da busca por ID.
     * Na primeira chamada, busca no banco e cacheia.
     * Nas próximas chamadas, retorna do cache.
     */
    @Override
    @Transactional
    @Cacheable(value = "meu-cache", key = "#id")
    public MeuResponse obterPorId(UUID id) {
        log.debug("Buscando entidade por ID: {} (cache miss)", id);
        
        MeuEntity entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
        
        return mapper.toResponse(entity);
    }
}
```

### Exemplo com Múltiplos Parâmetros

```java
/**
 * Cacheia usando múltiplos parâmetros como chave.
 */
@Cacheable(value = "consultas", key = "#pacienteId + '_' + #data")
public ConsultaResponse buscarPorPacienteEData(UUID pacienteId, LocalDate data) {
    log.debug("Buscando consulta (cache miss)");
    
    Consulta consulta = consultaRepository.findByPacienteIdAndData(pacienteId, data)
        .orElseThrow(() -> new NotFoundException("Consulta não encontrada"));
    
    return consultaMapper.toResponse(consulta);
}
```

### Exemplo com Condição

```java
/**
 * Só cacheia se a entidade estiver ativa.
 */
@Cacheable(value = "entidades", key = "#id", condition = "#id != null")
public EntidadeResponse obterPorId(UUID id) {
    if (id == null) {
        throw new BadRequestException("ID é obrigatório");
    }
    
    Entidade entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
    
    return mapper.toResponse(entity);
}
```

## @CacheEvict - Invalidar Cache

### Invalidar Chave Específica (Update)

```java
/**
 * Ao atualizar, invalida apenas a chave específica do cache.
 */
@Override
@Transactional
@CacheEvict(value = "meu-cache", key = "#id")
public MeuResponse atualizar(UUID id, MeuRequest request) {
    log.debug("Atualizando entidade. ID: {}", id);
    
    MeuEntity entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
    
    // Atualiza os dados
    atualizarDados(entity, request);
    
    MeuEntity entityAtualizada = repository.save(entity);
    log.info("Entidade atualizada. Cache invalidado para ID: {}", id);
    
    return mapper.toResponse(entityAtualizada);
}
```

### Invalidar Todo o Cache (Create)

```java
/**
 * Ao criar, invalida todo o cache porque pode afetar listagens.
 */
@Override
@Transactional
@CacheEvict(value = "meu-cache", allEntries = true)
public MeuResponse criar(MeuRequest request) {
    log.debug("Criando nova entidade");
    
    MeuEntity entity = mapper.fromRequest(request);
    entity.setActive(true);
    
    MeuEntity entitySalva = repository.save(entity);
    log.info("Entidade criada. Cache completo invalidado");
    
    return mapper.toResponse(entitySalva);
}
```

### Invalidar Múltiplas Chaves (Delete)

```java
/**
 * Ao excluir, invalida a chave específica e também pode invalidar listagens.
 */
@Override
@Transactional
@CacheEvict(value = {"meu-cache", "meu-cache-lista"}, key = "#id")
public void excluir(UUID id) {
    log.debug("Excluindo entidade. ID: {}", id);
    
    MeuEntity entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
    
    entity.setActive(false);
    repository.save(entity);
    
    log.info("Entidade excluída. Cache invalidado para ID: {}", id);
}
```

### Invalidar com Condição

```java
/**
 * Só invalida o cache se a atualização for bem-sucedida.
 */
@CacheEvict(value = "meu-cache", key = "#id", condition = "#result != null")
public MeuResponse atualizar(UUID id, MeuRequest request) {
    MeuEntity entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
    
    atualizarDados(entity, request);
    
    MeuEntity entityAtualizada = repository.save(entity);
    
    // Se retornar null, o cache não será invalidado
    return mapper.toResponse(entityAtualizada);
}
```

## @CachePut - Atualizar Cache

### Atualizar Cache Após Criação

```java
/**
 * Cria a entidade E atualiza o cache com o resultado.
 */
@Override
@Transactional
@CachePut(value = "meu-cache", key = "#result.id")
public MeuResponse criar(MeuRequest request) {
    log.debug("Criando nova entidade");
    
    MeuEntity entity = mapper.fromRequest(request);
    entity.setActive(true);
    
    MeuEntity entitySalva = repository.save(entity);
    MeuResponse response = mapper.toResponse(entitySalva);
    
    // O cache será atualizado automaticamente com o resultado
    log.info("Entidade criada e cache atualizado. ID: {}", response.getId());
    
    return response;
}
```

### Atualizar Cache Após Atualização

```java
/**
 * Atualiza a entidade E atualiza o cache com o resultado.
 */
@Override
@Transactional
@CachePut(value = "meu-cache", key = "#id")
public MeuResponse atualizar(UUID id, MeuRequest request) {
    log.debug("Atualizando entidade. ID: {}", id);
    
    MeuEntity entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
    
    atualizarDados(entity, request);
    
    MeuEntity entityAtualizada = repository.save(entity);
    MeuResponse response = mapper.toResponse(entityAtualizada);
    
    // O cache será atualizado automaticamente com o resultado
    log.info("Entidade atualizada e cache atualizado. ID: {}", id);
    
    return response;
}
```

## Casos de Uso Avançados

### Combinando @CacheEvict e @CachePut

```java
/**
 * Invalida o cache antigo e atualiza com o novo valor.
 */
@Override
@Transactional
@CacheEvict(value = "meu-cache", key = "#id")
@CachePut(value = "meu-cache", key = "#id")
public MeuResponse atualizar(UUID id, MeuRequest request) {
    MeuEntity entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
    
    atualizarDados(entity, request);
    
    MeuEntity entityAtualizada = repository.save(entity);
    return mapper.toResponse(entityAtualizada);
}
```

### Cache com TTL Personalizado

Para usar TTL personalizado, você precisa criar configurações de cache específicas na `RedisConfig`:

```java
@Bean
public CacheManager cacheManager(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
    GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
    
    // Configuração padrão (5 minutos)
    RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(5))
        .prefixCacheNameWith("upsaude::")
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer))
        .disableCachingNullValues();
    
    // Configuração para cache de sessão (30 minutos)
    RedisCacheConfiguration sessionConfig = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(30))
        .prefixCacheNameWith("upsaude::sessao::")
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer))
        .disableCachingNullValues();
    
    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(defaultConfig)
        .withCacheConfiguration("sessoes", sessionConfig)
        .transactionAware()
        .build();
}
```

### Cache com Chave Composta

```java
/**
 * Exemplo: Cache de consultas por paciente e data.
 */
@Cacheable(value = "consultas", key = "T(java.util.UUID).fromString(#pacienteId.toString() + '-' + #data.toString())")
public ConsultaResponse buscarPorPacienteEData(UUID pacienteId, LocalDate data) {
    Consulta consulta = consultaRepository.findByPacienteIdAndData(pacienteId, data)
        .orElseThrow(() -> new NotFoundException("Consulta não encontrada"));
    
    return consultaMapper.toResponse(consulta);
}
```

### Cache de Listas (Cuidado!)

⚠️ **ATENÇÃO**: Cachear listas pode ser problemático porque:
- Listas mudam frequentemente
- Difícil invalidar quando um item é adicionado/removido
- Pode consumir muita memória

**Alternativa recomendada**: Cachear apenas buscas por ID individuais.

```java
// ❌ EVITE cachear listas diretamente
@Cacheable(value = "entidades-lista")
public Page<EntidadeResponse> listar(Pageable pageable) {
    // ...
}

// ✅ PREFIRA cachear buscas individuais
@Cacheable(value = "entidades", key = "#id")
public EntidadeResponse obterPorId(UUID id) {
    // ...
}
```

## 📝 Checklist para Implementar Cache

Ao adicionar cache em um novo service, siga este checklist:

- [ ] ✅ Método `obterPorId` tem `@Cacheable`
- [ ] ✅ Método `atualizar` tem `@CacheEvict` com `key = "#id"`
- [ ] ✅ Método `excluir` tem `@CacheEvict` com `key = "#id"`
- [ ] ✅ Método `criar` tem `@CacheEvict` com `allEntries = true` (ou `@CachePut`)
- [ ] ✅ Nome do cache é consistente em todas as anotações
- [ ] ✅ Logs indicam quando há cache miss (para debug)
- [ ] ✅ Dados são seguros para cachear (não são críticos ou muito dinâmicos)

## 🔍 Debugging

Para verificar se o cache está funcionando, adicione logs:

```java
@Cacheable(value = "meu-cache", key = "#id")
public MeuResponse obterPorId(UUID id) {
    // Este log só aparece quando há cache miss
    log.debug("🔴 CACHE MISS - Buscando no banco. ID: {}", id);
    
    MeuEntity entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Entidade não encontrada"));
    
    log.debug("✅ Dados buscados do banco e cacheados. ID: {}", id);
    return mapper.toResponse(entity);
}
```

Se você sempre ver o log "CACHE MISS", o cache não está funcionando. Verifique:
1. Redis está conectado?
2. `@EnableCaching` está habilitado?
3. O método está sendo chamado através do proxy Spring?

---

**Última atualização**: Dezembro 2024

