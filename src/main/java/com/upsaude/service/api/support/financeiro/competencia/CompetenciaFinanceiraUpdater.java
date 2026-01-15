package com.upsaude.service.api.support.financeiro.competencia;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
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
public class CompetenciaFinanceiraUpdater {

    private final CompetenciaFinanceiraRepository repository;
    private final CompetenciaFinanceiraMapper mapper;
    private final CompetenciaFinanceiraValidationService validationService;

    public CompetenciaFinanceira atualizar(UUID id, CompetenciaFinanceiraRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaAtualizacao(id, request, tenantId);

        CompetenciaFinanceira entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Competência financeira não encontrada com ID: " + id));

        mapper.updateFromRequest(request, entity);

        if (entity.getTenant() == null) {
            entity.setTenant(tenant);
        }

        CompetenciaFinanceira saved = repository.save(Objects.requireNonNull(entity));
        log.info("Competência financeira atualizada com sucesso. ID: {}, Tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

