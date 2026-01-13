package com.upsaude.service.api.support.financeiro.reserva;

import com.upsaude.api.request.financeiro.ReservaOrcamentariaAssistencialRequest;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.ReservaOrcamentariaAssistencialMapper;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import com.upsaude.repository.financeiro.ReservaOrcamentariaAssistencialRepository;
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
public class ReservaOrcamentariaAssistencialCreator {

    private final ReservaOrcamentariaAssistencialRepository repository;
    private final OrcamentoCompetenciaRepository orcamentoRepository;
    private final OrcamentoCompetenciaDomainService orcamentoDomainService;
    private final ReservaOrcamentariaAssistencialMapper mapper;
    private final ReservaOrcamentariaAssistencialRelacionamentosHandler relacionamentosHandler;
    private final ReservaOrcamentariaAssistencialDomainService domainService;

    public ReservaOrcamentariaAssistencial criar(ReservaOrcamentariaAssistencialRequest request, UUID tenantId, Tenant tenant) {
        ReservaOrcamentariaAssistencial entity = mapper.fromRequest(request);
        entity.setActive(true);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);
        domainService.aplicarDefaults(entity);

        OrcamentoCompetencia orcamento = orcamentoRepository.findByTenantAndCompetencia(tenantId, request.getCompetencia())
                .orElseThrow(() -> new com.upsaude.exception.BadRequestException("Orçamento não encontrado para esta competência"));
        domainService.validarSaldoDisponivel(orcamento, entity.getValorReservadoTotal());

        BigDecimal reservas = orcamento.getReservasAtivas() != null ? orcamento.getReservasAtivas() : BigDecimal.ZERO;
        orcamento.setReservasAtivas(reservas.add(entity.getValorReservadoTotal()));
        orcamentoDomainService.aplicarDefaults(orcamento);
        orcamentoRepository.save(orcamento);

        ReservaOrcamentariaAssistencial saved = repository.save(Objects.requireNonNull(entity));
        log.info("Reserva orçamentária criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

