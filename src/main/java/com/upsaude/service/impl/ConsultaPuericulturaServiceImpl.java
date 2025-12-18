package com.upsaude.service.impl;

import com.upsaude.api.request.atendimento.ConsultaPuericulturaRequest;
import com.upsaude.api.response.atendimento.ConsultaPuericulturaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.atendimento.ConsultaPuericultura;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.atendimento.ConsultaPuericulturaRepository;
import com.upsaude.service.atendimento.ConsultaPuericulturaService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.consultapuericultura.ConsultaPuericulturaCreator;
import com.upsaude.service.support.consultapuericultura.ConsultaPuericulturaResponseBuilder;
import com.upsaude.service.support.consultapuericultura.ConsultaPuericulturaTenantEnforcer;
import com.upsaude.service.support.consultapuericultura.ConsultaPuericulturaUpdater;
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
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar consulta de puericultura", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONSULTAS_PUERICULTURA, keyGenerator = "consultaPuericulturaCacheKeyGenerator")
    public ConsultaPuericulturaResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da consulta de puericultura é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        ConsultaPuericultura entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaPuericulturaResponse> listar(Pageable pageable, UUID puericulturaId, UUID estabelecimentoId) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<ConsultaPuericultura> page;
        if (puericulturaId != null) {
            page = repository.findByPuericulturaIdAndTenantIdOrderByDataConsultaAsc(puericulturaId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataConsultaDesc(estabelecimentoId, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS_PUERICULTURA, keyGenerator = "consultaPuericulturaCacheKeyGenerator")
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
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONSULTAS_PUERICULTURA, keyGenerator = "consultaPuericulturaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da consulta de puericultura é obrigatório");
        }

        ConsultaPuericultura entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Consulta de puericultura já está inativa");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Consulta de puericultura excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

