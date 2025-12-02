package com.upsaude.service;

import com.upsaude.config.GrafanaPrometheusConfig;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Serviço responsável por fazer push periódico das métricas Prometheus
 * para o Grafana Cloud usando o endpoint de remote_write.
 * 
 * Este serviço:
 * - Coleta métricas do MeterRegistry (Prometheus) ou via endpoint HTTP do Actuator
 * - Envia as métricas em formato Prometheus para o Grafana Cloud
 * - Executa periodicamente conforme configurado
 * - Usa autenticação básica com username e token
 * 
 * @author UPSaúde
 */
@Slf4j
@Service
@ConditionalOnProperty(
    prefix = "grafana.prometheus.remote-write",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = false
)
public class GrafanaPrometheusPushService {

    private final GrafanaPrometheusConfig config;
    private final MeterRegistry meterRegistry;
    private final RestClient grafanaClient;
    private final RestClient actuatorClient;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private Method scrapeMethod;
    private boolean useHttpEndpoint = false;

    public GrafanaPrometheusPushService(
            GrafanaPrometheusConfig config,
            MeterRegistry meterRegistry,
            @Value("${server.port:8080}") int serverPort,
            @Value("${server.servlet.context-path:/api}") String contextPath) {
        this.config = config;
        this.meterRegistry = meterRegistry;
        
        // Cria o RestClient para Grafana Cloud com autenticação básica
        String auth = Base64.getEncoder().encodeToString(
            (config.getUsername() + ":" + config.getPassword())
                .getBytes(StandardCharsets.UTF_8)
        );
        
        this.grafanaClient = RestClient.builder()
            .baseUrl(config.getUrl())
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth)
            .build();
        
        // Cria o RestClient para obter métricas do Actuator (fallback)
        String actuatorUrl = String.format("http://localhost:%d%s", serverPort, contextPath);
        this.actuatorClient = RestClient.builder()
            .baseUrl(actuatorUrl)
            .build();
        
        // Tenta encontrar o método scrape() do PrometheusMeterRegistry usando reflection
        try {
            Class<?> prometheusClass = Class.forName("io.micrometer.prometheus.PrometheusMeterRegistry");
            if (prometheusClass.isInstance(meterRegistry)) {
                this.scrapeMethod = prometheusClass.getMethod("scrape");
                log.info("✅ Método scrape() do PrometheusMeterRegistry encontrado via reflection");
            } else {
                log.warn("MeterRegistry não é uma instância de PrometheusMeterRegistry. Usando endpoint HTTP do Actuator.");
                this.useHttpEndpoint = true;
            }
        } catch (ClassNotFoundException e) {
            log.warn("PrometheusMeterRegistry não encontrado no classpath. Usando endpoint HTTP do Actuator.");
            this.useHttpEndpoint = true;
        } catch (Exception e) {
            log.warn("Erro ao configurar reflection: {}. Usando endpoint HTTP do Actuator.", e.getMessage());
            this.useHttpEndpoint = true;
        }
        
        log.info("GrafanaPrometheusPushService inicializado. Push habilitado para: {}", config.getUrl());
        log.info("Intervalo de push: {} segundos", config.getPushInterval());
        log.info("Método de coleta: {}", useHttpEndpoint ? "HTTP Actuator Endpoint" : "Reflection (scrape())");
    }

    /**
     * Envia as métricas Prometheus para o Grafana Cloud.
     * Este método é executado periodicamente conforme configurado.
     * 
     * NOTA: O Grafana Cloud espera receber métricas no formato Prometheus Remote Write Protocol
     * (Snappy + protobuf), não texto plano. Este serviço está desabilitado por padrão.
     * Recomenda-se usar scraping do endpoint /actuator/prometheus ao invés de push.
     */
    @Scheduled(fixedDelayString = "${grafana.prometheus.remote-write.push-interval:30}000")
    public void pushMetrics() {
        // NOTA: Push automático desabilitado porque o Grafana Cloud requer formato Remote Write Protocol
        // (Snappy + protobuf), não texto plano. Use scraping do endpoint /actuator/prometheus ao invés de push.
        // Este método está vazio para evitar erros nos logs.
        // Para habilitar push, é necessário implementar o Remote Write Protocol completo.
        log.debug("Push automático de métricas desabilitado. Use scraping do endpoint /actuator/prometheus no Grafana Cloud.");
        return;
    }

    /**
     * Envia métricas manualmente (útil para testes).
     */
    public void pushMetricsNow() {
        pushMetrics();
    }
}

