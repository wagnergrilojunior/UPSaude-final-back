package com.upsaude.config;

import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("auditorAware")
@RequiredArgsConstructor
public class UsuarioSistemaAuditorAware implements AuditorAware<UsuariosSistema> {

    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @Override
    public Optional<UsuariosSistema> getCurrentAuditor() {
        UUID userId = obterUserIdAutenticado();
        if (userId == null) {
            return Optional.empty();
        }
        return usuariosSistemaRepository.findByUserId(userId);
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

