package com.upsaude.service.api.support.financeiro.estorno;

import com.upsaude.api.request.financeiro.EstornoFinanceiroRequest;
import com.upsaude.entity.financeiro.EstornoFinanceiro;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.EstornoFinanceiroMapper;
import com.upsaude.repository.financeiro.EstornoFinanceiroRepository;
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
public class EstornoFinanceiroCreator {

    private final EstornoFinanceiroRepository repository;
    private final OrcamentoCompetenciaRepository orcamentoRepository;
    private final OrcamentoCompetenciaDomainService orcamentoDomainService;
    private final EstornoFinanceiroMapper mapper;
    private final EstornoFinanceiroRelacionamentosHandler relacionamentosHandler;

    public EstornoFinanceiro criar(EstornoFinanceiroRequest request, UUID tenantId, Tenant tenant) {
        EstornoFinanceiro entity = mapper.fromRequest(request);
        entity.setActive(true);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        EstornoFinanceiro saved = repository.save(Objects.requireNonNull(entity));

        OrcamentoCompetencia orcamento = orcamentoRepository.findByTenantAndCompetencia(tenantId, request.getCompetencia())
                .orElse(null);
        if (orcamento != null) {
            BigDecimal estornos = orcamento.getEstornos() != null ? orcamento.getEstornos() : BigDecimal.ZERO;
            BigDecimal valor = saved.getValorEstornado() != null ? saved.getValorEstornado() : BigDecimal.ZERO;
            orcamento.setEstornos(estornos.add(valor));
            orcamentoDomainService.aplicarDefaults(orcamento);
            orcamentoRepository.save(orcamento);
        }

        log.info("Estorno financeiro criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

