package com.upsaude.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

@Slf4j
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "brevo")
public class BrevoConfig {
    
    private Boolean enabled = true;
    private String apiKey;
    private String baseUrl = "https://api.brevo.com/v3";
    private Integer timeoutSeconds = 30;
    private Retry retry = new Retry();
    private Sender sender = new Sender();

    @PostConstruct
    public void init() {
        if (apiKey != null) {
            apiKey = apiKey.trim(); // Remove espaços em branco
        }
        
        if (Boolean.TRUE.equals(enabled)) {
            if (apiKey == null || apiKey.isEmpty()) {
                log.warn("⚠️ Brevo está habilitado mas a chave API não está configurada!");
            } else {
                String masked = apiKey.length() > 10 
                        ? apiKey.substring(0, 8) + "..." + apiKey.substring(apiKey.length() - 4)
                        : "N/A";
                log.info("✅ Brevo configurado. Chave API: {} (tamanho: {} caracteres)", masked, apiKey.length());
                
                // Validar formato
                if (!apiKey.startsWith("xkeysib-") && !apiKey.startsWith("xsmtpsib-")) {
                    log.warn("⚠️ Chave API do Brevo não tem formato esperado. Deve começar com 'xkeysib-' ou 'xsmtpsib-'");
                }
            }
        } else {
            log.info("ℹ️ Brevo está desabilitado (brevo.enabled=false)");
        }
    }

    @Data
    public static class Retry {
        private Integer maxAttempts = 3;
        private Long backoffMillis = 5000L;
    }

    @Data
    public static class Sender {
        private Noreply noreply = new Noreply();
        private Notificacoes notificacoes = new Notificacoes();
        private Suporte suporte = new Suporte();

        @Data
        public static class Noreply {
            private String email = "noreply@wgbsolucoes.com.br";
            private String name = "UPSaude - Não Responda";
        }

        @Data
        public static class Notificacoes {
            private String email = "notificacoes@wgbsolucoes.com.br";
            private String name = "UPSaude - Notificações";
        }

        @Data
        public static class Suporte {
            private String email = "suporte@wgbsolucoes.com.br";
            private String name = "UPSaude - Suporte";
        }
    }

    @Bean(name = "brevoRestTemplate")
    public RestTemplate brevoRestTemplate() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(20);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(timeoutSeconds))
                .setResponseTimeout(Timeout.ofSeconds(timeoutSeconds))
                .setConnectionRequestTimeout(Timeout.ofSeconds(timeoutSeconds))
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .evictIdleConnections(TimeValue.ofMinutes(5))
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(timeoutSeconds * 1000);
        factory.setConnectionRequestTimeout(timeoutSeconds * 1000);

        return new RestTemplate(factory);
    }
}
