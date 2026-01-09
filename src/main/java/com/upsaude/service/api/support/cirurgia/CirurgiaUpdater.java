package com.upsaude.service.api.support.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaRequest;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.clinica.cirurgia.CirurgiaMapper;
import com.upsaude.repository.clinica.cirurgia.CirurgiaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CirurgiaUpdater {

    private final CirurgiaRepository repository;
    private final CirurgiaMapper mapper;
    private final CirurgiaTenantEnforcer tenantEnforcer;
    private final CirurgiaValidationService validationService;
    private final CirurgiaRelacionamentosHandler relacionamentosHandler;

    public Cirurgia atualizar(UUID id, CirurgiaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Cirurgia entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar cirurgia"));

        relacionamentosHandler.processarRelacionamentos(entity, request, tenantId, tenant);

        Cirurgia updated = repository.save(Objects.requireNonNull(entity));
        log.info("Cirurgia atualizada com sucesso. ID: {}, tenant: {}", updated.getId(), tenantId);
        return updated;
    }
}
