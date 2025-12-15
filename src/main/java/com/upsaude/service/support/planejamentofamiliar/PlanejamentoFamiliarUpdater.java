package com.upsaude.service.support.planejamentofamiliar;

import com.upsaude.api.request.PlanejamentoFamiliarRequest;
import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.PlanejamentoFamiliarMapper;
import com.upsaude.repository.PlanejamentoFamiliarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanejamentoFamiliarUpdater {

    private final PlanejamentoFamiliarRepository repository;
    private final PlanejamentoFamiliarMapper mapper;
    private final PlanejamentoFamiliarTenantEnforcer tenantEnforcer;
    private final PlanejamentoFamiliarValidationService validationService;
    private final PlanejamentoFamiliarRelacionamentosHandler relacionamentosHandler;

    public PlanejamentoFamiliar atualizar(UUID id, PlanejamentoFamiliarRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarConsistencias(request);

        PlanejamentoFamiliar entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        PlanejamentoFamiliar saved = repository.save(Objects.requireNonNull(entity));
        log.info("Planejamento familiar atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

