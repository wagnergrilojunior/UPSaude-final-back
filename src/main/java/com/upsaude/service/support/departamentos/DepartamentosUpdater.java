package com.upsaude.service.support.departamentos;

import com.upsaude.api.request.DepartamentosRequest;
import com.upsaude.entity.Departamentos;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.DepartamentosMapper;
import com.upsaude.repository.DepartamentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartamentosUpdater {

    private final DepartamentosRepository repository;
    private final DepartamentosMapper mapper;
    private final DepartamentosTenantEnforcer tenantEnforcer;
    private final DepartamentosValidationService validationService;
    private final DepartamentosRelacionamentosHandler relacionamentosHandler;

    public Departamentos atualizar(UUID id, DepartamentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Departamentos entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar departamento"));

        relacionamentosHandler.processarEstabelecimento(request, entity, tenantId);

        Departamentos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Departamento atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

