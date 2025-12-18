package com.upsaude.service.support.responsavellegal;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.paciente.ResponsavelLegalRequest;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.ConflictException;
import com.upsaude.mapper.paciente.ResponsavelLegalMapper;
import com.upsaude.repository.paciente.ResponsavelLegalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponsavelLegalCreator {

    private final ResponsavelLegalRepository repository;
    private final ResponsavelLegalMapper mapper;
    private final ResponsavelLegalValidationService validationService;
    private final ResponsavelLegalRelacionamentosHandler relacionamentosHandler;

    public ResponsavelLegal criar(ResponsavelLegalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        repository.findByPacienteIdAndTenantId(request.getPaciente(), tenantId)
            .ifPresent(d -> {
                throw new ConflictException("Responsável legal já existe para este paciente. ID do responsável existente: " + d.getId());
            });

        ResponsavelLegal entity = mapper.fromRequest(request);
        entity.setActive(true);

        entity.setCpf(validationService.normalizarCpf(request.getCpf()));
        entity.setTelefone(validationService.normalizarTelefone(request.getTelefone()));

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ResponsavelLegal saved = repository.save(Objects.requireNonNull(entity));
        log.info("Responsável legal criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

