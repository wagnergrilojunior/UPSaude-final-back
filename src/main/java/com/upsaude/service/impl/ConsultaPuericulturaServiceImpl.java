package com.upsaude.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.clinica.atendimento.ConsultaPuericulturaRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultaPuericulturaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.atendimento.ConsultaPuericultura;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.clinica.atendimento.ConsultaPuericulturaRepository;
import com.upsaude.service.clinica.atendimento.ConsultaPuericulturaService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.consultapuericultura.ConsultaPuericulturaCreator;
import com.upsaude.service.support.consultapuericultura.ConsultaPuericulturaResponseBuilder;
import com.upsaude.service.support.consultapuericultura.ConsultaPuericulturaTenantEnforcer;
import com.upsaude.service.support.consultapuericultura.ConsultaPuericulturaUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaServiceImpl implements ConsultaPuericulturaService {

    private final ConsultaPuericulturaRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ConsultaPuericulturaCreator creator;
    private final ConsultaPuericulturaUpdater updater;
    private final ConsultaPuericulturaResponseBuilder responseBuilder;
    private final ConsultaPuericulturaTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ConsultaPuericulturaResponse criar(ConsultaPuericulturaRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            ConsultaPuericultura saved = creator.criar(request, tenantId, tenant);
            ConsultaPuericulturaResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CONSULTAS_PUERICULTURA);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.consultaPuericultura(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar consulta de puericultura", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "consultasPuericultura", keyGenerator = "consultaPuericulturaCacheKeyGenerator")
    public ConsultaPuericulturaResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da consulta de puericultura é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaPuericulturaResponse> listar(Pageable pageable, UUID puericulturaId, UUID estabelecimentoId) {
        UUID tenantId = tenantService.validarTenantAtual();

        if (puericulturaId != null) {
            List<ConsultaPuericultura> list = repository.findByPuericulturaIdAndTenantIdOrderByDataConsultaAsc(puericulturaId, tenantId, pageable).getContent();
            Pageable safePageable = (pageable == null) ? Pageable.unpaged() : pageable;
            return new PageImpl<>(list, safePageable, list.size()).map(responseBuilder::build);
        }

        Page<ConsultaPuericultura> page;
        if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataConsultaDesc(estabelecimentoId, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "consultasPuericultura", keyGenerator = "consultaPuericulturaCacheKeyGenerator")
    public ConsultaPuericulturaResponse atualizar(UUID id, ConsultaPuericulturaRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da consulta de puericultura é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ConsultaPuericultura updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "consultasPuericultura", keyGenerator = "consultaPuericulturaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da consulta de puericultura é obrigatório");
        }

        ConsultaPuericultura entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
