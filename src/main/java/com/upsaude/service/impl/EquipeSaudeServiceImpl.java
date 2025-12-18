package com.upsaude.service.impl;

import com.upsaude.api.request.profissional.equipe.EquipeSaudeRequest;
import com.upsaude.api.response.profissional.equipe.EquipeSaudeResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.EquipeSaudeRepository;
import com.upsaude.service.profissional.equipe.EquipeSaudeService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.equipesaude.EquipeSaudeCreator;
import com.upsaude.service.support.equipesaude.EquipeSaudeDomainService;
import com.upsaude.service.support.equipesaude.EquipeSaudeResponseBuilder;
import com.upsaude.service.support.equipesaude.EquipeSaudeTenantEnforcer;
import com.upsaude.service.support.equipesaude.EquipeSaudeUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeSaudeServiceImpl implements EquipeSaudeService {

    private final EquipeSaudeRepository equipeSaudeRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final EquipeSaudeCreator creator;
    private final EquipeSaudeUpdater updater;
    private final EquipeSaudeTenantEnforcer tenantEnforcer;
    private final EquipeSaudeResponseBuilder responseBuilder;
    private final EquipeSaudeDomainService domainService;

    @Override
    @Transactional
    public EquipeSaudeResponse criar(EquipeSaudeRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            EquipeSaude equipe = creator.criar(request, tenantId, tenant);
            EquipeSaudeResponse response = responseBuilder.build(equipe);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_EQUIPES_SAUDE);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.equipeSaude(tenantId, equipe.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar equipe de saúde. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar equipe de saúde.", e);
            throw new InternalServerErrorException("Erro ao persistir equipe de saúde", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_EQUIPES_SAUDE, keyGenerator = "equipeSaudeCacheKeyGenerator")
    public EquipeSaudeResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da equipe é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        EquipeSaude equipe = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(equipe);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeSaudeResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return equipeSaudeRepository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeSaudeResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        return equipeSaudeRepository.findByEstabelecimentoIdAndTenantIdOrderByNomeReferenciaAsc(estabelecimentoId, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeSaudeResponse> listarPorStatus(StatusAtivoEnum status, UUID estabelecimentoId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (status == null) {
            throw new BadRequestException("Status é obrigatório");
        }
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        return equipeSaudeRepository.findByStatusAndEstabelecimentoIdAndTenantId(status, estabelecimentoId, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_EQUIPES_SAUDE, keyGenerator = "equipeSaudeCacheKeyGenerator")
    public EquipeSaudeResponse atualizar(UUID id, EquipeSaudeRequest request) {
        try {
            if (id == null) {
                throw new BadRequestException("ID da equipe é obrigatório");
            }
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            EquipeSaude equipe = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(equipe);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar equipe de saúde. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar equipe de saúde.", e);
            throw new InternalServerErrorException("Erro ao atualizar equipe de saúde", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_EQUIPES_SAUDE, keyGenerator = "equipeSaudeCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir(inativar) equipe de saúde. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir(inativar) equipe de saúde.", e);
            throw new InternalServerErrorException("Erro ao excluir equipe de saúde", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da equipe é obrigatório");
        }
        EquipeSaude equipe = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(equipe);
        equipe.setActive(false);
        equipeSaudeRepository.save(Objects.requireNonNull(equipe));
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
