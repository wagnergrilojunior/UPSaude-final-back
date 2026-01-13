package com.upsaude.service.api.support.financeiro.plano;

import com.upsaude.api.request.financeiro.PlanoContasRequest;
import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.PlanoContasMapper;
import com.upsaude.repository.financeiro.PlanoContasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanoContasUpdater {

    private final PlanoContasRepository repository;
    private final PlanoContasMapper mapper;
    private final PlanoContasTenantEnforcer tenantEnforcer;
    private final PlanoContasValidationService validationService;
    private final PlanoContasRelacionamentosHandler relacionamentosHandler;
    private final PlanoContasDomainService domainService;

    public PlanoContas atualizar(UUID id, PlanoContasRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaAtualizacao(id, request, tenantId);

        PlanoContas entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        PlanoContas saved = repository.save(Objects.requireNonNull(entity));
        log.info("Plano de contas atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

