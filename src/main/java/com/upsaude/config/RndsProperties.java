package com.upsaude.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rnds")
public class RndsProperties {
    private String baseUrl;
    private Integer timeoutSeconds = 30;
    private String apiKey;
    private String apiSecret;
}
