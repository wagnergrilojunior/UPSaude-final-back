package com.upsaude.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(RndsProperties.class)
@RequiredArgsConstructor
public class RndsClientConfig {

    private final RndsProperties rndsProperties;

    @Bean(name = "rndsRestTemplate")
    public RestTemplate rndsRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(rndsProperties.getTimeoutSeconds()))
                .setReadTimeout(Duration.ofSeconds(rndsProperties.getTimeoutSeconds()))
                .build();
    }
}
