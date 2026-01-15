package com.upsaude.service.api.support.financeiro.competencia;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.CompetenciaFinanceiraMapper;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraCreator {

    private final CompetenciaFinanceiraRepository repository;
    private final CompetenciaFinanceiraMapper mapper;
    private final CompetenciaFinanceiraValidationService validationService;

    public CompetenciaFinanceira criar(CompetenciaFinanceiraRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        CompetenciaFinanceira entity = mapper.fromRequest(request);
        entity.setTenant(tenant);
        entity.setActive(true);

        if (entity.getStatus() == null || entity.getStatus().isEmpty()) {
            entity.setStatus("ABERTA");
        }

        CompetenciaFinanceira saved = repository.save(Objects.requireNonNull(entity));
        log.info("Competência financeira criada com sucesso. ID: {}, Tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

