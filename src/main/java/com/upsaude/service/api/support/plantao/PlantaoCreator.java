package com.upsaude.service.api.support.plantao;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.equipe.PlantaoRequest;
import com.upsaude.entity.profissional.equipe.Plantao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.profissional.equipe.PlantaoMapper;
import com.upsaude.repository.profissional.equipe.PlantaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantaoCreator {

    private final PlantaoRepository repository;
    private final PlantaoMapper mapper;
    private final PlantaoValidationService validationService;
    private final PlantaoRelacionamentosHandler relacionamentosHandler;

    public Plantao criar(PlantaoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Plantao entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Plantao saved = repository.save(Objects.requireNonNull(entity));
        log.info("Plantão criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
