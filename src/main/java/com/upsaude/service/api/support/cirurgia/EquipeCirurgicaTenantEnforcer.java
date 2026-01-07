package com.upsaude.service.api.support.cirurgia;

import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.cirurgia.EquipeCirurgicaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeCirurgicaTenantEnforcer {

    private final EquipeCirurgicaRepository repository;

    public EquipeCirurgica validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso à equipe cirúrgica. ID: {}, tenant: {}", id, tenantId);
        return repository.findByIdAndTenant(id, tenantId)
            .orElseThrow(() -> {
                log.warn("Equipe cirúrgica não encontrada. ID: {}, tenant: {}", id, tenantId);
                return new NotFoundException("Equipe cirúrgica não encontrada com ID: " + id);
            });
    }

    public EquipeCirurgica validarAcessoCompleto(UUID id, UUID tenantId) {
        return validarAcesso(id, tenantId);
    }
}

