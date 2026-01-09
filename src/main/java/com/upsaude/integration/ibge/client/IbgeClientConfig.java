package com.upsaude.integration.ibge.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * Configuração do cliente HTTP para integração com API IBGE
 */
@Data
@Configuration
@Primary
@ConfigurationProperties(prefix = "ibge.api")
public class IbgeClientConfig {

    private String baseUrl = "https://servicodados.ibge.gov.br/api/v1";
    private Integer timeoutSeconds = 30;
    private Integer timeoutValidationSeconds = 10;
    private Retry retry = new Retry();

    @Data
    public static class Retry {
        private Integer maxAttempts = 3;
        private Integer maxAttemptsValidation = 2;
        private Long backoffMillis = 1000L;
    }

    @Bean(name = "ibgeRestTemplate")
    public RestTemplate ibgeRestTemplate() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(20);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(timeoutSeconds))
                .setResponseTimeout(Timeout.ofSeconds(timeoutSeconds))
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .evictIdleConnections(Timeout.of(30, TimeUnit.SECONDS))
                .evictExpiredConnections()
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }
}

