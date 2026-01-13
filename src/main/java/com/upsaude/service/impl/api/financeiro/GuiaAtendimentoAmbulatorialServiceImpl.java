package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.GuiaAtendimentoAmbulatorialRequest;
import com.upsaude.api.response.financeiro.GuiaAtendimentoAmbulatorialResponse;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.GuiaAtendimentoAmbulatorialRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.GuiaAtendimentoAmbulatorialService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.guia.GuiaAtendimentoAmbulatorialCreator;
import com.upsaude.service.api.support.financeiro.guia.GuiaAtendimentoAmbulatorialDomainService;
import com.upsaude.service.api.support.financeiro.guia.GuiaAtendimentoAmbulatorialResponseBuilder;
import com.upsaude.service.api.support.financeiro.guia.GuiaAtendimentoAmbulatorialTenantEnforcer;
import com.upsaude.service.api.support.financeiro.guia.GuiaAtendimentoAmbulatorialUpdater;
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
public class GuiaAtendimentoAmbulatorialServiceImpl implements GuiaAtendimentoAmbulatorialService {

    private final GuiaAtendimentoAmbulatorialRepository repository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final GuiaAtendimentoAmbulatorialTenantEnforcer tenantEnforcer;
    private final GuiaAtendimentoAmbulatorialCreator creator;
    private final GuiaAtendimentoAmbulatorialUpdater updater;
    private final GuiaAtendimentoAmbulatorialResponseBuilder responseBuilder;
    private final GuiaAtendimentoAmbulatorialDomainService domainService;

    @Override
    @Transactional
    public GuiaAtendimentoAmbulatorialResponse criar(GuiaAtendimentoAmbulatorialRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);
        GuiaAtendimentoAmbulatorial saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public GuiaAtendimentoAmbulatorialResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GuiaAtendimentoAmbulatorialResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public GuiaAtendimentoAmbulatorialResponse atualizar(UUID id, GuiaAtendimentoAmbulatorialRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);
        GuiaAtendimentoAmbulatorial updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            GuiaAtendimentoAmbulatorial entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir guia ambulatorial", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            GuiaAtendimentoAmbulatorial entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar guia ambulatorial", e);
        }
    }
}

