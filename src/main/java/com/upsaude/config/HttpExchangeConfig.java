package com.upsaude.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para habilitar o endpoint /actuator/httpexchanges.
 * 
 * Este endpoint registra as últimas requisições HTTP recebidas pela aplicação,
 * incluindo informações como:
 * - URL
 * - Método HTTP
 * - Status de resposta
 * - Tempo de processamento
 * - Headers
 * - Principal (usuário autenticado)
 * 
 * O InMemoryHttpExchangeRepository armazena os últimos 100 requests por padrão.
 * Útil para debugging, monitoramento e auditoria de requisições.
 * 
 * @author UPSaude
 */
@Configuration
public class HttpExchangeConfig {
    
    /**
     * Cria um repositório em memória para armazenar as últimas 100 requisições HTTP.
     * 
     * @return HttpExchangeRepository configurado
     */
    @Bean
    public HttpExchangeRepository httpExchangeRepository() {
        return new InMemoryHttpExchangeRepository();
    }
}

