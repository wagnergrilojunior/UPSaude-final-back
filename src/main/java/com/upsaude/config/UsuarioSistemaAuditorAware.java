package com.upsaude.config;

import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component("auditorAware")
@RequiredArgsConstructor
public class UsuarioSistemaAuditorAware implements AuditorAware<UsuariosSistema> {

    private final UsuariosSistemaRepository usuariosSistemaRepository;
    
    // Cache ThreadLocal para evitar múltiplas queries durante a mesma transação
    // e prevenir loops infinitos quando o auditor é chamado durante flush do Hibernate
    private static final ThreadLocal<Optional<UsuariosSistema>> auditorCache = new ThreadLocal<>();

    @Override
    public Optional<UsuariosSistema> getCurrentAuditor() {
        // Verifica cache primeiro para evitar queries desnecessárias
        Optional<UsuariosSistema> cached = auditorCache.get();
        if (cached != null) {
            return cached;
        }
        
        UUID userId = obterUserIdAutenticado();
        if (userId == null) {
            Optional<UsuariosSistema> empty = Optional.empty();
            auditorCache.set(empty);
            return empty;
        }
        
        try {
            Optional<UsuariosSistema> auditor = usuariosSistemaRepository.findByUserId(userId);
            if (auditor == null) {
                auditor = Optional.empty();
            }
            auditorCache.set(auditor);
            return auditor;
        } catch (Exception e) {
            log.warn("Erro ao buscar auditor, retornando empty: {}", e.getMessage());
            Optional<UsuariosSistema> empty = Optional.empty();
            auditorCache.set(empty);
            return empty;
        }
    }
    
    /**
     * Limpa o cache do auditor. Deve ser chamado no final da transação.
     */
    public static void clearCache() {
        auditorCache.remove();
    }

    private UUID obterUserIdAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object details = authentication.getDetails();
        if (details instanceof com.upsaude.integration.supabase.SupabaseAuthResponse.User) {
            return ((com.upsaude.integration.supabase.SupabaseAuthResponse.User) details).getId();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof String) {
            try {
                return UUID.fromString((String) principal);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}

