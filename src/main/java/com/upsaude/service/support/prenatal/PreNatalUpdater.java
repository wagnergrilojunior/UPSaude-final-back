package com.upsaude.service.support.prenatal;

import com.upsaude.api.request.planejamento.PreNatalRequest;
import com.upsaude.entity.planejamento.PreNatal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.PreNatalMapper;
import com.upsaude.repository.saude_publica.planejamento.PreNatalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreNatalUpdater {

    private final PreNatalRepository repository;
    private final PreNatalMapper mapper;
    private final PreNatalTenantEnforcer tenantEnforcer;
    private final PreNatalValidationService validationService;
    private final PreNatalRelacionamentosHandler relacionamentosHandler;

    public PreNatal atualizar(UUID id, PreNatalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        PreNatal entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        PreNatal saved = repository.save(Objects.requireNonNull(entity));
        log.info("Pré-natal atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

