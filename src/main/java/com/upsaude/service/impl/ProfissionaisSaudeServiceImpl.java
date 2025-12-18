package com.upsaude.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.service.profissional.ProfissionaisSaudeService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeCreator;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeDomainService;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeResponseBuilder;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeServiceImpl implements ProfissionaisSaudeService {

    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final ProfissionaisSaudeCreator profissionalCreator;
    private final ProfissionaisSaudeUpdater profissionalUpdater;
    private final ProfissionaisSaudeTenantEnforcer tenantEnforcer;
    private final ProfissionaisSaudeResponseBuilder responseBuilder;
    private final ProfissionaisSaudeDomainService domainService;

    @Override
    @Transactional
    public ProfissionaisSaudeResponse criar(ProfissionaisSaudeRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            ProfissionaisSaude profissional = profissionalCreator.criar(request, tenantId, tenant);
            ProfissionaisSaudeResponse response = responseBuilder.build(profissional);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.profissionalSaude(tenantId, profissional.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator")
    public ProfissionaisSaudeResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        ProfissionaisSaude profissional = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(profissional);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfissionaisSaudeResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<ProfissionaisSaude> profissionais = profissionaisSaudeRepository.findAllByTenant(tenantId, pageable);
        return profissionais.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator")
    public ProfissionaisSaudeResponse atualizar(UUID id, ProfissionaisSaudeRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);
            ProfissionaisSaude profissional = profissionalUpdater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(profissional);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator", beforeInvocation = false)
    public void deletarPermanentemente(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            ProfissionaisSaude profissional = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(profissional);
            profissionaisSaudeRepository.delete(Objects.requireNonNull(profissional));
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao deletar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao deletar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao deletar ProfissionalSaude", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        ProfissionaisSaude profissional = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(profissional);
        profissional.setActive(false);
        profissionaisSaudeRepository.save(Objects.requireNonNull(profissional));
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
