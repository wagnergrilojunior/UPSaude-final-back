package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.ContaContabilRequest;
import com.upsaude.api.response.financeiro.ContaContabilResponse;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.ContaContabilService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.contacontabil.ContaContabilCreator;
import com.upsaude.service.api.support.financeiro.contacontabil.ContaContabilDomainService;
import com.upsaude.service.api.support.financeiro.contacontabil.ContaContabilResponseBuilder;
import com.upsaude.service.api.support.financeiro.contacontabil.ContaContabilTenantEnforcer;
import com.upsaude.service.api.support.financeiro.contacontabil.ContaContabilUpdater;
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
public class ContaContabilServiceImpl implements ContaContabilService {

    private final ContaContabilRepository repository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final ContaContabilTenantEnforcer tenantEnforcer;
    private final ContaContabilCreator creator;
    private final ContaContabilUpdater updater;
    private final ContaContabilResponseBuilder responseBuilder;
    private final ContaContabilDomainService domainService;

    @Override
    @Transactional
    public ContaContabilResponse criar(ContaContabilRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }
        ContaContabil saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ContaContabilResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        ContaContabil entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContaContabilResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public ContaContabilResponse atualizar(UUID id, ContaContabilRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }
        ContaContabil updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ContaContabil entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir conta contábil", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ContaContabil entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar conta contábil", e);
        }
    }
}

