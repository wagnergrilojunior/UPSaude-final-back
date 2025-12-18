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

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.api.response.profissional.HistoricoHabilitacaoProfissionalResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.profissional.HistoricoHabilitacaoProfissionalRepository;
import com.upsaude.service.profissional.HistoricoHabilitacaoProfissionalService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalCreator;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalResponseBuilder;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalTenantEnforcer;
import com.upsaude.service.support.historicohabilitacaoprofissional.HistoricoHabilitacaoProfissionalUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalServiceImpl implements HistoricoHabilitacaoProfissionalService {

    private final HistoricoHabilitacaoProfissionalRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final HistoricoHabilitacaoProfissionalCreator creator;
    private final HistoricoHabilitacaoProfissionalUpdater updater;
    private final HistoricoHabilitacaoProfissionalResponseBuilder responseBuilder;
    private final HistoricoHabilitacaoProfissionalTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public HistoricoHabilitacaoProfissionalResponse criar(HistoricoHabilitacaoProfissionalRequest request) {
        log.debug("Criando novo histórico de habilitação profissional");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            HistoricoHabilitacaoProfissional saved = creator.criar(request, tenantId, tenant);
            HistoricoHabilitacaoProfissionalResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_HISTORICO_HABILITACAO_PROFISSIONAL);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.historicoHabilitacaoProfissional(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar histórico de habilitação profissional", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_HISTORICO_HABILITACAO_PROFISSIONAL, keyGenerator = "historicoHabilitacaoProfissionalCacheKeyGenerator")
    public HistoricoHabilitacaoProfissionalResponse obterPorId(UUID id) {
        log.debug("Buscando histórico de habilitação profissional por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do histórico de habilitação profissional é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        HistoricoHabilitacaoProfissional entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistoricoHabilitacaoProfissionalResponse> listar(Pageable pageable) {
        log.debug("Listando históricos de habilitação profissional paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<HistoricoHabilitacaoProfissional> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_HISTORICO_HABILITACAO_PROFISSIONAL, keyGenerator = "historicoHabilitacaoProfissionalCacheKeyGenerator")
    public HistoricoHabilitacaoProfissionalResponse atualizar(UUID id, HistoricoHabilitacaoProfissionalRequest request) {
        log.debug("Atualizando histórico de habilitação profissional. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do histórico de habilitação profissional é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        HistoricoHabilitacaoProfissional updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_HISTORICO_HABILITACAO_PROFISSIONAL, keyGenerator = "historicoHabilitacaoProfissionalCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo histórico de habilitação profissional. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do histórico de habilitação profissional é obrigatório");
        }

        HistoricoHabilitacaoProfissional entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Histórico de habilitação profissional excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
