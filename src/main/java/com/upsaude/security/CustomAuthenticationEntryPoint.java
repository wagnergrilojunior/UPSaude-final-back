package com.upsaude.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
 * IMPORTANTE: Não trata erros de autenticação de endpoints do Actuator ou
 * /error.
 * O Actuator usa /error como fallback quando ocorre exceção interna.
 * Esses endpoints devem tratar seus próprios erros normalmente.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    /**
     * Verifica se o path deve ser ignorado por este handler.
     * Endpoints /error não devem ser tratados por este handler.
     * 
     * @param path caminho da requisição
     * @return true se deve ser ignorado, false caso contrário
     */
    private boolean shouldIgnoreEndpoint(String path) {
        if (path == null) {
            return false;
        }

        // Ignora endpoint /error usado pelo Spring Boot para tratamento de erros
        boolean isErrorEndpoint = path.equals("/error") ||
                path.equals("/api/error") ||
                path.startsWith("/error/") ||
                path.startsWith("/api/error/");

        return isErrorEndpoint;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        String path = request.getRequestURI();

        // Não trata erros de autenticação de endpoints do Actuator ou /error
        // Deixa o Spring Security tratar normalmente
        if (shouldIgnoreEndpoint(path)) {
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

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
