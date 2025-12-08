package com.upsaude.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

/**
 * Inicializador de cache para garantir que métricas estejam disponíveis no Actuator.
 * 
 * O Spring Boot Actuator só expõe métricas de cache após o cache ser usado pela primeira vez.
 * Este componente inicializa todos os caches durante o startup para que as métricas
 * estejam disponíveis imediatamente, evitando erros 404 ao acessar /actuator/metrics/cache.*
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

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        // Evita registrar duas vezes quando há múltiplos contextos
        if (event.getApplicationContext().getParent() == null) {
            initializeCaches();
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
            if (cacheNames == null || cacheNames.isEmpty()) {
                log.debug("Nenhum cache configurado - métricas de cache não estarão disponíveis até o primeiro uso");
                return;
            }

            int initializedCount = 0;
            for (String cacheName : cacheNames) {
                try {
                    Cache cache = cacheManager.getCache(cacheName);
                    if (cache != null) {
                        // Inicializa o cache fazendo uma operação simples
                        // Usa uma chave temporária que será removida automaticamente pelo TTL
                        String initKey = "__cache_init__" + System.currentTimeMillis();
                        cache.put(initKey, "initialized");
                        cache.evict(initKey); // Remove imediatamente para não poluir o cache
                        initializedCount++;
                        log.debug("Cache '{}' inicializado para métricas", cacheName);
                    }
                } catch (Exception e) {
                    log.warn("Erro ao inicializar cache '{}': {}. Métricas podem não estar disponíveis até o primeiro uso.", 
                        cacheName, e.getMessage());
                }
            }

            if (initializedCount > 0) {
                log.info("{} cache(s) inicializado(s) para métricas do Actuator", initializedCount);
            } else {
                log.debug("Nenhum cache foi inicializado - métricas estarão disponíveis após o primeiro uso");
            }
        } catch (Exception e) {
            log.warn("Erro ao inicializar caches para métricas: {}. " +
                "Métricas de cache estarão disponíveis após o primeiro uso do cache.", e.getMessage());
        }
    }
}
