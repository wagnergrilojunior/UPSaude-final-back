package com.upsaude.service.impl;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.api.response.ProcedimentosOdontologicosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.ProcedimentosOdontologicosRepository;
import com.upsaude.service.ProcedimentosOdontologicosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.Tenant;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.procedimentosodontologicos.ProcedimentosOdontologicosCreator;
import com.upsaude.service.support.procedimentosodontologicos.ProcedimentosOdontologicosDomainService;
import com.upsaude.service.support.procedimentosodontologicos.ProcedimentosOdontologicosResponseBuilder;
import com.upsaude.service.support.procedimentosodontologicos.ProcedimentosOdontologicosTenantEnforcer;
import com.upsaude.service.support.procedimentosodontologicos.ProcedimentosOdontologicosUpdater;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcedimentosOdontologicosServiceImpl implements ProcedimentosOdontologicosService {

    private final ProcedimentosOdontologicosRepository procedimentosOdontologicosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ProcedimentosOdontologicosCreator creator;
    private final ProcedimentosOdontologicosUpdater updater;
    private final ProcedimentosOdontologicosResponseBuilder responseBuilder;
    private final ProcedimentosOdontologicosDomainService domainService;
    private final ProcedimentosOdontologicosTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ProcedimentosOdontologicosResponse criar(ProcedimentosOdontologicosRequest request) {
        log.debug("Criando novo procedimentosodontologicos");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            ProcedimentosOdontologicos saved = creator.criar(request, tenantId, tenant);
            ProcedimentosOdontologicosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PROCEDIMENTOS_ODONTOLOGICOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.procedimentoOdontologico(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar procedimento odontológico", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PROCEDIMENTOS_ODONTOLOGICOS, keyGenerator = "procedimentosOdontologicosCacheKeyGenerator")
    public ProcedimentosOdontologicosResponse obterPorId(UUID id) {
        log.debug("Buscando procedimentosodontologicos por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do procedimentosodontologicos é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        ProcedimentosOdontologicos entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcedimentosOdontologicosResponse> listar(Pageable pageable) {
        return listar(pageable, null, null);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PROCEDIMENTOS_ODONTOLOGICOS, keyGenerator = "procedimentosOdontologicosCacheKeyGenerator")
    public ProcedimentosOdontologicosResponse atualizar(UUID id, ProcedimentosOdontologicosRequest request) {
        log.debug("Atualizando procedimentosodontologicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do procedimentosodontologicos é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ProcedimentosOdontologicos updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROCEDIMENTOS_ODONTOLOGICOS, keyGenerator = "procedimentosOdontologicosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo procedimentosodontologicos. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcedimentosOdontologicosResponse> listar(Pageable pageable, String codigo, String nome) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<ProcedimentosOdontologicos> page;
        if (codigo != null && !codigo.isBlank()) {
            page = procedimentosOdontologicosRepository.findByCodigoContainingIgnoreCaseAndTenantId(codigo, tenantId, pageable);
        } else if (nome != null && !nome.isBlank()) {
            page = procedimentosOdontologicosRepository.findByNomeContainingIgnoreCaseAndTenantId(nome, tenantId, pageable);
        } else {
            page = procedimentosOdontologicosRepository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do procedimentosodontologicos é obrigatório");
        }

        ProcedimentosOdontologicos entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        procedimentosOdontologicosRepository.save(Objects.requireNonNull(entity));
        log.info("ProcedimentosOdontologicos excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
