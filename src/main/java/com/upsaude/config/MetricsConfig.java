package com.upsaude.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

/**
 * Configuração de métricas personalizadas para observabilidade.
 * 
 * Esta classe configura:
 * - Métricas HTTP personalizadas
 * - Métricas da JVM
 * - Suporte a anotações @Timed, @Counted e @Observed
 * 
 * NOTA: Métricas do Redis/Cache são expostas automaticamente pelo Spring Boot Actuator
 * quando habilitadas via application.properties. Não fazemos ping no Redis durante
 * inicialização para evitar erros 500 no Actuator.
 * 
 * @author UPSaúde
 */
@Slf4j
@Configuration
public class MetricsConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final MeterRegistry meterRegistry;
    
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    /**
     * Construtor com @Lazy para evitar dependência circular.
     * DataSource removido do construtor para evitar criação de conexão durante inicialização.
     */
    public MetricsConfig(@Lazy MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     * Customiza o registro de métricas com tags globais.
     * Inclui a tag 'environment' para permitir separação de métricas por ambiente no Grafana.
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
            .commonTags("application", "UPSaude")
            .commonTags("version", "1.0.0")
            .commonTags("environment", activeProfile);
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
                // registerCacheMetrics() removido - métricas de cache são expostas automaticamente
                // pelo Spring Boot Actuator quando management.metrics.enable.cache=true
                // Não precisamos fazer ping no Redis durante inicialização
                log.info("Métricas personalizadas registradas com sucesso");
            } catch (Exception e) {
                log.error("Erro ao registrar métricas personalizadas", e);
            }
        }
    }

    /**
     * Registra métricas do DataSource (HikariCP).
     * DESABILITADO para evitar criação de conexões extras durante inicialização.
     * As métricas do HikariCP já são expostas automaticamente pelo Actuator quando habilitadas.
     */
    private void registerDataSourceMetrics() {
        // Métricas do DataSource desabilitadas para evitar conexões extras
        // O Actuator já expõe métricas do HikariCP quando management.metrics.jdbc.datasource.enabled=true
        // Como desabilitamos isso, não precisamos registrar métricas customizadas
        log.debug("Métricas do DataSource desabilitadas para evitar conexões extras durante inicialização");
    }

    /**
     * Método removido: registerCacheMetrics()
     * 
     * As métricas de cache são expostas automaticamente pelo Spring Boot Actuator
     * quando management.metrics.enable.cache=true está configurado.
     * 
     * Não precisamos fazer ping no Redis durante inicialização ou ao coletar métricas,
     * pois isso pode causar erros 500 no endpoint /actuator/metrics se o Redis
     * estiver lento ou indisponível.
     */

    /**
     * Cria um contador de requisições totais.
     */
    @Bean
    public Counter totalRequestsCounter() {
        try {
            return Counter.builder("upsaude.http.requests.total")
                .description("Total de requisições HTTP recebidas")
                .tag("type", "total")
                .register(meterRegistry);
        } catch (Exception e) {
            // Fallback se meterRegistry não estiver pronto
            return Counter.builder("upsaude.http.requests.total.fallback")
                .description("Total de requisições HTTP (fallback)")
                .register(new io.micrometer.core.instrument.simple.SimpleMeterRegistry());
        }
    }

    /**
     * Cria um contador de requisições falhadas.
     */
    @Bean
    public Counter failedRequestCounter() {
        try {
            return Counter.builder("upsaude.http.requests.failed")
                .description("Total de requisições HTTP que falharam")
                .tag("type", "failed")
                .register(meterRegistry);
        } catch (Exception e) {
            // Fallback se meterRegistry não estiver pronto
            return Counter.builder("upsaude.http.requests.failed.fallback")
                .description("Total de requisições falhadas (fallback)")
                .register(new io.micrometer.core.instrument.simple.SimpleMeterRegistry());
        }
    }

    /**
     * Cria um timer para latência de requisições.
     */
    @Bean
    public Timer requestLatencyTimer() {
        try {
            return Timer.builder("upsaude.http.requests.latency")
                .description("Latência das requisições HTTP")
                .register(meterRegistry);
        } catch (Exception e) {
            // Fallback se meterRegistry não estiver pronto
            return Timer.builder("upsaude.http.requests.latency.fallback")
                .description("Latência das requisições HTTP (fallback)")
                .register(new io.micrometer.core.instrument.simple.SimpleMeterRegistry());
        }
    }
}

