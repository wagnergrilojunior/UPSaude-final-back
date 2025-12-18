package com.upsaude.service.support.planejamentofamiliar;

import com.upsaude.api.request.planejamento.PlanejamentoFamiliarRequest;
import com.upsaude.entity.planejamento.PlanejamentoFamiliar;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.PlanejamentoFamiliarMapper;
import com.upsaude.repository.saude_publica.planejamento.PlanejamentoFamiliarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanejamentoFamiliarCreator {

    private final PlanejamentoFamiliarRepository repository;
    private final PlanejamentoFamiliarMapper mapper;
    private final PlanejamentoFamiliarValidationService validationService;
    private final PlanejamentoFamiliarDomainService domainService;
    private final PlanejamentoFamiliarRelacionamentosHandler relacionamentosHandler;

    public PlanejamentoFamiliar criar(PlanejamentoFamiliarRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarConsistencias(request);

        PlanejamentoFamiliar entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaultsNaCriacao(entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        PlanejamentoFamiliar saved = repository.save(Objects.requireNonNull(entity));
        log.info("Planejamento familiar criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

