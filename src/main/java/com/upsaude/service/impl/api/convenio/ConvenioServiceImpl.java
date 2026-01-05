package com.upsaude.service.impl.api.convenio;

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

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.convenio.ConvenioRepository;
import org.springframework.dao.DataAccessException;
import com.upsaude.service.api.convenio.ConvenioService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.convenio.ConvenioCreator;
import com.upsaude.service.api.support.convenio.ConvenioDomainService;
import com.upsaude.service.api.support.convenio.ConvenioResponseBuilder;
import com.upsaude.service.api.support.convenio.ConvenioTenantEnforcer;
import com.upsaude.service.api.support.convenio.ConvenioUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioServiceImpl implements ConvenioService {

    private final ConvenioRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ConvenioCreator creator;
    private final ConvenioUpdater updater;
    private final ConvenioResponseBuilder responseBuilder;
    private final ConvenioDomainService domainService;
    private final ConvenioTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ConvenioResponse criar(ConvenioRequest request) {
        log.debug("Criando novo convênio. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Convenio saved = creator.criar(request, tenantId, tenant);
            ConvenioResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CONVENIOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.convenio(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar convênio", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONVENIOS, keyGenerator = "convenioCacheKeyGenerator")
    public ConvenioResponse obterPorId(UUID id) {
        log.debug("Buscando convênio por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do convênio é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Convenio entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConvenioResponse> listar(Pageable pageable) {
        log.debug("Listando convênios paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Convenio> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONVENIOS, keyGenerator = "convenioCacheKeyGenerator")
    public ConvenioResponse atualizar(UUID id, ConvenioRequest request) {
        log.debug("Atualizando convênio. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do convênio é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Convenio updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONVENIOS, keyGenerator = "convenioCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo convênio permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Convenio entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Convênio excluído permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir Convênio. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Convênio. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Convênio", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONVENIOS, keyGenerator = "convenioCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando convênio. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar Convênio. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Convênio. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Convênio", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do convênio é obrigatório");
        }

        Convenio entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Convênio inativado com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
