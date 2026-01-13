package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.PlanoContasRequest;
import com.upsaude.api.response.financeiro.PlanoContasResponse;
import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.PlanoContasRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.PlanoContasService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.plano.PlanoContasCreator;
import com.upsaude.service.api.support.financeiro.plano.PlanoContasDomainService;
import com.upsaude.service.api.support.financeiro.plano.PlanoContasResponseBuilder;
import com.upsaude.service.api.support.financeiro.plano.PlanoContasTenantEnforcer;
import com.upsaude.service.api.support.financeiro.plano.PlanoContasUpdater;
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
public class PlanoContasServiceImpl implements PlanoContasService {

    private final PlanoContasRepository repository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final PlanoContasTenantEnforcer tenantEnforcer;
    private final PlanoContasCreator creator;
    private final PlanoContasUpdater updater;
    private final PlanoContasResponseBuilder responseBuilder;
    private final PlanoContasDomainService domainService;

    @Override
    @Transactional
    public PlanoContasResponse criar(PlanoContasRequest request) {
        log.debug("Criando plano de contas. Request: {}", request);
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }

        PlanoContas saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PlanoContasResponse obterPorId(UUID id) {
        log.debug("Buscando plano de contas por ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        PlanoContas entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoContasResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public PlanoContasResponse atualizar(UUID id, PlanoContasRequest request) {
        log.debug("Atualizando plano de contas. ID: {}, Request: {}", id, request);
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }

        PlanoContas updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            PlanoContas entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir plano de contas", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            PlanoContas entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar plano de contas", e);
        }
    }
}

