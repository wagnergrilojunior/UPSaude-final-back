package com.upsaude.service.api.support.paciente;

import com.upsaude.entity.paciente.Paciente;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.PacienteRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteTenantEnforcer {

    private final PacienteRepository pacienteRepository;
    private final EntityManager entityManager;

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

        Paciente paciente = pacienteRepository.findByIdCompletoAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Paciente não encontrado com ID: {} para tenant: {}", id, tenantId);
                    return new NotFoundException("Paciente não encontrado com ID: " + id);
                });

        // Carregar relacionamentos usando EntityGraph
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("Paciente.prontuarioCompleto");
        Map<String, Object> hints = new HashMap<>();
        hints.put("jakarta.persistence.fetchgraph", entityGraph);
        
        // Recarregar o paciente com os relacionamentos
        return entityManager.find(Paciente.class, paciente.getId(), hints);
    }
}

