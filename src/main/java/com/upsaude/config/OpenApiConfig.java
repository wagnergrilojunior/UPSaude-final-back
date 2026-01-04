package com.upsaude.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080/api");
        localServer.setDescription("Ambiente Local");

        Server devServer = new Server();
        devServer.setUrl("https://api-dev.upsaude.wgbsolucoes.com.br/api");
        devServer.setDescription("Ambiente de Desenvolvimento");

        Server prodServer = new Server();
        prodServer.setUrl("https://api-pmsrs.upsaude.wgbsolucoes.com.br/api");
        prodServer.setDescription("Ambiente de Produção");

        openAPI.setServers(List.of(localServer, devServer, prodServer));

        return openAPI;
    }
}
