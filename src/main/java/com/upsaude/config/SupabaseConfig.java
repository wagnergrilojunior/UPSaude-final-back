package com.upsaude.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "supabase")
public class SupabaseConfig {
    private String url;
    private String anonKey;
    private String serviceRoleKey;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate;
    }

    @Bean(name = "supabaseObjectMapper")
    @org.springframework.context.annotation.Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Java 17 - JavaTimeModule já está disponível via jackson-datatype-jsr310 (incluído no spring-boot-starter-web)
        mapper.findAndRegisterModules();
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}

