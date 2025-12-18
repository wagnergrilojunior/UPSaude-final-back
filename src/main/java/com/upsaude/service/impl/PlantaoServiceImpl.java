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

import com.upsaude.api.request.profissional.equipe.PlantaoRequest;
import com.upsaude.api.response.profissional.equipe.PlantaoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.equipe.Plantao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.TipoPlantaoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.profissional.equipe.PlantaoRepository;
import com.upsaude.service.profissional.equipe.PlantaoService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.plantao.PlantaoCreator;
import com.upsaude.service.support.plantao.PlantaoResponseBuilder;
import com.upsaude.service.support.plantao.PlantaoTenantEnforcer;
import com.upsaude.service.support.plantao.PlantaoUpdater;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantaoServiceImpl implements PlantaoService {

    private final PlantaoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final PlantaoCreator creator;
    private final PlantaoUpdater updater;
    private final PlantaoResponseBuilder responseBuilder;
    private final PlantaoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public PlantaoResponse criar(PlantaoRequest request) {
        log.debug("Criando novo plantão");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Plantao saved = creator.criar(request, tenantId, tenant);
            PlantaoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PLANTOES);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.plantao(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar plantão", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantoesCacheKeyGenerator")
    public PlantaoResponse obterPorId(UUID id) {
        log.debug("Buscando plantão por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do plantão é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Plantao entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlantaoResponse> listar(Pageable pageable, UUID profissionalId, UUID medicoId, UUID estabelecimentoId, TipoPlantaoEnum tipoPlantao, OffsetDateTime dataInicio, OffsetDateTime dataFim, Boolean emAndamento) {
        log.debug("Listando plantões paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Plantao> page;

        if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDataHoraInicioDesc(profissionalId, tenantId, pageable);
        } else if (medicoId != null) {
            page = repository.findByMedicoIdAndTenantIdOrderByDataHoraInicioDesc(medicoId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(estabelecimentoId, tenantId, pageable);
        } else if (tipoPlantao != null) {
            page = repository.findByTipoPlantaoAndTenantIdOrderByDataHoraInicioDesc(tipoPlantao, tenantId, pageable);
        } else if (dataInicio != null && dataFim != null) {
            page = repository.findByDataHoraInicioBetweenAndTenantIdOrderByDataHoraInicioDesc(dataInicio, dataFim, tenantId, pageable);
        } else if (Boolean.TRUE.equals(emAndamento)) {
            OffsetDateTime agora = OffsetDateTime.now();
            page = repository.findEmAndamentoAndTenantIdOrderByDataHoraInicioDesc(agora, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantoesCacheKeyGenerator")
    public PlantaoResponse atualizar(UUID id, PlantaoRequest request) {
        log.debug("Atualizando plantão. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do plantão é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Plantao updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantoesCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo plantão. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do plantão é obrigatório");
        }

        Plantao entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Plantão excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
