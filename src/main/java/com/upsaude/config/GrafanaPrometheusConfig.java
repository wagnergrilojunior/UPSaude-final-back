package com.upsaude.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração das propriedades do Grafana Cloud Prometheus.
 * 
 * Esta classe armazena as configurações necessárias para enviar métricas
 * para o Grafana Cloud usando o endpoint de remote_write.
 * 
 * @author UPSaúde
 */
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "grafana.prometheus.remote-write")
public class GrafanaPrometheusConfig {
    
    /**
     * Habilita ou desabilita o envio de métricas para Grafana Cloud.
     */
    private boolean enabled = false;
    
    /**
     * URL do endpoint de remote_write do Grafana Cloud.
     */
    private String url;
    
    /**
     * Username/Instance ID para autenticação básica.
     */
    private String username;
    
    /**
     * Token/Password para autenticação básica.
     */
    private String password;
    
    /**
     * Intervalo em segundos para fazer push das métricas.
     * Padrão: 30 segundos
     */
    private int pushInterval = 30;
}

