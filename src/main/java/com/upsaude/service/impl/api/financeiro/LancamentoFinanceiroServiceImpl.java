package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroRequest;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroResponse;
import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.LancamentoFinanceiroRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.LancamentoFinanceiroService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.lancamento.LancamentoFinanceiroCreator;
import com.upsaude.service.api.support.financeiro.lancamento.LancamentoFinanceiroDomainService;
import com.upsaude.service.api.support.financeiro.lancamento.LancamentoFinanceiroResponseBuilder;
import com.upsaude.service.api.support.financeiro.lancamento.LancamentoFinanceiroTenantEnforcer;
import com.upsaude.service.api.support.financeiro.lancamento.LancamentoFinanceiroUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LancamentoFinanceiroServiceImpl implements LancamentoFinanceiroService {

    private final LancamentoFinanceiroRepository repository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final LancamentoFinanceiroTenantEnforcer tenantEnforcer;
    private final LancamentoFinanceiroCreator creator;
    private final LancamentoFinanceiroUpdater updater;
    private final LancamentoFinanceiroResponseBuilder responseBuilder;
    private final LancamentoFinanceiroDomainService domainService;

    @Override
    @Transactional
    public LancamentoFinanceiroResponse criar(LancamentoFinanceiroRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);
        LancamentoFinanceiro saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LancamentoFinanceiroResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LancamentoFinanceiroResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public LancamentoFinanceiroResponse atualizar(UUID id, LancamentoFinanceiroRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);
        LancamentoFinanceiro updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            LancamentoFinanceiro entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir lançamento financeiro", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            LancamentoFinanceiro entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar lançamento financeiro", e);
        }
    }
}

