package com.upsaude.service.api.support.prontuarios;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.prontuario.ProntuariosRequest;
import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.clinica.prontuario.ProntuariosMapper;
import com.upsaude.repository.clinica.prontuario.ProntuariosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuariosUpdater {

    private final ProntuariosRepository repository;
    private final ProntuariosMapper mapper;
    private final ProntuariosTenantEnforcer tenantEnforcer;
    private final ProntuariosValidationService validationService;
    private final ProntuariosRelacionamentosHandler relacionamentosHandler;

    public Prontuarios atualizar(UUID id, ProntuariosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Prontuarios entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Prontuarios saved = repository.save(Objects.requireNonNull(entity));
        log.info("Prontuário atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

