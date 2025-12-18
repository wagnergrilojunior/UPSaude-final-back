package com.upsaude.service.impl;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.prontuario.HistoricoClinicoRequest;
import com.upsaude.api.response.prontuario.HistoricoClinicoResponse;
import com.upsaude.entity.prontuario.HistoricoClinico;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.prontuario.HistoricoClinicoRepository;
import com.upsaude.service.prontuario.HistoricoClinicoService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.historicoclinico.HistoricoClinicoCreator;
import com.upsaude.service.support.historicoclinico.HistoricoClinicoResponseBuilder;
import com.upsaude.service.support.historicoclinico.HistoricoClinicoTenantEnforcer;
import com.upsaude.service.support.historicoclinico.HistoricoClinicoUpdater;
import com.upsaude.service.support.historicoclinico.HistoricoClinicoValidationService;
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
public class HistoricoClinicoServiceImpl implements HistoricoClinicoService {

    private final HistoricoClinicoRepository repository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final HistoricoClinicoValidationService validationService;
    private final HistoricoClinicoTenantEnforcer tenantEnforcer;
    private final HistoricoClinicoCreator creator;
    private final HistoricoClinicoUpdater updater;
    private final HistoricoClinicoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public HistoricoClinicoResponse criar(HistoricoClinicoRequest request) {
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        HistoricoClinico saved = creator.criar(request, tenantId, tenant);
        HistoricoClinicoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_HISTORICO_CLINICO);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.historicoClinico(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_HISTORICO_CLINICO, keyGenerator = "historicoClinicoCacheKeyGenerator")
    public HistoricoClinicoResponse obterPorId(UUID id) {
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistoricoClinicoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_HISTORICO_CLINICO, keyGenerator = "historicoClinicoCacheKeyGenerator")
    public HistoricoClinicoResponse atualizar(UUID id, HistoricoClinicoRequest request) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        HistoricoClinico updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_HISTORICO_CLINICO, keyGenerator = "historicoClinicoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        HistoricoClinico entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Registro já está inativo");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Registro excluído (desativado) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
