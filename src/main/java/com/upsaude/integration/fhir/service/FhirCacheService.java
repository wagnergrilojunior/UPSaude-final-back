package com.upsaude.integration.fhir.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.config.FhirProperties;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FhirCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final FhirProperties fhirProperties;

    private static final String CACHE_PREFIX = "fhir::";

    @Autowired
    public FhirCacheService(
            @Autowired(required = false) RedisTemplate<String, Object> redisTemplate,
            ObjectMapper objectMapper,
            FhirProperties fhirProperties) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.fhirProperties = fhirProperties;
    }

    private boolean isRedisAvailable() {
        return redisTemplate != null && fhirProperties.getCache().isEnabled();
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        if (!isRedisAvailable()) {
            return Optional.empty();
        }

        try {
            String fullKey = CACHE_PREFIX + key;
            Object cached = redisTemplate.opsForValue().get(fullKey);

            if (cached == null) {
                log.debug("Cache miss para: {}", key);
                return Optional.empty();
            }

            log.debug("Cache hit para: {}", key);

            if (cached instanceof String) {
                return Optional.of(objectMapper.readValue((String) cached, type));
            }

            String json = objectMapper.writeValueAsString(cached);
            return Optional.of(objectMapper.readValue(json, type));

        } catch (Exception e) {
            log.warn("Erro ao ler do cache: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public void put(String key, Object value) {
        if (!isRedisAvailable() || value == null) {
            return;
        }

        try {
            String fullKey = CACHE_PREFIX + key;
            String json = objectMapper.writeValueAsString(value);
            Duration ttl = Duration.ofHours(fhirProperties.getCache().getTtlHours());

            redisTemplate.opsForValue().set(fullKey, json, ttl);
            log.debug("Valor armazenado no cache: {} (TTL: {}h)", key, fhirProperties.getCache().getTtlHours());

        } catch (JsonProcessingException e) {
            log.warn("Erro ao serializar para cache: {}", e.getMessage());
        }
    }

    public void evict(String key) {
        if (!isRedisAvailable()) {
            return;
        }
        String fullKey = CACHE_PREFIX + key;
        Boolean deleted = redisTemplate.delete(fullKey);
        if (Boolean.TRUE.equals(deleted)) {
            log.debug("Cache evicted: {}", key);
        }
    }

    public void evictByPattern(String pattern) {
        if (!isRedisAvailable()) {
            return;
        }
        try {
            var keys = redisTemplate.keys(CACHE_PREFIX + pattern + "*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("Evicted {} chaves do cache FHIR com pattern: {}", keys.size(), pattern);
            }
        } catch (Exception e) {
            log.warn("Erro ao evict por pattern: {}", e.getMessage());
        }
    }

    public void evictAll() {
        evictByPattern("");
    }

    public boolean isEnabled() {
        return isRedisAvailable();
    }
}
