package com.upsaude.service.api.support.prontuarios;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.prontuario.ProntuarioRequest;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.clinica.prontuario.ProntuarioMapper;
import com.upsaude.repository.clinica.prontuario.ProntuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuarioUpdater {

    private final ProntuarioRepository repository;
    private final ProntuarioMapper mapper;
    private final ProntuarioTenantEnforcer tenantEnforcer;
    private final ProntuarioValidationService validationService;
    private final ProntuarioRelacionamentosHandler relacionamentosHandler;

    public Prontuario atualizar(UUID id, ProntuarioRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Prontuario entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Prontuario saved = repository.save(Objects.requireNonNull(entity));
        log.info("Prontuário atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

