package com.upsaude.service;

import com.upsaude.config.GrafanaPrometheusConfig;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
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
 * - Coleta métricas do MeterRegistry (Prometheus)
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
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private Method scrapeMethod;

    public GrafanaPrometheusPushService(
            GrafanaPrometheusConfig config,
            MeterRegistry meterRegistry) {
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
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
            .build();
        
        // Tenta encontrar o método scrape() do PrometheusMeterRegistry usando reflection
        try {
            Class<?> prometheusClass = Class.forName("io.micrometer.prometheus.PrometheusMeterRegistry");
            if (prometheusClass.isInstance(meterRegistry)) {
                this.scrapeMethod = prometheusClass.getMethod("scrape");
                log.info("Método scrape() do PrometheusMeterRegistry encontrado via reflection");
            }
        } catch (Exception e) {
            log.warn("Não foi possível encontrar PrometheusMeterRegistry via reflection: {}", e.getMessage());
        }
        
        log.info("GrafanaPrometheusPushService inicializado. Push habilitado para: {}", config.getUrl());
        log.info("Intervalo de push: {} segundos", config.getPushInterval());
    }

    /**
     * Envia as métricas Prometheus para o Grafana Cloud.
     * Este método é executado periodicamente conforme configurado.
     */
    @Scheduled(fixedDelayString = "${grafana.prometheus.remote-write.push-interval:30}000")
    public void pushMetrics() {
        // Evita execuções concorrentes
        if (!isRunning.compareAndSet(false, true)) {
            log.debug("Push de métricas já em execução, pulando este ciclo");
            return;
        }

        try {
            // Obtém as métricas em formato Prometheus
            String prometheusMetrics = null;
            
            // Tenta usar o método scrape() se disponível via reflection
            if (scrapeMethod != null) {
                try {
                    prometheusMetrics = (String) scrapeMethod.invoke(meterRegistry);
                } catch (Exception e) {
                    log.warn("Erro ao chamar método scrape() via reflection: {}", e.getMessage());
                }
            }
            
            // Se não conseguiu obter via reflection, retorna sem enviar
            // (em produção, você pode configurar um endpoint HTTP alternativo)
            if (prometheusMetrics == null || prometheusMetrics.trim().isEmpty()) {
                log.warn("Nenhuma métrica disponível para enviar");
                return;
            }
            
            // Garante que não é null para evitar warnings
            final String metricsToSend = prometheusMetrics;

            // Envia as métricas para o Grafana Cloud
            grafanaClient.post()
                .body(metricsToSend)
                .retrieve()
                .toBodilessEntity();

            log.debug("Métricas enviadas com sucesso para Grafana Cloud. Tamanho: {} bytes", 
                metricsToSend.length());
                
        } catch (Exception e) {
            log.error("Erro ao enviar métricas para Grafana Cloud: {}", e.getMessage(), e);
        } finally {
            isRunning.set(false);
        }
    }

    /**
     * Envia métricas manualmente (útil para testes).
     */
    public void pushMetricsNow() {
        pushMetrics();
    }
}

