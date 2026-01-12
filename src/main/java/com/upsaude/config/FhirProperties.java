package com.upsaude.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "fhir")
@Getter
@Setter
public class FhirProperties {

    private String baseUrl = "https://terminologia.saude.gov.br/fhir";

    private int timeout = 30;

    private Cache cache = new Cache();

    @Getter
    @Setter
    public static class Cache {
        private boolean enabled = true;
        private int ttlHours = 24;
        private String keyPrefix = "fhir::";
    }
}
