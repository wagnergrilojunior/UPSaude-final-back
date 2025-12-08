package com.upsaude.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry point customizado para tratamento de erros de autenticação.
 * Retorna 401 (Unauthorized) quando o usuário não está autenticado.
 * 
 * IMPORTANTE: Não trata erros de autenticação de endpoints do Actuator.
 * O Actuator deve tratar seus próprios erros normalmente.
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Verifica se o path é de um endpoint do Actuator.
     * 
     * @param path caminho da requisição
     * @return true se for endpoint do Actuator, false caso contrário
     */
    private boolean isActuatorEndpoint(String path) {
        if (path == null) {
            return false;
        }
        // Verifica se o path contém /actuator (com ou sem context-path /api)
        return path.contains("/actuator/") || path.startsWith("/actuator");
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        
        String path = request.getRequestURI();
        
        // Não trata erros de autenticação de endpoints do Actuator
        // Deixa o Spring Security tratar normalmente
        if (isActuatorEndpoint(path)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
            return;
        }
        
        log.warn("Acesso não autorizado para: {} - Método: {} - Erro: {}", 
                path, request.getMethod(), authException.getMessage());
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("erro", "Não Autorizado");
        body.put("mensagem", "Token de autenticação inválido ou não fornecido");
        body.put("path", path);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}

