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
public class CreditoOrcamentarioCreator {

    private final CreditoOrcamentarioRepository repository;
    private final OrcamentoCompetenciaRepository orcamentoRepository;
    private final CreditoOrcamentarioMapper mapper;
    private final CreditoOrcamentarioRelacionamentosHandler relacionamentosHandler;
    private final CreditoOrcamentarioDomainService domainService;
    private final OrcamentoCompetenciaDomainService orcamentoDomainService;

    public CreditoOrcamentario criar(CreditoOrcamentarioRequest request, UUID tenantId, Tenant tenant) {
        CreditoOrcamentario entity = mapper.fromRequest(request);
        entity.setActive(true);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);
        domainService.aplicarDefaults(entity);

        CreditoOrcamentario saved = repository.save(Objects.requireNonNull(entity));

        OrcamentoCompetencia orcamento = orcamentoRepository.findByTenantAndCompetencia(tenantId, request.getCompetencia())
                .orElseGet(() -> {
                    OrcamentoCompetencia o = new OrcamentoCompetencia();
                    o.setTenant(tenant);
                    o.setActive(true);
                    o.setCompetencia(saved.getCompetencia());
                    o.setSaldoAnterior(BigDecimal.ZERO);
                    o.setCreditos(BigDecimal.ZERO);
                    o.setReservasAtivas(BigDecimal.ZERO);
                    o.setConsumos(BigDecimal.ZERO);
                    o.setEstornos(BigDecimal.ZERO);
                    o.setDespesasAdmin(BigDecimal.ZERO);
                    o.setSaldoFinal(BigDecimal.ZERO);
                    orcamentoDomainService.aplicarDefaults(o);
                    return o;
                });

        BigDecimal creditosAtuais = orcamento.getCreditos() != null ? orcamento.getCreditos() : BigDecimal.ZERO;
        orcamento.setCreditos(creditosAtuais.add(saved.getValor()));
        orcamentoDomainService.aplicarDefaults(orcamento);
        orcamentoRepository.save(orcamento);

        log.info("Crédito orçamentário criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

