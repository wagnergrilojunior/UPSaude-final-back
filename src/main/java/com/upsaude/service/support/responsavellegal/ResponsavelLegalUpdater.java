package com.upsaude.service.support.responsavellegal;

import com.upsaude.api.request.paciente.ResponsavelLegalRequest;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.ConflictException;
import com.upsaude.mapper.ResponsavelLegalMapper;
import com.upsaude.repository.paciente.ResponsavelLegalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponsavelLegalUpdater {

    private final ResponsavelLegalRepository repository;
    private final ResponsavelLegalMapper mapper;
    private final ResponsavelLegalTenantEnforcer tenantEnforcer;
    private final ResponsavelLegalValidationService validationService;
    private final ResponsavelLegalRelacionamentosHandler relacionamentosHandler;

    public ResponsavelLegal atualizar(UUID id, ResponsavelLegalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ResponsavelLegal entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getPaciente() != null && entity.getPaciente() != null && !request.getPaciente().equals(entity.getPaciente().getId())) {
            repository.findByPacienteIdAndTenantId(request.getPaciente(), tenantId)
                .ifPresent(d -> {
                    if (!d.getId().equals(id)) {
                        throw new ConflictException("Responsável legal já existe para este paciente");
                    }
                });
        }

        mapper.updateFromRequest(request, entity);
        entity.setCpf(validationService.normalizarCpf(request.getCpf()));
        entity.setTelefone(validationService.normalizarTelefone(request.getTelefone()));

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ResponsavelLegal saved = repository.save(Objects.requireNonNull(entity));
        log.info("Responsável legal atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

