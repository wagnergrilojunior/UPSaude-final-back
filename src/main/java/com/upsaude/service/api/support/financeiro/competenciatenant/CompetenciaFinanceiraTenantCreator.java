package com.upsaude.service.api.support.financeiro.competenciatenant;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraTenantRequest;
import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.CompetenciaFinanceiraTenantMapper;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraTenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraTenantCreator {

    private final CompetenciaFinanceiraTenantRepository repository;
    private final CompetenciaFinanceiraTenantMapper mapper;
    private final CompetenciaFinanceiraTenantValidationService validationService;
    private final CompetenciaFinanceiraTenantRelacionamentosHandler relacionamentosHandler;

    public CompetenciaFinanceiraTenant criar(CompetenciaFinanceiraTenantRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        CompetenciaFinanceiraTenant entity = mapper.fromRequest(request);
        entity.setActive(true);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        CompetenciaFinanceiraTenant saved = repository.save(Objects.requireNonNull(entity));
        log.info("Competência (tenant) criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

