package com.upsaude.service.impl.api.clinica.prontuario;

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

import com.upsaude.api.request.clinica.prontuario.HistoricoClinicoRequest;
import com.upsaude.api.response.clinica.prontuario.HistoricoClinicoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.prontuario.HistoricoClinico;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.clinica.prontuario.HistoricoClinicoRepository;
import com.upsaude.service.api.clinica.prontuario.HistoricoClinicoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.historicoclinico.HistoricoClinicoCreator;
import com.upsaude.service.api.support.historicoclinico.HistoricoClinicoDomainService;
import com.upsaude.service.api.support.historicoclinico.HistoricoClinicoResponseBuilder;
import com.upsaude.service.api.support.historicoclinico.HistoricoClinicoTenantEnforcer;
import com.upsaude.service.api.support.historicoclinico.HistoricoClinicoUpdater;
import org.springframework.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoClinicoServiceImpl implements HistoricoClinicoService {

    private final HistoricoClinicoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final HistoricoClinicoCreator creator;
    private final HistoricoClinicoUpdater updater;
    private final HistoricoClinicoResponseBuilder responseBuilder;
    private final HistoricoClinicoTenantEnforcer tenantEnforcer;
    private final HistoricoClinicoDomainService domainService;

    @Override
    @Transactional
    public HistoricoClinicoResponse criar(HistoricoClinicoRequest request) {
        log.debug("Criando novo histórico clínico");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            HistoricoClinico saved = creator.criar(request, tenantId, tenant);
            HistoricoClinicoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_HISTORICO_CLINICO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.historicoClinico(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar histórico clínico", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_HISTORICO_CLINICO, keyGenerator = "historicoClinicoCacheKeyGenerator")
    public HistoricoClinicoResponse obterPorId(UUID id) {
        log.debug("Buscando histórico clínico por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do histórico clínico é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        HistoricoClinico entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistoricoClinicoResponse> listar(Pageable pageable) {
        log.debug("Listando histórico clínico paginado. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<HistoricoClinico> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_HISTORICO_CLINICO, keyGenerator = "historicoClinicoCacheKeyGenerator")
    public HistoricoClinicoResponse atualizar(UUID id, HistoricoClinicoRequest request) {
        log.debug("Atualizando histórico clínico. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do histórico clínico é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        HistoricoClinico updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_HISTORICO_CLINICO, keyGenerator = "historicoClinicoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo histórico clínico permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            HistoricoClinico entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Histórico clínico excluído permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao excluir HistoricoClinico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir HistoricoClinico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir HistoricoClinico", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_HISTORICO_CLINICO, keyGenerator = "historicoClinicoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando histórico clínico. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao inativar HistoricoClinico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar HistoricoClinico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar HistoricoClinico", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do histórico clínico é obrigatório");
        }

        HistoricoClinico entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Histórico clínico inativado com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
