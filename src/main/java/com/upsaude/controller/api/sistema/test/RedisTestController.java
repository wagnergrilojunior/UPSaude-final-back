package com.upsaude.controller.api.sistema.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/test/redis")
@Tag(name = "Teste Redis", description = "Endpoints para testar e diagnosticar o Redis")
@Profile("!local")
@ConditionalOnBean(RedisConnectionFactory.class)
@Slf4j
public class RedisTestController {

    private final RedisConnectionFactory redisConnectionFactory;
    private final CacheManager cacheManager;

    public RedisTestController(RedisConnectionFactory redisConnectionFactory, CacheManager cacheManager) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/health")
    @Operation(summary = "Verificar saúde do Redis", description = "Testa a conexão com o Redis e retorna informações sobre o status")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();

        try {

            RedisConnection connection = redisConnectionFactory.getConnection();
            String pong = connection.ping();
            connection.close();

            response.put("status", "OK");
            response.put("ping", pong);
            response.put("timestamp", LocalDateTime.now());
            response.put("message", "Redis está funcionando corretamente");

            log.info("✅ Teste de saúde do Redis: OK - Ping: {}", pong);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ Erro ao testar Redis: {}", e.getMessage(), e);
            response.put("status", "ERROR");
            response.put("error", e.getMessage());
            response.put("timestamp", LocalDateTime.now());
            response.put("message", "Redis não está disponível");

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }

    @PostMapping("/test-cache")
    @Operation(summary = "Testar operações de cache", description = "Testa operações de escrita e leitura no cache")
    public ResponseEntity<Map<String, Object>> testCache() {
        Map<String, Object> response = new HashMap<>();

        try {
            Cache cache = cacheManager.getCache("paciente");
            if (cache == null) {
                response.put("status", "ERROR");
                response.put("message", "Cache 'paciente' não encontrado");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            String testKey = "test-" + UUID.randomUUID().toString();
            TestData testData = new TestData();
            testData.setId(UUID.randomUUID());
            testData.setMessage("Teste de cache Redis");
            testData.setTimestamp(LocalDateTime.now());

            log.info("📝 Testando escrita no cache - Chave: {}", testKey);
            cache.put(testKey, testData);

            log.info("📖 Testando leitura do cache - Chave: {}", testKey);
            Cache.ValueWrapper wrapper = cache.get(testKey);

            if (wrapper != null && wrapper.get() != null) {
                TestData retrieved = (TestData) wrapper.get();
                boolean match = retrieved != null && testData.getId().equals(retrieved.getId());
                response.put("status", "OK");
                response.put("message", "Cache funcionando corretamente");
                response.put("testKey", testKey);
                response.put("written", testData);
                response.put("retrieved", retrieved);
                response.put("match", match);

                cache.evict(testKey);

                log.info("✅ Teste de cache: OK - Dados escritos e lidos com sucesso");
            } else {
                response.put("status", "ERROR");
                response.put("message", "Não foi possível ler os dados do cache");
                log.warn("⚠️ Teste de cache: FALHOU - Dados não encontrados após escrita");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ Erro ao testar cache: {}", e.getMessage(), e);
            response.put("status", "ERROR");
            response.put("error", e.getMessage());
            response.put("message", "Erro ao testar operações de cache");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/info")
    @Operation(summary = "Obter informações do Redis", description = "Retorna informações sobre a configuração e status do Redis")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();

        try {
            RedisConnection connection = redisConnectionFactory.getConnection();

            Map<String, Object> redisInfo = new HashMap<>();
            redisInfo.put("connected", true);

            try {
                String pong = connection.ping();
                redisInfo.put("ping", pong);
            } catch (Exception e) {
                log.warn("Não foi possível fazer ping no Redis: {}", e.getMessage());
            }

            try {

                if (connection instanceof org.springframework.data.redis.connection.DefaultedRedisConnection) {
                    org.springframework.data.redis.connection.DefaultedRedisConnection defaultedConnection =
                        (org.springframework.data.redis.connection.DefaultedRedisConnection) connection;
                    Long dbSize = defaultedConnection.dbSize();
                    redisInfo.put("dbSize", dbSize != null ? dbSize : 0);
                } else {
                    redisInfo.put("dbSize", "N/A (tipo de conexão não suportado)");
                }
            } catch (Exception e) {
                log.warn("Não foi possível obter tamanho do banco Redis: {}", e.getMessage());
                redisInfo.put("dbSize", "N/A");
            }

            connection.close();

            Map<String, Object> cacheInfo = new HashMap<>();
            cacheInfo.put("cacheNames", cacheManager.getCacheNames());

            if (redisConnectionFactory instanceof org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory) {
                org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory lettuceFactory =
                    (org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory) redisConnectionFactory;
                redisInfo.put("hostName", lettuceFactory.getHostName());
                redisInfo.put("port", lettuceFactory.getPort());
                redisInfo.put("database", lettuceFactory.getDatabase());
            }

            response.put("status", "OK");
            response.put("redis", redisInfo);
            response.put("cacheManager", cacheInfo);
            response.put("timestamp", LocalDateTime.now());

            log.info("✅ Informações do Redis obtidas com sucesso");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ Erro ao obter informações do Redis: {}", e.getMessage(), e);
            response.put("status", "ERROR");
            response.put("error", e.getMessage());
            response.put("message", "Erro ao obter informações do Redis");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obter estatísticas do cache", description = "Retorna estatísticas de uso do cache")
    public ResponseEntity<Map<String, Object>> stats() {
        Map<String, Object> response = new HashMap<>();

        try {
            Cache cache = cacheManager.getCache("paciente");
            if (cache == null) {
                response.put("status", "ERROR");
                response.put("message", "Cache 'paciente' não encontrado");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            Map<String, Object> stats = new HashMap<>();
            stats.put("cacheName", cache.getName());
            stats.put("nativeCache", cache.getNativeCache().getClass().getName());

            response.put("status", "OK");
            response.put("cache", stats);
            response.put("timestamp", LocalDateTime.now());

            log.info("✅ Estatísticas do cache obtidas com sucesso");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ Erro ao obter estatísticas do cache: {}", e.getMessage(), e);
            response.put("status", "ERROR");
            response.put("error", e.getMessage());
            response.put("message", "Erro ao obter estatísticas do cache");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Data
    private static class TestData {
        private UUID id;
        private String message;
        private LocalDateTime timestamp;
    }
}
