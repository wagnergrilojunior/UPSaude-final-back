package com.upsaude.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Inicializador de cache para garantir que métricas estejam disponíveis no Actuator.
 * 
 * O Spring Boot Actuator só expõe métricas de cache após o cache ser usado pela primeira vez.
 * Este componente inicializa todos os caches durante o startup para que as métricas
 * estejam disponíveis imediatamente, evitando erros 404 ao acessar /actuator/metrics/cache.*
 * 
 * Também executa um comando Redis diretamente para garantir que as métricas do Lettuce
 * sejam registradas imediatamente.
 * 
 * IMPORTANTE: Esta configuração é desabilitada no profile 'local' para permitir
 * desenvolvimento sem Redis.
 * 
 * @author UPSaúde
 */
@Slf4j
@Configuration
@Profile("!local")
public class CacheInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    // Lista de caches conhecidos que podem ser usados na aplicação
    private static final List<String> KNOWN_CACHES = Arrays.asList(
        "cidades", "estados", "medicos", "pacientes", "especialidades",
        "profissionaisSaude", "vacinas", "medicacoes", "exames"
    );

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        // Evita registrar duas vezes quando há múltiplos contextos
        if (event.getApplicationContext().getParent() == null) {
            initializeRedisMetrics();
            initializeCaches();
        }
    }

    /**
     * Executa um comando Redis diretamente para garantir que as métricas do Lettuce sejam registradas.
     * Isso garante que /actuator/metrics/lettuce.* esteja disponível imediatamente.
     */
    private void initializeRedisMetrics() {
        if (redisConnectionFactory == null) {
            log.debug("RedisConnectionFactory não disponível - pulando inicialização de métricas Redis");
            return;
        }

        try {
            RedisConnection connection = redisConnectionFactory.getConnection();
            try {
                // Executa um comando simples para garantir que as métricas do Lettuce sejam registradas
                connection.ping();
                log.debug("Comando Redis executado com sucesso - métricas do Lettuce devem estar disponíveis");
            } catch (Exception e) {
                log.warn("Erro ao executar comando Redis durante inicialização: {}. " +
                    "Métricas do Lettuce estarão disponíveis após o primeiro uso do Redis.", e.getMessage());
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (Exception e) {
            log.warn("Erro ao obter conexão Redis durante inicialização: {}. " +
                "Métricas do Redis estarão disponíveis após o primeiro uso.", e.getMessage());
        }
    }

    /**
     * Inicializa todos os caches disponíveis para que as métricas estejam disponíveis.
     */
    private void initializeCaches() {
        if (cacheManager == null) {
            log.debug("CacheManager não disponível - pulando inicialização de cache");
            return;
        }

        try {
            var cacheNames = cacheManager.getCacheNames();
            
            // Se não houver caches criados ainda, tenta criar alguns conhecidos
            if (cacheNames == null || cacheNames.isEmpty()) {
                log.debug("Nenhum cache criado ainda - tentando inicializar caches conhecidos");
                initializeKnownCaches();
                return;
            }

            int initializedCount = 0;
            for (String cacheName : cacheNames) {
                try {
                    Cache cache = cacheManager.getCache(cacheName);
                    if (cache != null) {
                        initializeCache(cache, cacheName);
                        initializedCount++;
                    }
                } catch (Exception e) {
                    log.warn("Erro ao inicializar cache '{}': {}. Métricas podem não estar disponíveis até o primeiro uso.", 
                        cacheName, e.getMessage());
                }
            }

            if (initializedCount > 0) {
                log.info("{} cache(s) inicializado(s) para métricas do Actuator", initializedCount);
            }
        } catch (Exception e) {
            log.warn("Erro ao inicializar caches para métricas: {}. " +
                "Métricas de cache estarão disponíveis após o primeiro uso do cache.", e.getMessage());
        }
    }

    /**
     * Tenta inicializar caches conhecidos mesmo que ainda não tenham sido criados.
     */
    private void initializeKnownCaches() {
        int initializedCount = 0;
        for (String cacheName : KNOWN_CACHES) {
            try {
                Cache cache = cacheManager.getCache(cacheName);
                if (cache != null) {
                    initializeCache(cache, cacheName);
                    initializedCount++;
                }
            } catch (Exception e) {
                // Ignora erros - cache pode não existir ainda
                log.trace("Cache '{}' não disponível ainda: {}", cacheName, e.getMessage());
            }
        }
        
        if (initializedCount > 0) {
            log.info("{} cache(s) conhecido(s) inicializado(s) para métricas do Actuator", initializedCount);
        }
    }

    /**
     * Inicializa um cache específico fazendo uma operação simples.
     */
    private void initializeCache(Cache cache, String cacheName) {
        try {
            // Inicializa o cache fazendo uma operação simples
            // Usa uma chave temporária que será removida automaticamente pelo TTL
            String initKey = "__cache_init__" + System.currentTimeMillis();
            cache.put(initKey, "initialized");
            cache.evict(initKey); // Remove imediatamente para não poluir o cache
            log.debug("Cache '{}' inicializado para métricas", cacheName);
        } catch (Exception e) {
            log.warn("Erro ao inicializar cache '{}': {}", cacheName, e.getMessage());
            throw e;
        }
    }
}
