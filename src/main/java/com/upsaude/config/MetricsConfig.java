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

@Slf4j
@Configuration
public class MetricsConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final MeterRegistry meterRegistry;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public MetricsConfig(@Lazy MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
            .commonTags("application", "UPSaude")
            .commonTags("version", "1.0.0")
            .commonTags("environment", activeProfile);
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() == null) {
            try {
                registerDataSourceMetrics();

                log.info("Métricas personalizadas registradas com sucesso");
            } catch (Exception e) {
                log.error("Erro ao registrar métricas personalizadas", e);
            }
        }
    }

    private void registerDataSourceMetrics() {

        log.debug("Métricas do DataSource desabilitadas para evitar conexões extras durante inicialização");
    }

    @Bean
    public Counter totalRequestsCounter() {
        try {
            return Counter.builder("upsaude.http.requests.total")
                .description("Total de requisições HTTP recebidas")
                .tag("type", "total")
                .register(meterRegistry);
        } catch (Exception e) {

            return Counter.builder("upsaude.http.requests.total.fallback")
                .description("Total de requisições HTTP (fallback)")
                .register(new io.micrometer.core.instrument.simple.SimpleMeterRegistry());
        }
    }

    @Bean
    public Counter failedRequestCounter() {
        try {
            return Counter.builder("upsaude.http.requests.failed")
                .description("Total de requisições HTTP que falharam")
                .tag("type", "failed")
                .register(meterRegistry);
        } catch (Exception e) {

            return Counter.builder("upsaude.http.requests.failed.fallback")
                .description("Total de requisições falhadas (fallback)")
                .register(new io.micrometer.core.instrument.simple.SimpleMeterRegistry());
        }
    }

    @Bean
    public Timer requestLatencyTimer() {
        try {
            return Timer.builder("upsaude.http.requests.latency")
                .description("Latência das requisições HTTP")
                .register(meterRegistry);
        } catch (Exception e) {

            return Timer.builder("upsaude.http.requests.latency.fallback")
                .description("Latência das requisições HTTP (fallback)")
                .register(new io.micrometer.core.instrument.simple.SimpleMeterRegistry());
        }
    }
}
