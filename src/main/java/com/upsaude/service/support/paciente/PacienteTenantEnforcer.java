package com.upsaude.service.support.paciente;

import com.upsaude.entity.Paciente;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteTenantEnforcer {

    private final PacienteRepository pacienteRepository;

    public Paciente validarAcesso(UUID id, UUID tenantId) {
        log.debug("Validando acesso ao paciente ID: {} para tenant: {}", id, tenantId);
        
        return pacienteRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Paciente não encontrado com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Paciente não encontrado com ID: " + id);
                });
    }

    public Paciente validarAcessoCompleto(UUID id, UUID tenantId) {
        log.debug("Validando acesso completo ao paciente ID: {} para tenant: {}", id, tenantId);
        
        return pacienteRepository.findByIdCompletoAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Paciente não encontrado com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Paciente não encontrado com ID: " + id);
                });
    }
}
