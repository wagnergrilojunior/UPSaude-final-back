package com.upsaude.service.support.plantao;

import com.upsaude.api.request.equipe.PlantaoRequest;
import com.upsaude.entity.equipe.Plantao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.PlantaoMapper;
import com.upsaude.repository.profissional.equipe.PlantaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantaoUpdater {

    private final PlantaoRepository repository;
    private final PlantaoMapper mapper;
    private final PlantaoTenantEnforcer tenantEnforcer;
    private final PlantaoValidationService validationService;
    private final PlantaoDomainService domainService;
    private final PlantaoRelacionamentosHandler relacionamentosHandler;

    public Plantao atualizar(UUID id, PlantaoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Plantao entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);
        domainService.validarPeriodo(entity);

        Plantao saved = repository.save(Objects.requireNonNull(entity));
        log.info("Plantão atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

