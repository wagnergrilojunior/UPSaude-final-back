package com.upsaude.service.impl;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.InfraestruturaEstabelecimentoRequest;
import com.upsaude.api.response.InfraestruturaEstabelecimentoResponse;
import com.upsaude.entity.InfraestruturaEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.InfraestruturaEstabelecimentoRepository;
import com.upsaude.service.InfraestruturaEstabelecimentoService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoCreator;
import com.upsaude.service.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoResponseBuilder;
import com.upsaude.service.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoTenantEnforcer;
import com.upsaude.service.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoUpdater;
import com.upsaude.service.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoValidationService;
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
public class InfraestruturaEstabelecimentoServiceImpl implements InfraestruturaEstabelecimentoService {

    private final InfraestruturaEstabelecimentoRepository repository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final InfraestruturaEstabelecimentoValidationService validationService;
    private final InfraestruturaEstabelecimentoTenantEnforcer tenantEnforcer;
    private final InfraestruturaEstabelecimentoCreator creator;
    private final InfraestruturaEstabelecimentoUpdater updater;
    private final InfraestruturaEstabelecimentoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public InfraestruturaEstabelecimentoResponse criar(InfraestruturaEstabelecimentoRequest request) {
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        InfraestruturaEstabelecimento saved = creator.criar(request, tenantId, tenant);
        InfraestruturaEstabelecimentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_INFRAESTRUTURA_ESTABELECIMENTO);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.infraestruturaEstabelecimento(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_INFRAESTRUTURA_ESTABELECIMENTO, keyGenerator = "infraestruturaEstabelecimentoCacheKeyGenerator")
    public InfraestruturaEstabelecimentoResponse obterPorId(UUID id) {
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InfraestruturaEstabelecimentoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_INFRAESTRUTURA_ESTABELECIMENTO, keyGenerator = "infraestruturaEstabelecimentoCacheKeyGenerator")
    public InfraestruturaEstabelecimentoResponse atualizar(UUID id, InfraestruturaEstabelecimentoRequest request) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        InfraestruturaEstabelecimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_INFRAESTRUTURA_ESTABELECIMENTO, keyGenerator = "infraestruturaEstabelecimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        InfraestruturaEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Registro já está inativo");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Registro excluído (desativado) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
