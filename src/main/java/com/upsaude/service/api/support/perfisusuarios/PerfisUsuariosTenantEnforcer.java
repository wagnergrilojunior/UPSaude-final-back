package com.upsaude.service.api.support.perfisusuarios;

import com.upsaude.entity.sistema.usuario.PerfisUsuarios;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.usuario.PerfisUsuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerfisUsuariosTenantEnforcer {

    private final PerfisUsuariosRepository repository;

    public PerfisUsuarios validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao perfil de usuário. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Perfil de usuário não encontrado. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Perfil de usuário não encontrado com ID: " + id);
            });
    }

    public PerfisUsuarios validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

