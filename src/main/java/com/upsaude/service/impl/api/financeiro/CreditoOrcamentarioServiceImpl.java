package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.CreditoOrcamentarioRequest;
import com.upsaude.api.response.financeiro.CreditoOrcamentarioResponse;
import com.upsaude.entity.financeiro.CreditoOrcamentario;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.CreditoOrcamentarioRepository;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.CreditoOrcamentarioService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.credito.CreditoOrcamentarioCreator;
import com.upsaude.service.api.support.financeiro.credito.CreditoOrcamentarioDomainService;
import com.upsaude.service.api.support.financeiro.credito.CreditoOrcamentarioResponseBuilder;
import com.upsaude.service.api.support.financeiro.credito.CreditoOrcamentarioTenantEnforcer;
import com.upsaude.service.api.support.financeiro.credito.CreditoOrcamentarioUpdater;
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
public class CreditoOrcamentarioServiceImpl implements CreditoOrcamentarioService {

    private final CreditoOrcamentarioRepository repository;
    private final OrcamentoCompetenciaRepository orcamentoRepository;
    private final OrcamentoCompetenciaDomainService orcamentoDomainService;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final CreditoOrcamentarioTenantEnforcer tenantEnforcer;
    private final CreditoOrcamentarioCreator creator;
    private final CreditoOrcamentarioUpdater updater;
    private final CreditoOrcamentarioResponseBuilder responseBuilder;
    private final CreditoOrcamentarioDomainService domainService;

    @Override
    @Transactional
    public CreditoOrcamentarioResponse criar(CreditoOrcamentarioRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        CreditoOrcamentario saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CreditoOrcamentarioResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CreditoOrcamentarioResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public CreditoOrcamentarioResponse atualizar(UUID id, CreditoOrcamentarioRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        CreditoOrcamentario updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            CreditoOrcamentario entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            // Ajusta orçamento (se existir)
            OrcamentoCompetencia orcamento = orcamentoRepository.findByTenantAndCompetencia(tenantId, entity.getCompetencia().getId()).orElse(null);
            if (orcamento != null) {
                BigDecimal creditosAtuais = orcamento.getCreditos() != null ? orcamento.getCreditos() : BigDecimal.ZERO;
                BigDecimal valor = entity.getValor() != null ? entity.getValor() : BigDecimal.ZERO;
                orcamento.setCreditos(creditosAtuais.subtract(valor));
                orcamentoDomainService.aplicarDefaults(orcamento);
                orcamentoRepository.save(orcamento);
            }
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir crédito orçamentário", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            CreditoOrcamentario entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar crédito orçamentário", e);
        }
    }
}

