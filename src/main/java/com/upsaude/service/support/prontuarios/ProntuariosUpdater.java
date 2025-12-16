package com.upsaude.service.support.prontuarios;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.entity.Prontuarios;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ProntuariosMapper;
import com.upsaude.repository.ProntuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

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

