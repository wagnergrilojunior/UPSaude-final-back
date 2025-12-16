package com.upsaude.service.impl;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.MovimentacoesEstoqueResponse;
import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.MovimentacoesEstoqueRepository;
import com.upsaude.service.MovimentacoesEstoqueService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueCreator;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueResponseBuilder;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueTenantEnforcer;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueUpdater;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueServiceImpl implements MovimentacoesEstoqueService {

    private final MovimentacoesEstoqueRepository movimentacoesEstoqueRepository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final MovimentacoesEstoqueValidationService validationService;
    private final MovimentacoesEstoqueTenantEnforcer tenantEnforcer;
    private final MovimentacoesEstoqueCreator creator;
    private final MovimentacoesEstoqueUpdater updater;
    private final MovimentacoesEstoqueResponseBuilder responseBuilder;

    @Override
    @Transactional
    public MovimentacoesEstoqueResponse criar(MovimentacoesEstoqueRequest request) {
        log.debug("Criando nova movimentação de estoque. Request: {}", request);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        MovimentacoesEstoque saved = creator.criar(request, tenantId, tenant);
        MovimentacoesEstoqueResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_MOVIMENTACOES_ESTOQUE);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.movimentacaoEstoque(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_MOVIMENTACOES_ESTOQUE, keyGenerator = "movimentacoesEstoqueCacheKeyGenerator")
    public MovimentacoesEstoqueResponse obterPorId(UUID id) {
        log.debug("Buscando movimentacoesestoque por ID: {} (cache miss)", id);
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimentacoesEstoqueResponse> listar(Pageable pageable) {
        log.debug("Listando movimentações de estoque paginadas. Pageable: {}", pageable);
        UUID tenantId = tenantService.validarTenantAtual();
        return movimentacoesEstoqueRepository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_MOVIMENTACOES_ESTOQUE, keyGenerator = "movimentacoesEstoqueCacheKeyGenerator")
    public MovimentacoesEstoqueResponse atualizar(UUID id, MovimentacoesEstoqueRequest request) {
        log.debug("Atualizando movimentacoesestoque. ID: {}", id);

        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        MovimentacoesEstoque updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MOVIMENTACOES_ESTOQUE, keyGenerator = "movimentacoesEstoqueCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo movimentacoesestoque. ID: {}", id);

        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        MovimentacoesEstoque entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Movimentação de estoque já está inativa");
        }

        entity.setActive(false);
        movimentacoesEstoqueRepository.save(entity);
        log.info("Movimentação de estoque excluída (desativada) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
