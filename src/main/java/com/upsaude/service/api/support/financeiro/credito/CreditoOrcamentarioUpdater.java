package com.upsaude.service.api.support.financeiro.credito;

import com.upsaude.api.request.financeiro.CreditoOrcamentarioRequest;
import com.upsaude.entity.financeiro.CreditoOrcamentario;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.CreditoOrcamentarioMapper;
import com.upsaude.repository.financeiro.CreditoOrcamentarioRepository;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditoOrcamentarioUpdater {

    private final CreditoOrcamentarioRepository repository;
    private final OrcamentoCompetenciaRepository orcamentoRepository;
    private final CreditoOrcamentarioMapper mapper;
    private final CreditoOrcamentarioTenantEnforcer tenantEnforcer;
    private final CreditoOrcamentarioRelacionamentosHandler relacionamentosHandler;
    private final CreditoOrcamentarioDomainService domainService;
    private final OrcamentoCompetenciaDomainService orcamentoDomainService;

    public CreditoOrcamentario atualizar(UUID id, CreditoOrcamentarioRequest request, UUID tenantId, Tenant tenant) {
        CreditoOrcamentario entity = tenantEnforcer.validarAcesso(id, tenantId);
        BigDecimal valorAnterior = entity.getValor() != null ? entity.getValor() : BigDecimal.ZERO;

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);
        domainService.aplicarDefaults(entity);

        CreditoOrcamentario saved = repository.save(Objects.requireNonNull(entity));

        OrcamentoCompetencia orcamento = orcamentoRepository.findByTenantAndCompetencia(tenantId, request.getCompetencia())
                .orElse(null);
        if (orcamento != null) {
            BigDecimal creditosAtuais = orcamento.getCreditos() != null ? orcamento.getCreditos() : BigDecimal.ZERO;
            BigDecimal delta = (saved.getValor() != null ? saved.getValor() : BigDecimal.ZERO).subtract(valorAnterior);
            orcamento.setCreditos(creditosAtuais.add(delta));
            orcamentoDomainService.aplicarDefaults(orcamento);
            orcamentoRepository.save(orcamento);
        }

        log.info("Crédito orçamentário atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

