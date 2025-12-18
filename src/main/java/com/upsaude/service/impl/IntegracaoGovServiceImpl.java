package com.upsaude.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.sistema.integracao.IntegracaoGovRequest;
import com.upsaude.api.response.sistema.integracao.IntegracaoGovResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.integracao.IntegracaoGovRepository;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.sistema.integracao.IntegracaoGovService;
import com.upsaude.service.support.integracaogov.IntegracaoGovCreator;
import com.upsaude.service.support.integracaogov.IntegracaoGovResponseBuilder;
import com.upsaude.service.support.integracaogov.IntegracaoGovTenantEnforcer;
import com.upsaude.service.support.integracaogov.IntegracaoGovUpdater;
import com.upsaude.service.support.integracaogov.IntegracaoGovValidationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegracaoGovServiceImpl implements IntegracaoGovService {

    private final IntegracaoGovRepository repository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final IntegracaoGovValidationService validationService;
    private final IntegracaoGovTenantEnforcer tenantEnforcer;
    private final IntegracaoGovCreator creator;
    private final IntegracaoGovUpdater updater;
    private final IntegracaoGovResponseBuilder responseBuilder;

    @Override
    @Transactional
    public IntegracaoGovResponse criar(IntegracaoGovRequest request) {
        log.debug("Criando integração gov. Request: {}", request);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        IntegracaoGov saved = creator.criar(request, tenantId, tenant);
        IntegracaoGovResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_INTEGRACAO_GOV);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.integracaoGov(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_INTEGRACAO_GOV, keyGenerator = "integracaoGovCacheKeyGenerator")
    public IntegracaoGovResponse obterPorId(UUID id) {
        log.debug("Buscando integração gov por ID: {} (cache miss)", id);
        validationService.validarId(id);

        UUID tenantId = tenantService.validarTenantAtual();
        IntegracaoGov entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public IntegracaoGovResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando integração gov por paciente ID: {}", pacienteId);
        validationService.validarPacienteId(pacienteId);

        UUID tenantId = tenantService.validarTenantAtual();
        IntegracaoGov entity = repository.findByPacienteIdAndTenantId(pacienteId, tenantId)
            .orElseThrow(() -> new NotFoundException("Integração gov não encontrada para o paciente: " + pacienteId));
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntegracaoGovResponse> listar(Pageable pageable) {
        log.debug("Listando integrações gov paginadas. Pageable: {}", pageable);
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_INTEGRACAO_GOV, keyGenerator = "integracaoGovCacheKeyGenerator")
    public IntegracaoGovResponse atualizar(UUID id, IntegracaoGovRequest request) {
        log.debug("Atualizando integração gov. ID: {}", id);
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        IntegracaoGov updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_INTEGRACAO_GOV, keyGenerator = "integracaoGovCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo integração gov. ID: {}", id);
        validationService.validarId(id);

        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        IntegracaoGov entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Integração gov já está inativa");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Integração gov excluída (desativada) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
