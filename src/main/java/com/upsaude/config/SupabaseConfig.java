package com.upsaude.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "supabase")
public class SupabaseConfig {
    private String url;
    private String anonKey;
    private String serviceRoleKey;

    @Bean
    public CloseableHttpClient supabaseHttpClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(20);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(30))
                // Para arquivos grandes, precisamos de um responseTimeout muito alto (upload streaming pode levar horas para arquivos muito grandes)
                .setResponseTimeout(Timeout.ofHours(2))
                // Socket timeout também aumentado para uploads longos
                .setConnectionRequestTimeout(Timeout.ofSeconds(30))
                .build();

        return HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .evictIdleConnections(TimeValue.ofMinutes(5))
                .build();
    }

    @Bean
    public RestTemplate restTemplate(CloseableHttpClient supabaseHttpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(supabaseHttpClient);
        // Timeouts aumentados para suportar uploads de arquivos grandes
        // O cliente HTTP já tem responseTimeout configurado para 2 horas, então aqui só configuramos
        // os timeouts de conexão que o RestTemplate gerencia diretamente
        factory.setConnectTimeout(30_000); // 30 segundos
        factory.setConnectionRequestTimeout(30_000); // 30 segundos
        // Desabilita buffer para permitir streaming de arquivos grandes
        factory.setBufferRequestBody(false);
        return new RestTemplate(factory);
    }

    @Bean(name = "supabaseObjectMapper")
    @org.springframework.context.annotation.Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.findAndRegisterModules();
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
