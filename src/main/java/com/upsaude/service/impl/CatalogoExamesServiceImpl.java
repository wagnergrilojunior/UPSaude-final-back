package com.upsaude.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.exame.CatalogoExamesRequest;
import com.upsaude.api.response.clinica.exame.CatalogoExamesResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.exame.CatalogoExames;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.exame.CatalogoExamesRepository;
import com.upsaude.service.clinica.exame.CatalogoExamesService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.catalogoexames.CatalogoExamesCreator;
import com.upsaude.service.support.catalogoexames.CatalogoExamesDomainService;
import com.upsaude.service.support.catalogoexames.CatalogoExamesResponseBuilder;
import com.upsaude.service.support.catalogoexames.CatalogoExamesTenantEnforcer;
import com.upsaude.service.support.catalogoexames.CatalogoExamesUpdater;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoExamesServiceImpl implements CatalogoExamesService {

    private final CatalogoExamesRepository catalogoExamesRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final CatalogoExamesCreator creator;
    private final CatalogoExamesUpdater updater;
    private final CatalogoExamesTenantEnforcer tenantEnforcer;
    private final CatalogoExamesResponseBuilder responseBuilder;
    private final CatalogoExamesDomainService domainService;

    @Override
    @Transactional
    public CatalogoExamesResponse criar(CatalogoExamesRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            CatalogoExames exame = creator.criar(request, tenantId, tenant);
            CatalogoExamesResponse response = responseBuilder.build(exame);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CATALOGO_EXAMES);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.catalogoExame(tenantId, exame.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar exame no catálogo. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar exame no catálogo. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir exame no catálogo", e);
        }
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CATALOGO_EXAMES, keyGenerator = "catalogoExamesCacheKeyGenerator")
    public CatalogoExamesResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do exame é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        CatalogoExames exame = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(exame);
    }

    @Override
    @Transactional
    public Page<CatalogoExamesResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return catalogoExamesRepository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CATALOGO_EXAMES, keyGenerator = "catalogoExamesCacheKeyGenerator")
    public CatalogoExamesResponse atualizar(UUID id, CatalogoExamesRequest request) {
        try {
            if (id == null) {
                throw new BadRequestException("ID do exame é obrigatório");
            }
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            CatalogoExames exame = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(exame);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar exame no catálogo. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar exame no catálogo. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar exame no catálogo", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CATALOGO_EXAMES, keyGenerator = "catalogoExamesCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir(inativar) exame no catálogo. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir(inativar) exame no catálogo. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir exame no catálogo", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do exame é obrigatório");
        }
        CatalogoExames exame = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(exame);
        exame.setActive(false);
        catalogoExamesRepository.save(Objects.requireNonNull(exame));
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
