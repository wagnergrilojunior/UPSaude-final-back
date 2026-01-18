package com.upsaude.security;

import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
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
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

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
            log.debug("Validando token JWT para path: {}", path);

            // Valida o token com o Supabase e obtém informações do usuário
            SupabaseAuthResponse.User user = supabaseAuthService.verifyToken(token);

            if (user != null) {
                log.debug("Token válido para usuário: {}, userId: {}", user.getEmail(), user.getId());

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
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user.getId().toString(),
                        token,
                        authorities);

                // 5. Adiciona detalhes do usuário
                authentication.setDetails(user);

                // 6. Valida se o usuário tem acesso ao sistema (UsuariosSistema criado)
                // Exceto para o endpoint de verificar acesso, que precisa ser acessível mesmo
                // sem UsuariosSistema
                if (!isVerificarAcessoEndpoint(path)) {
                    boolean temAcesso = usuariosSistemaRepository.findByUserId(user.getId())
                            .map(usuario -> usuario.getAtivo() != null && usuario.getAtivo())
                            .orElse(false);

                    if (!temAcesso) {
                        log.warn("Usuário {} não tem acesso ao sistema (UsuariosSistema não encontrado ou inativo)",
                                user.getId());
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setContentType("application/json");
                        response.getWriter().write(
                                "{\"erro\": \"Usuário não tem acesso ao sistema. É necessário criar um registro em UsuariosSistema.\"}");
                        return;
                    }
                    log.debug("Usuário {} tem acesso ao sistema confirmado", user.getId());
                }

                // 7. Define o authentication no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Autenticação definida no contexto de segurança para: {}", user.getEmail());
            } else {
                log.warn("Token verificado mas usuário retornado é null - limpando contexto de segurança");
                SecurityContextHolder.clearContext();
            }
        } catch (com.upsaude.exception.UnauthorizedException e) {
            log.warn("Token inválido ou expirado para path {}: {}", path, e.getMessage());
            SecurityContextHolder.clearContext();
            // Retorna 401 imediatamente para erros de autenticação
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\": \"" + e.getMessage() + "\"}");
            return;
        } catch (Exception e) {
            log.error("Erro inesperado ao validar token para path {}: {} - {}", path, e.getClass().getSimpleName(),
                    e.getMessage(), e);
            SecurityContextHolder.clearContext();
            // Continua o filter chain - o Spring Security vai retornar 403
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        if (path == null) {
            return false;
        }

        // Endpoint /error usado pelo Spring Boot para tratamento de erros
        boolean isErrorEndpoint = path.equals("/error") ||
                path.equals("/api/error") ||
                path.startsWith("/error/") ||
                path.startsWith("/api/error/");

        return path.contains("/v1/auth/login") ||
                path.contains("/v1/auth/refresh") ||
                isErrorEndpoint ||
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
