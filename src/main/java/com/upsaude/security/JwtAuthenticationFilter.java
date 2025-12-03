package com.upsaude.security;

import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.repository.UsuariosSistemaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SupabaseAuthService supabaseAuthService;
    private final UsuariosSistemaRepository usuariosSistemaRepository;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        String method = request.getMethod();
        
        // Ignora endpoints públicos (não processa autenticação)
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = extractTokenFromRequest(request);
        
        if (token == null || token.isEmpty()) {
            // Continua o filter chain - o Spring Security vai retornar 403 ou 401
            filterChain.doFilter(request, response);
            return;
        }
        
        // Valida o token mesmo se já existe autenticação (para refresh se necessário)
        try {
            // Valida o token com o Supabase e obtém informações do usuário
            SupabaseAuthResponse.User user = supabaseAuthService.verifyToken(token);
            
            if (user != null) {
                // 1. Adiciona role do Supabase (RBAC do Supabase)
                Set<String> authoritiesSet = new HashSet<>();
                if (user.getRole() != null && !user.getRole().isEmpty()) {
                    String supabaseRole = "ROLE_" + user.getRole().toUpperCase();
                    authoritiesSet.add(supabaseRole);
                }
                
                // 2. Adiciona role padrão authenticated se não houver nenhuma role
                if (authoritiesSet.isEmpty()) {
                    authoritiesSet.add("ROLE_AUTHENTICATED");
                }
                
                // 3. Converte para lista de GrantedAuthority
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String authority : authoritiesSet) {
                    authorities.add(new SimpleGrantedAuthority(authority));
                }
                
                // 4. Cria o authentication object
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                                user.getId().toString(),
                                token,
                                authorities
                        );
                
                // 5. Adiciona detalhes do usuário
                authentication.setDetails(user);
                
                // 6. Valida se o usuário tem acesso ao sistema (UsuariosSistema criado)
                // Exceto para o endpoint de verificar acesso, que precisa ser acessível mesmo sem UsuariosSistema
                if (!isVerificarAcessoEndpoint(path)) {
                    boolean temAcesso = usuariosSistemaRepository.findByUserId(user.getId())
                            .map(usuario -> usuario.getActive() != null && usuario.getActive())
                            .orElse(false);
                    
                    if (!temAcesso) {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setContentType("application/json");
                        response.getWriter().write("{\"erro\": \"Usuário não tem acesso ao sistema. É necessário criar um registro em UsuariosSistema.\"}");
                        return;
                    }
                }
                
                // 7. Define o authentication no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            // Continua o filter chain - o Spring Security vai retornar 403
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
    
    /**
     * Verifica se o endpoint é o de verificar acesso.
     * Este endpoint precisa ser acessível mesmo sem UsuariosSistema criado,
     * pois é usado pelo frontend para verificar se precisa criar o registro.
     */
    private boolean isVerificarAcessoEndpoint(String path) {
        if (path == null) {
            return false;
        }
        return path.contains("/v1/auth/verificar-acesso");
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}

