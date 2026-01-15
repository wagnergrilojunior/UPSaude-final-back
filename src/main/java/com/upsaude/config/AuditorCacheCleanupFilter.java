package com.upsaude.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para limpar o cache do auditor no final de cada requisição.
 * Isso garante que o cache ThreadLocal não fique "sujo" entre requisições
 * quando a thread é reutilizada pelo pool de threads do servidor.
 */
@Slf4j
@Component
public class AuditorCacheCleanupFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            // Sempre limpa o cache no final da requisição
            UsuarioSistemaAuditorAware.clearCache();
        }
    }
}
