package com.upsaude.service.impl;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.api.response.profissional.HistoricoHabilitacaoProfissionalResponse;
import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.profissional.HistoricoHabilitacaoProfissionalRepository;
import com.upsaude.service.profissional.HistoricoHabilitacaoProfissionalService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalCreator;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalResponseBuilder;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalTenantEnforcer;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalUpdater;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalServiceImpl implements HistoricoHabilitacaoProfissionalService {

    private final HistoricoHabilitacaoProfissionalRepository repository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final HistoricoHabilitacaoProfissionalValidationService validationService;
    private final HistoricoHabilitacaoProfissionalTenantEnforcer tenantEnforcer;
    private final HistoricoHabilitacaoProfissionalCreator creator;
    private final HistoricoHabilitacaoProfissionalUpdater updater;
    private final HistoricoHabilitacaoProfissionalResponseBuilder responseBuilder;

    @Override
    @Transactional
    public HistoricoHabilitacaoProfissionalResponse criar(HistoricoHabilitacaoProfissionalRequest request) {
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        HistoricoHabilitacaoProfissional saved = creator.criar(request, tenantId, tenant);
        HistoricoHabilitacaoProfissionalResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_HISTORICO_HABILITACAO_PROFISSIONAL);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.historicoHabilitacaoProfissional(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_HISTORICO_HABILITACAO_PROFISSIONAL, keyGenerator = "historicoHabilitacaoProfissionalCacheKeyGenerator")
    public HistoricoHabilitacaoProfissionalResponse obterPorId(UUID id) {
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistoricoHabilitacaoProfissionalResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_HISTORICO_HABILITACAO_PROFISSIONAL, keyGenerator = "historicoHabilitacaoProfissionalCacheKeyGenerator")
    public HistoricoHabilitacaoProfissionalResponse atualizar(UUID id, HistoricoHabilitacaoProfissionalRequest request) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        HistoricoHabilitacaoProfissional updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_HISTORICO_HABILITACAO_PROFISSIONAL, keyGenerator = "historicoHabilitacaoProfissionalCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        HistoricoHabilitacaoProfissional entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Registro já está inativo");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Registro excluído (desativado) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
