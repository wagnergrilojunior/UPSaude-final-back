package com.upsaude.service.support.atividadeprofissional;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.AtividadeProfissionalRequest;
import com.upsaude.entity.profissional.AtividadeProfissional;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.profissional.AtividadeProfissionalMapper;
import com.upsaude.repository.profissional.AtividadeProfissionalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtividadeProfissionalUpdater {

    private final AtividadeProfissionalRepository repository;
    private final AtividadeProfissionalMapper mapper;
    private final AtividadeProfissionalTenantEnforcer tenantEnforcer;
    private final AtividadeProfissionalValidationService validationService;
    private final AtividadeProfissionalRelacionamentosHandler relacionamentosHandler;

    public AtividadeProfissional atualizar(UUID id, AtividadeProfissionalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarConsistencia(request);

        AtividadeProfissional entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        AtividadeProfissional saved = repository.save(Objects.requireNonNull(entity));
        log.info("Atividade profissional atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

