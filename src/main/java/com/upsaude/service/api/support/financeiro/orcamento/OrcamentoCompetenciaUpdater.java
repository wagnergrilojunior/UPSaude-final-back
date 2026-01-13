package com.upsaude.service.api.support.financeiro.orcamento;

import com.upsaude.api.request.financeiro.OrcamentoCompetenciaRequest;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.OrcamentoCompetenciaMapper;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrcamentoCompetenciaUpdater {

    private final OrcamentoCompetenciaRepository repository;
    private final OrcamentoCompetenciaMapper mapper;
    private final OrcamentoCompetenciaTenantEnforcer tenantEnforcer;
    private final OrcamentoCompetenciaValidationService validationService;
    private final OrcamentoCompetenciaRelacionamentosHandler relacionamentosHandler;
    private final OrcamentoCompetenciaDomainService domainService;

    public OrcamentoCompetencia atualizar(UUID id, OrcamentoCompetenciaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaAtualizacao(id, request, tenantId);

        OrcamentoCompetencia entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        domainService.aplicarDefaults(entity);
        domainService.validarSaldoNaoNegativo(entity);

        OrcamentoCompetencia saved = repository.save(Objects.requireNonNull(entity));
        log.info("Orçamento por competência atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

