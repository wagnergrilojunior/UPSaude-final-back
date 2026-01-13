package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.ContaFinanceiraRequest;
import com.upsaude.api.response.financeiro.ContaFinanceiraResponse;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.ContaFinanceiraService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.contafinanceira.ContaFinanceiraCreator;
import com.upsaude.service.api.support.financeiro.contafinanceira.ContaFinanceiraDomainService;
import com.upsaude.service.api.support.financeiro.contafinanceira.ContaFinanceiraResponseBuilder;
import com.upsaude.service.api.support.financeiro.contafinanceira.ContaFinanceiraTenantEnforcer;
import com.upsaude.service.api.support.financeiro.contafinanceira.ContaFinanceiraUpdater;
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
public class ContaFinanceiraServiceImpl implements ContaFinanceiraService {

    private final ContaFinanceiraRepository repository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final ContaFinanceiraTenantEnforcer tenantEnforcer;
    private final ContaFinanceiraCreator creator;
    private final ContaFinanceiraUpdater updater;
    private final ContaFinanceiraResponseBuilder responseBuilder;
    private final ContaFinanceiraDomainService domainService;

    @Override
    @Transactional
    public ContaFinanceiraResponse criar(ContaFinanceiraRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }
        ContaFinanceira saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ContaFinanceiraResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        ContaFinanceira entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContaFinanceiraResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public ContaFinanceiraResponse atualizar(UUID id, ContaFinanceiraRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }
        ContaFinanceira updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ContaFinanceira entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir conta financeira", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ContaFinanceira entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar conta financeira", e);
        }
    }
}

