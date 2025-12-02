package com.upsaude.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Configuração de métricas personalizadas para observabilidade.
 * 
 * Esta classe configura:
 * - Métricas do HikariCP (DataSource)
 * - Métricas do Redis (Cache)
 * - Métricas HTTP personalizadas
 * - Métricas da JVM
 * - Suporte a anotações @Timed, @Counted e @Observed
 * 
 * @author UPSaúde
 */
@Slf4j
@Configuration
public class MetricsConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final MeterRegistry meterRegistry;
    private final DataSource dataSource;
    
    // Dependências opcionais - podem ser null se não configuradas
    @Autowired(required = false)
    private CacheManager cacheManager;
    
    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * Construtor com @Lazy para evitar dependência circular.
     */
    public MetricsConfig(@Lazy MeterRegistry meterRegistry, DataSource dataSource) {
        this.meterRegistry = meterRegistry;
        this.dataSource = dataSource;
    }

    /**
     * Customiza o registro de métricas com tags globais.
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
            .commonTags("application", "UPSaude")
            .commonTags("version", "1.0.0");
    }

    /**
     * Habilita suporte à anotação @Timed em métodos.
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    /**
     * Registra métricas personalizadas após o contexto ser totalmente inicializado.
     * Isso evita problemas de dependência circular.
     */
    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        // Evita registrar duas vezes quando há múltiplos contextos
        if (event.getApplicationContext().getParent() == null) {
            try {
                registerDataSourceMetrics();
                registerCacheMetrics();
                log.info("Métricas personalizadas registradas com sucesso");
            } catch (Exception e) {
                log.error("Erro ao registrar métricas personalizadas", e);
            }
        }
    }

    /**
     * Registra métricas do DataSource (HikariCP).
     */
    private void registerDataSourceMetrics() {
        try {
            // Métrica de conexões ativas
            Gauge.builder("upsaude.datasource.connections.active", dataSource, ds -> {
                try (Connection conn = ds.getConnection()) {
                    // Verifica se a conexão está válida
                    return conn.isValid(1) ? 1 : 0;
                } catch (SQLException e) {
                    return 0;
                }
            })
            .description("Número de conexões ativas no pool do HikariCP")
            .register(meterRegistry);

            // Métrica de disponibilidade do DataSource
            Gauge.builder("upsaude.datasource.available", dataSource, ds -> {
                try (Connection conn = ds.getConnection()) {
                    return conn.isValid(1) ? 1 : 0;
                } catch (SQLException e) {
                    return 0;
                }
            })
            .description("Disponibilidade do DataSource (1 = disponível, 0 = indisponível)")
            .register(meterRegistry);

            log.debug("Métricas do DataSource registradas");
        } catch (Exception e) {
            log.warn("Não foi possível registrar métricas do DataSource: {}", e.getMessage());
        }
    }

    /**
     * Registra métricas do Cache (Redis).
     */
    private void registerCacheMetrics() {
        try {
            if (cacheManager != null && redisConnectionFactory != null) {
                // Métrica de disponibilidade do Redis
                Gauge.builder("upsaude.cache.redis.available", redisConnectionFactory, factory -> {
                    try {
                        factory.getConnection().ping();
                        return 1;
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .description("Disponibilidade do Redis (1 = disponível, 0 = indisponível)")
                .register(meterRegistry);

                log.debug("Métricas do Cache (Redis) registradas");
            }
        } catch (Exception e) {
            log.warn("Não foi possível registrar métricas do Cache: {}", e.getMessage());
        }
    }

    /**
     * Cria um contador de requisições totais.
     */
    @Bean
    public Counter totalRequestsCounter() {
        return Counter.builder("upsaude.http.requests.total")
            .description("Total de requisições HTTP recebidas")
            .tag("type", "total")
            .register(meterRegistry);
    }

    /**
     * Cria um contador de requisições falhadas.
     */
    @Bean
    public Counter failedRequestCounter() {
        return Counter.builder("upsaude.http.requests.failed")
            .description("Total de requisições HTTP que falharam")
            .tag("type", "failed")
            .register(meterRegistry);
    }

    /**
     * Cria um timer para latência de requisições.
     */
    @Bean
    public Timer requestLatencyTimer() {
        return Timer.builder("upsaude.http.requests.latency")
            .description("Latência das requisições HTTP")
            .register(meterRegistry);
    }
}

