package com.upsaude.service.impl;

import com.upsaude.api.request.ProcedimentoCirurgicoRequest;
import com.upsaude.api.response.ProcedimentoCirurgicoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.ProcedimentoCirurgicoRepository;
import com.upsaude.service.ProcedimentoCirurgicoService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.procedimentocirurgico.ProcedimentoCirurgicoCreator;
import com.upsaude.service.support.procedimentocirurgico.ProcedimentoCirurgicoDomainService;
import com.upsaude.service.support.procedimentocirurgico.ProcedimentoCirurgicoResponseBuilder;
import com.upsaude.service.support.procedimentocirurgico.ProcedimentoCirurgicoTenantEnforcer;
import com.upsaude.service.support.procedimentocirurgico.ProcedimentoCirurgicoUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcedimentoCirurgicoServiceImpl implements ProcedimentoCirurgicoService {

    private final ProcedimentoCirurgicoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ProcedimentoCirurgicoCreator creator;
    private final ProcedimentoCirurgicoUpdater updater;
    private final ProcedimentoCirurgicoResponseBuilder responseBuilder;
    private final ProcedimentoCirurgicoDomainService domainService;
    private final ProcedimentoCirurgicoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ProcedimentoCirurgicoResponse criar(ProcedimentoCirurgicoRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            ProcedimentoCirurgico saved = creator.criar(request, tenantId, tenant);
            ProcedimentoCirurgicoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PROCEDIMENTOS_CIRURGICOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.procedimentoCirurgico(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar procedimento cirúrgico", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PROCEDIMENTOS_CIRURGICOS, keyGenerator = "procedimentoCirurgicoCacheKeyGenerator")
    public ProcedimentoCirurgicoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do procedimento cirúrgico é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        ProcedimentoCirurgico entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcedimentoCirurgicoResponse> listar(Pageable pageable, UUID cirurgiaId, String codigoProcedimento) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<ProcedimentoCirurgico> page;
        if (cirurgiaId != null) {
            page = repository.findByCirurgiaIdAndTenantIdOrderByCreatedAtAsc(cirurgiaId, tenantId, pageable);
        } else if (codigoProcedimento != null && !codigoProcedimento.isBlank()) {
            page = repository.findByCodigoProcedimentoAndTenantIdOrderByCreatedAtDesc(codigoProcedimento, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PROCEDIMENTOS_CIRURGICOS, keyGenerator = "procedimentoCirurgicoCacheKeyGenerator")
    public ProcedimentoCirurgicoResponse atualizar(UUID id, ProcedimentoCirurgicoRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do procedimento cirúrgico é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ProcedimentoCirurgico updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROCEDIMENTOS_CIRURGICOS, keyGenerator = "procedimentoCirurgicoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do procedimento cirúrgico é obrigatório");
        }

        ProcedimentoCirurgico entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

