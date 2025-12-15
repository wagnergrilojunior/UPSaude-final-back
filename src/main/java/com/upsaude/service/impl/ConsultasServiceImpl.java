package com.upsaude.service.impl;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.api.response.ConsultasResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Consultas;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.ConsultasRepository;
import com.upsaude.service.ConsultasService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.consultas.ConsultasCreator;
import com.upsaude.service.support.consultas.ConsultasResponseBuilder;
import com.upsaude.service.support.consultas.ConsultasTenantEnforcer;
import com.upsaude.service.support.consultas.ConsultasUpdater;
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
public class ConsultasServiceImpl implements ConsultasService {

    private final ConsultasRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ConsultasCreator creator;
    private final ConsultasUpdater updater;
    private final ConsultasResponseBuilder responseBuilder;
    private final ConsultasTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ConsultasResponse criar(ConsultasRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Consultas saved = creator.criar(request, tenantId, tenant);
            ConsultasResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CONSULTAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.consulta(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar consulta", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Consultas entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultasResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Consultas> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Consultas> page = repository.findByEstabelecimentoIdAndTenantIdOrderByInformacoesDataConsultaDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse atualizar(UUID id, ConsultasRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Consultas updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar consulta", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }

        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Consulta já está inativa");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Consulta excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }

}
