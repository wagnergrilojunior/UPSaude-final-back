package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.ReservaOrcamentariaAssistencialRequest;
import com.upsaude.api.response.financeiro.ReservaOrcamentariaAssistencialResponse;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import com.upsaude.repository.financeiro.ReservaOrcamentariaAssistencialRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.ReservaOrcamentariaAssistencialService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaDomainService;
import com.upsaude.service.api.support.financeiro.reserva.ReservaOrcamentariaAssistencialCreator;
import com.upsaude.service.api.support.financeiro.reserva.ReservaOrcamentariaAssistencialDomainService;
import com.upsaude.service.api.support.financeiro.reserva.ReservaOrcamentariaAssistencialResponseBuilder;
import com.upsaude.service.api.support.financeiro.reserva.ReservaOrcamentariaAssistencialTenantEnforcer;
import com.upsaude.service.api.support.financeiro.reserva.ReservaOrcamentariaAssistencialUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaOrcamentariaAssistencialServiceImpl implements ReservaOrcamentariaAssistencialService {

    private final ReservaOrcamentariaAssistencialRepository repository;
    private final OrcamentoCompetenciaRepository orcamentoRepository;
    private final OrcamentoCompetenciaDomainService orcamentoDomainService;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final ReservaOrcamentariaAssistencialTenantEnforcer tenantEnforcer;
    private final ReservaOrcamentariaAssistencialCreator creator;
    private final ReservaOrcamentariaAssistencialUpdater updater;
    private final ReservaOrcamentariaAssistencialResponseBuilder responseBuilder;
    private final ReservaOrcamentariaAssistencialDomainService domainService;

    @Override
    @Transactional
    public ReservaOrcamentariaAssistencialResponse criar(ReservaOrcamentariaAssistencialRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        ReservaOrcamentariaAssistencial saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaOrcamentariaAssistencialResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservaOrcamentariaAssistencialResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public ReservaOrcamentariaAssistencialResponse atualizar(UUID id, ReservaOrcamentariaAssistencialRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        ReservaOrcamentariaAssistencial updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ReservaOrcamentariaAssistencial entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);

            OrcamentoCompetencia orcamento = orcamentoRepository.findByTenantAndCompetencia(tenantId, entity.getCompetencia().getId()).orElse(null);
            if (orcamento != null) {
                BigDecimal reservas = orcamento.getReservasAtivas() != null ? orcamento.getReservasAtivas() : BigDecimal.ZERO;
                BigDecimal valor = entity.getValorReservadoTotal() != null ? entity.getValorReservadoTotal() : BigDecimal.ZERO;
                orcamento.setReservasAtivas(reservas.subtract(valor));
                orcamentoDomainService.aplicarDefaults(orcamento);
                orcamentoRepository.save(orcamento);
            }

            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir reserva orçamentária", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ReservaOrcamentariaAssistencial entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar reserva orçamentária", e);
        }
    }
}

