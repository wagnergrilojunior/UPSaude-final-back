package com.upsaude.service.impl;

import com.upsaude.api.request.atendimento.ConsultaPreNatalRequest;
import com.upsaude.api.response.atendimento.ConsultaPreNatalResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.atendimento.ConsultaPreNatal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.atendimento.ConsultaPreNatalRepository;
import com.upsaude.service.atendimento.ConsultaPreNatalService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.consultaprenatal.ConsultaPreNatalCreator;
import com.upsaude.service.support.consultaprenatal.ConsultaPreNatalDomainService;
import com.upsaude.service.support.consultaprenatal.ConsultaPreNatalResponseBuilder;
import com.upsaude.service.support.consultaprenatal.ConsultaPreNatalTenantEnforcer;
import com.upsaude.service.support.consultaprenatal.ConsultaPreNatalUpdater;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPreNatalServiceImpl implements ConsultaPreNatalService {

    private final ConsultaPreNatalRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ConsultaPreNatalCreator creator;
    private final ConsultaPreNatalUpdater updater;
    private final ConsultaPreNatalResponseBuilder responseBuilder;
    private final ConsultaPreNatalDomainService domainService;
    private final ConsultaPreNatalTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ConsultaPreNatalResponse criar(ConsultaPreNatalRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            ConsultaPreNatal saved = creator.criar(request, tenantId, tenant);
            ConsultaPreNatalResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CONSULTAS_PRE_NATAL);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.consultaPreNatal(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar consulta pré-natal", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONSULTAS_PRE_NATAL, keyGenerator = "consultaPreNatalCacheKeyGenerator")
    public ConsultaPreNatalResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da consulta pré-natal é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaPreNatalResponse> listar(Pageable pageable, UUID preNatalId, UUID estabelecimentoId, OffsetDateTime inicio, OffsetDateTime fim) {
        UUID tenantId = tenantService.validarTenantAtual();

        if ((inicio != null && fim == null) || (inicio == null && fim != null)) {
            throw new BadRequestException("Para filtrar por período, informe 'inicio' e 'fim'");
        }

        if (preNatalId != null) {
            List<ConsultaPreNatal> list = (inicio != null)
                ? repository.findByPreNatalIdAndDataConsultaBetweenAndTenantIdOrderByDataConsultaAsc(preNatalId, inicio, fim, tenantId)
                : repository.findByPreNatalIdAndTenantIdOrderByDataConsultaAsc(preNatalId, tenantId);
            org.springframework.data.domain.Pageable safePageable = (pageable == null)
                ? org.springframework.data.domain.Pageable.unpaged()
                : pageable;
            return new org.springframework.data.domain.PageImpl<>(list, safePageable, list.size()).map(responseBuilder::build);
        }

        Page<ConsultaPreNatal> page;
        if (estabelecimentoId != null) {
            page = (inicio != null)
                ? repository.findByEstabelecimentoIdAndDataConsultaBetweenAndTenantIdOrderByDataConsultaDesc(estabelecimentoId, inicio, fim, tenantId, pageable)
                : repository.findByEstabelecimentoIdAndTenantIdOrderByDataConsultaDesc(estabelecimentoId, tenantId, pageable);
        } else if (inicio != null) {
            page = repository.findByDataConsultaBetweenAndTenantIdOrderByDataConsultaDesc(inicio, fim, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPorPreNatalId(UUID preNatalId) {
        if (preNatalId == null) {
            throw new BadRequestException("ID do pré-natal é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.countByPreNatalIdAndTenantId(preNatalId, tenantId);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS_PRE_NATAL, keyGenerator = "consultaPreNatalCacheKeyGenerator")
    public ConsultaPreNatalResponse atualizar(UUID id, ConsultaPreNatalRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da consulta pré-natal é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ConsultaPreNatal updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONSULTAS_PRE_NATAL, keyGenerator = "consultaPreNatalCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da consulta pré-natal é obrigatório");
        }

        ConsultaPreNatal entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        ConsultaPreNatal saved = repository.save(Objects.requireNonNull(entity));

        if (saved.getPreNatal() != null) {
            domainService.recalcularNumeroConsultas(saved.getPreNatal(), tenantId);
        }
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

