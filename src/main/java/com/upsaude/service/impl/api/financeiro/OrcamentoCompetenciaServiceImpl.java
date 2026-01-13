package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.OrcamentoCompetenciaRequest;
import com.upsaude.api.response.financeiro.OrcamentoCompetenciaResponse;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.financeiro.OrcamentoCompetenciaRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.OrcamentoCompetenciaService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaCreator;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaDomainService;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaResponseBuilder;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaTenantEnforcer;
import com.upsaude.service.api.support.financeiro.orcamento.OrcamentoCompetenciaUpdater;
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
public class OrcamentoCompetenciaServiceImpl implements OrcamentoCompetenciaService {

    private final OrcamentoCompetenciaRepository repository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final OrcamentoCompetenciaTenantEnforcer tenantEnforcer;
    private final OrcamentoCompetenciaCreator creator;
    private final OrcamentoCompetenciaUpdater updater;
    private final OrcamentoCompetenciaResponseBuilder responseBuilder;
    private final OrcamentoCompetenciaDomainService domainService;

    @Override
    @Transactional
    public OrcamentoCompetenciaResponse criar(OrcamentoCompetenciaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        OrcamentoCompetencia saved = creator.criar(request, tenantId, tenant);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrcamentoCompetenciaResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        OrcamentoCompetencia entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrcamentoCompetenciaResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public OrcamentoCompetenciaResponse atualizar(UUID id, OrcamentoCompetenciaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        OrcamentoCompetencia updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            OrcamentoCompetencia entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir orçamento por competência", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            OrcamentoCompetencia entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar orçamento por competência", e);
        }
    }
}

