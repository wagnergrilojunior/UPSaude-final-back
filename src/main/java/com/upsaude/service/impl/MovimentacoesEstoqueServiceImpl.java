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

import com.upsaude.api.request.estabelecimento.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.estabelecimento.estoque.MovimentacoesEstoqueResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.estabelecimento.estoque.MovimentacoesEstoque;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.estabelecimento.estoque.MovimentacoesEstoqueRepository;
import com.upsaude.service.estabelecimento.estoque.MovimentacoesEstoqueService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueCreator;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueResponseBuilder;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueTenantEnforcer;
import com.upsaude.service.support.movimentacoesestoque.MovimentacoesEstoqueUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueServiceImpl implements MovimentacoesEstoqueService {

    private final MovimentacoesEstoqueRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final MovimentacoesEstoqueCreator creator;
    private final MovimentacoesEstoqueUpdater updater;
    private final MovimentacoesEstoqueResponseBuilder responseBuilder;
    private final MovimentacoesEstoqueTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public MovimentacoesEstoqueResponse criar(MovimentacoesEstoqueRequest request) {
        log.debug("Criando nova movimentação de estoque");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            MovimentacoesEstoque saved = creator.criar(request, tenantId, tenant);
            MovimentacoesEstoqueResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_MOVIMENTACOES_ESTOQUE);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.movimentacaoEstoque(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar movimentação de estoque", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_MOVIMENTACOES_ESTOQUE, keyGenerator = "movimentacoesEstoqueCacheKeyGenerator")
    public MovimentacoesEstoqueResponse obterPorId(UUID id) {
        log.debug("Buscando movimentação de estoque por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da movimentação de estoque é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        MovimentacoesEstoque entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimentacoesEstoqueResponse> listar(Pageable pageable) {
        log.debug("Listando movimentações de estoque paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<MovimentacoesEstoque> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_MOVIMENTACOES_ESTOQUE, keyGenerator = "movimentacoesEstoqueCacheKeyGenerator")
    public MovimentacoesEstoqueResponse atualizar(UUID id, MovimentacoesEstoqueRequest request) {
        log.debug("Atualizando movimentação de estoque. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da movimentação de estoque é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        MovimentacoesEstoque updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MOVIMENTACOES_ESTOQUE, keyGenerator = "movimentacoesEstoqueCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo movimentação de estoque. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da movimentação de estoque é obrigatório");
        }

        MovimentacoesEstoque entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Movimentação de estoque excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
