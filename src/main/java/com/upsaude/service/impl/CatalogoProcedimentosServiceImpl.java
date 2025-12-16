package com.upsaude.service.impl;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CatalogoProcedimentosRepository;
import com.upsaude.service.CatalogoProcedimentosService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.catalogoprocedimentos.CatalogoProcedimentosCreator;
import com.upsaude.service.support.catalogoprocedimentos.CatalogoProcedimentosDomainService;
import com.upsaude.service.support.catalogoprocedimentos.CatalogoProcedimentosResponseBuilder;
import com.upsaude.service.support.catalogoprocedimentos.CatalogoProcedimentosTenantEnforcer;
import com.upsaude.service.support.catalogoprocedimentos.CatalogoProcedimentosUpdater;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoProcedimentosServiceImpl implements CatalogoProcedimentosService {

    private final CatalogoProcedimentosRepository catalogoProcedimentosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final CatalogoProcedimentosCreator creator;
    private final CatalogoProcedimentosUpdater updater;
    private final CatalogoProcedimentosTenantEnforcer tenantEnforcer;
    private final CatalogoProcedimentosResponseBuilder responseBuilder;
    private final CatalogoProcedimentosDomainService domainService;

    @Override
    @Transactional
    public CatalogoProcedimentosResponse criar(CatalogoProcedimentosRequest request) {
        log.debug("Criando novo procedimento no catálogo. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            CatalogoProcedimentos procedimento = creator.criar(request, tenantId, tenant);
            CatalogoProcedimentosResponse response = responseBuilder.build(procedimento);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CATALOGO_PROCEDIMENTOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.catalogoProcedimento(tenantId, procedimento.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar procedimento no catálogo. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar procedimento no catálogo. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir procedimento no catálogo", e);
        }
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CATALOGO_PROCEDIMENTOS, keyGenerator = "catalogoProcedimentosCacheKeyGenerator")
    public CatalogoProcedimentosResponse obterPorId(UUID id) {
        log.debug("Buscando procedimento no catálogo por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do procedimento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        CatalogoProcedimentos procedimento = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(procedimento);
    }

    @Override
    @Transactional
    public Page<CatalogoProcedimentosResponse> listar(Pageable pageable) {
        log.debug("Listando procedimentos do catálogo paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        return catalogoProcedimentosRepository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CATALOGO_PROCEDIMENTOS, keyGenerator = "catalogoProcedimentosCacheKeyGenerator")
    public CatalogoProcedimentosResponse atualizar(UUID id, CatalogoProcedimentosRequest request) {
        log.debug("Atualizando procedimento no catálogo. ID: {}, Request: {}", id, request);

        try {
            if (id == null) {
                throw new BadRequestException("ID do procedimento é obrigatório");
            }
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            CatalogoProcedimentos procedimentoAtualizado = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(procedimentoAtualizado);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar procedimento no catálogo. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar procedimento no catálogo. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar procedimento no catálogo", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CATALOGO_PROCEDIMENTOS, keyGenerator = "catalogoProcedimentosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo procedimento do catálogo. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir(inativar) procedimento no catálogo. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir(inativar) procedimento no catálogo. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir procedimento no catálogo", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do procedimento é obrigatório");
        }
        CatalogoProcedimentos procedimento = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(procedimento);
        procedimento.setActive(false);
        catalogoProcedimentosRepository.save(Objects.requireNonNull(procedimento));
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
