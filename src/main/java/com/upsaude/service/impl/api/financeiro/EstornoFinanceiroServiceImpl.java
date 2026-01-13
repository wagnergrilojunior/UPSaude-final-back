package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.EstornoFinanceiroRequest;
import com.upsaude.api.response.financeiro.EstornoFinanceiroResponse;
import com.upsaude.entity.financeiro.EstornoFinanceiro;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.EstornoFinanceiroRepository;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.EstornoFinanceiroService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.estorno.EstornoFinanceiroCreator;
import com.upsaude.service.api.support.financeiro.estorno.EstornoFinanceiroDomainService;
import com.upsaude.service.api.support.financeiro.estorno.EstornoFinanceiroResponseBuilder;
import com.upsaude.service.api.support.financeiro.estorno.EstornoFinanceiroTenantEnforcer;
import com.upsaude.service.api.support.financeiro.estorno.EstornoFinanceiroUpdater;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaDomainService;
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
public class EstornoFinanceiroServiceImpl implements EstornoFinanceiroService {

    private final EstornoFinanceiroRepository repository;
    private final OrcamentoCompetenciaRepository orcamentoRepository;
    private final OrcamentoCompetenciaDomainService orcamentoDomainService;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final EstornoFinanceiroTenantEnforcer tenantEnforcer;
    private final EstornoFinanceiroCreator creator;
    private final EstornoFinanceiroUpdater updater;
    private final EstornoFinanceiroResponseBuilder responseBuilder;
    private final EstornoFinanceiroDomainService domainService;

    @Override
    @Transactional
    public EstornoFinanceiroResponse criar(EstornoFinanceiroRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);
        EstornoFinanceiro saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EstornoFinanceiroResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstornoFinanceiroResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public EstornoFinanceiroResponse atualizar(UUID id, EstornoFinanceiroRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);
        EstornoFinanceiro updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            EstornoFinanceiro entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            OrcamentoCompetencia orcamento = orcamentoRepository.findByTenantAndCompetencia(tenantId, entity.getCompetencia().getId()).orElse(null);
            if (orcamento != null) {
                BigDecimal estornos = orcamento.getEstornos() != null ? orcamento.getEstornos() : BigDecimal.ZERO;
                BigDecimal valor = entity.getValorEstornado() != null ? entity.getValorEstornado() : BigDecimal.ZERO;
                orcamento.setEstornos(estornos.subtract(valor));
                orcamentoDomainService.aplicarDefaults(orcamento);
                orcamentoRepository.save(orcamento);
            }
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir estorno financeiro", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            EstornoFinanceiro entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar estorno financeiro", e);
        }
    }
}

