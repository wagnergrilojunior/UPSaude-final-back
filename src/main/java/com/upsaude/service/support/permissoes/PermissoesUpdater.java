package com.upsaude.service.support.permissoes;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.PermissoesRequest;
import com.upsaude.entity.sistema.Permissoes;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.sistema.PermissoesMapper;
import com.upsaude.repository.sistema.PermissoesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissoesUpdater {

    private final PermissoesRepository repository;
    private final PermissoesMapper mapper;
    private final PermissoesTenantEnforcer tenantEnforcer;
    private final PermissoesValidationService validationService;

    public Permissoes atualizar(UUID id, PermissoesRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Permissoes entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        Permissoes saved = repository.save(Objects.requireNonNull(entity));
        log.info("Permissão atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

