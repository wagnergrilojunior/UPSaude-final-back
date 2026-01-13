package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraTenantRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraTenantResponse;
import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraTenantRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.CompetenciaFinanceiraTenantService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.competenciatenant.CompetenciaFinanceiraTenantCreator;
import com.upsaude.service.api.support.financeiro.competenciatenant.CompetenciaFinanceiraTenantDomainService;
import com.upsaude.service.api.support.financeiro.competenciatenant.CompetenciaFinanceiraTenantResponseBuilder;
import com.upsaude.service.api.support.financeiro.competenciatenant.CompetenciaFinanceiraTenantTenantEnforcer;
import com.upsaude.service.api.support.financeiro.competenciatenant.CompetenciaFinanceiraTenantUpdater;
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
public class CompetenciaFinanceiraTenantServiceImpl implements CompetenciaFinanceiraTenantService {

    private final CompetenciaFinanceiraTenantRepository repository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final CompetenciaFinanceiraTenantTenantEnforcer tenantEnforcer;
    private final CompetenciaFinanceiraTenantCreator creator;
    private final CompetenciaFinanceiraTenantUpdater updater;
    private final CompetenciaFinanceiraTenantResponseBuilder responseBuilder;
    private final CompetenciaFinanceiraTenantDomainService domainService;

    @Override
    @Transactional
    public CompetenciaFinanceiraTenantResponse criar(CompetenciaFinanceiraTenantRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }
        CompetenciaFinanceiraTenant saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CompetenciaFinanceiraTenantResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        CompetenciaFinanceiraTenant entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetenciaFinanceiraTenantResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public CompetenciaFinanceiraTenantResponse atualizar(UUID id, CompetenciaFinanceiraTenantRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }
        CompetenciaFinanceiraTenant updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            CompetenciaFinanceiraTenant entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir competência (tenant)", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            CompetenciaFinanceiraTenant entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar competência (tenant)", e);
        }
    }
}

