package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.CentroCustoRequest;
import com.upsaude.api.response.financeiro.CentroCustoResponse;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.CentroCustoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.centrocusto.CentroCustoCreator;
import com.upsaude.service.api.support.financeiro.centrocusto.CentroCustoDomainService;
import com.upsaude.service.api.support.financeiro.centrocusto.CentroCustoResponseBuilder;
import com.upsaude.service.api.support.financeiro.centrocusto.CentroCustoTenantEnforcer;
import com.upsaude.service.api.support.financeiro.centrocusto.CentroCustoUpdater;
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
public class CentroCustoServiceImpl implements CentroCustoService {

    private final CentroCustoRepository repository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final CentroCustoTenantEnforcer tenantEnforcer;
    private final CentroCustoCreator creator;
    private final CentroCustoUpdater updater;
    private final CentroCustoResponseBuilder responseBuilder;
    private final CentroCustoDomainService domainService;

    @Override
    @Transactional
    public CentroCustoResponse criar(CentroCustoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }
        CentroCusto saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CentroCustoResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        CentroCusto entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CentroCustoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public CentroCustoResponse atualizar(UUID id, CentroCustoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId).orElse(null);
        }
        CentroCusto updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            CentroCusto entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir centro de custo", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            CentroCusto entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar centro de custo", e);
        }
    }
}

