package com.upsaude.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do OpenAPI/Swagger para documentação da API.
 * 
 * @author UPSaúde
 */
@Configuration
public class OpenApiConfig {

    @Value("${springdoc.api-server-url:}")
    private String apiServerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        
        // Se a URL do servidor estiver configurada, adiciona como servidor
        if (apiServerUrl != null && !apiServerUrl.isEmpty()) {
            Server server = new Server();
            server.setUrl(apiServerUrl);
            server.setDescription("Servidor da API");
            openAPI.setServers(List.of(server));
        }
        
        return openAPI;
    }
}

