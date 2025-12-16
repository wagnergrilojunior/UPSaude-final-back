package com.upsaude.service.impl;

import com.upsaude.api.request.VisitasDomiciliaresRequest;
import com.upsaude.api.response.VisitasDomiciliaresResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.VisitasDomiciliares;
import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.VisitasDomiciliaresRepository;
import com.upsaude.service.TenantService;
import com.upsaude.service.VisitasDomiciliaresService;
import com.upsaude.service.support.visitasdomiciliares.VisitasDomiciliaresCreator;
import com.upsaude.service.support.visitasdomiciliares.VisitasDomiciliaresDomainService;
import com.upsaude.service.support.visitasdomiciliares.VisitasDomiciliaresResponseBuilder;
import com.upsaude.service.support.visitasdomiciliares.VisitasDomiciliaresTenantEnforcer;
import com.upsaude.service.support.visitasdomiciliares.VisitasDomiciliaresUpdater;
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

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitasDomiciliaresServiceImpl implements VisitasDomiciliaresService {

    private final VisitasDomiciliaresRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final VisitasDomiciliaresCreator creator;
    private final VisitasDomiciliaresUpdater updater;
    private final VisitasDomiciliaresResponseBuilder responseBuilder;
    private final VisitasDomiciliaresDomainService domainService;
    private final VisitasDomiciliaresTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public VisitasDomiciliaresResponse criar(VisitasDomiciliaresRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        VisitasDomiciliares saved = creator.criar(request, tenantId, tenant);
        VisitasDomiciliaresResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_VISITAS_DOMICILIARES);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.visitaDomiciliar(tenantId, saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_VISITAS_DOMICILIARES, keyGenerator = "visitasDomiciliaresCacheKeyGenerator")
    public VisitasDomiciliaresResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da visita domiciliar é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VisitasDomiciliaresResponse> listar(Pageable pageable,
                                                   UUID estabelecimentoId,
                                                   UUID pacienteId,
                                                   UUID profissionalId,
                                                   TipoVisitaDomiciliarEnum tipoVisita,
                                                   OffsetDateTime inicio,
                                                   OffsetDateTime fim) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<VisitasDomiciliares> page;
        if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataVisitaDesc(estabelecimentoId, tenantId, pageable);
        } else if (pacienteId != null) {
            page = repository.findByPacienteIdAndTenantIdOrderByDataVisitaDesc(pacienteId, tenantId, pageable);
        } else if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDataVisitaDesc(profissionalId, tenantId, pageable);
        } else if (tipoVisita != null) {
            page = repository.findByTipoVisitaAndTenantIdOrderByDataVisitaDesc(tipoVisita, tenantId, pageable);
        } else if (inicio != null || fim != null) {
            if (inicio == null || fim == null) {
                throw new BadRequestException("Para filtrar por período, informe 'inicio' e 'fim'");
            }
            page = repository.findByDataVisitaBetweenAndTenantIdOrderByDataVisitaDesc(inicio, fim, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_VISITAS_DOMICILIARES, keyGenerator = "visitasDomiciliaresCacheKeyGenerator")
    public VisitasDomiciliaresResponse atualizar(UUID id, VisitasDomiciliaresRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da visita domiciliar é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        VisitasDomiciliares updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_VISITAS_DOMICILIARES, keyGenerator = "visitasDomiciliaresCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da visita domiciliar é obrigatório");
        }

        VisitasDomiciliares entity = tenantEnforcer.validarAcesso(id, tenantId);
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
