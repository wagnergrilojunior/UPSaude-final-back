package com.upsaude.service.support.medico;

import com.upsaude.entity.profissional.Medicos;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.MedicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoTenantEnforcer {

    private final MedicosRepository medicosRepository;

    public Medicos validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao médico ID: {} para tenant: {}", id, tenantId);
        
        return medicosRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Médico não encontrado com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Médico não encontrado com ID: " + id);
                });
    }

    public Medicos validarAcessoCompleto(UUID id, UUID tenantId) {
        log.debug("Validando acesso completo ao médico ID: {} para tenant: {}", id, tenantId);
        
        return medicosRepository.findByIdCompletoAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Médico não encontrado com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Médico não encontrado com ID: " + id);
                });
    }
}
