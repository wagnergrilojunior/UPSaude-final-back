package com.upsaude.security;

import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SupabaseAuthService supabaseAuthService;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        String method = request.getMethod();
        log.info("Processando requisição para: {} - Método: {}", path, method);
        
        // Ignora endpoints públicos (não processa autenticação)
        if (isPublicEndpoint(path)) {
            log.debug("Endpoint público detectado: {}", path);
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = extractTokenFromRequest(request);
        
        if (token == null || token.isEmpty()) {
            log.warn("Token não fornecido para endpoint protegido: {} - Método: {}", path, method);
            // Continua o filter chain - o Spring Security vai retornar 403 ou 401
            filterChain.doFilter(request, response);
            return;
        }
        
        log.info("Token encontrado ({} caracteres), validando com Supabase...", token.length());
        
        // Valida o token mesmo se já existe autenticação (para refresh se necessário)
        try {
            // Valida o token com o Supabase e obtém informações do usuário
            SupabaseAuthResponse.User user = supabaseAuthService.verifyToken(token);
            
            if (user != null) {
                log.info("Token válido! Usuário: {}", user.getEmail());
                
                // Cria authorities baseado no role do usuário
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                if (user.getRole() != null) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
                }
                
                // Adiciona role padrão authenticated se não houver role específica
                if (authorities.isEmpty()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_AUTHENTICATED"));
                }
                
                // Cria o authentication object
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                                user.getId().toString(),
                                token,
                                authorities
                        );
                
                // Adiciona detalhes do usuário
                authentication.setDetails(user);
                
                // Define o authentication no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                log.info("Usuário autenticado com sucesso no Spring Security: {} (ID: {}) - Authorities: {}", 
                        user.getEmail(), user.getId(), authorities);
            } else {
                log.warn("Token válido mas usuário não encontrado");
                SecurityContextHolder.clearContext();
            }
        } catch (Exception e) {
            log.error("ERRO ao validar token JWT para path {}: {} - Mensagem: {}", 
                    path, e.getClass().getSimpleName(), e.getMessage(), e);
            SecurityContextHolder.clearContext();
            // Continua o filter chain - o Spring Security vai retornar 403
        }
        
        // Verifica se há autenticação no contexto antes de continuar
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.warn("AUTENTICAÇÃO NÃO CONFIGURADA no SecurityContext após processamento do token para: {}", path);
        } else {
            log.info("Autenticação presente no SecurityContext: {}", 
                    SecurityContextHolder.getContext().getAuthentication().getName());
        }
        
        filterChain.doFilter(request, response);
    }
    
    private boolean isPublicEndpoint(String path) {
        if (path == null) {
            return false;
        }
        // Verifica se o path contém endpoints públicos
        // Funciona tanto com quanto sem o context-path prefixado
        // Importante: apenas /v1/auth/login é público, não todos os endpoints de auth
        return path.contains("/v1/auth/login") ||
               path.contains("/actuator/health") ||
               path.contains("/actuator/info") ||
               path.contains("/api-docs") ||
               path.contains("/swagger-ui");
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}

