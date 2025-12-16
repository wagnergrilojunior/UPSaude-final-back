package com.upsaude.service.impl;

import com.upsaude.api.request.PlantaoRequest;
import com.upsaude.api.response.PlantaoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Plantao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoPlantaoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.PlantaoRepository;
import com.upsaude.service.PlantaoService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.plantao.PlantaoCreator;
import com.upsaude.service.support.plantao.PlantaoDomainService;
import com.upsaude.service.support.plantao.PlantaoResponseBuilder;
import com.upsaude.service.support.plantao.PlantaoTenantEnforcer;
import com.upsaude.service.support.plantao.PlantaoUpdater;
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
public class PlantaoServiceImpl implements PlantaoService {

    private final PlantaoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final PlantaoCreator creator;
    private final PlantaoUpdater updater;
    private final PlantaoResponseBuilder responseBuilder;
    private final PlantaoDomainService domainService;
    private final PlantaoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public PlantaoResponse criar(PlantaoRequest request) {
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
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantaoCacheKeyGenerator")
    public PlantaoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do plantão é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Plantao entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantaoCacheKeyGenerator")
    public PlantaoResponse atualizar(UUID id, PlantaoRequest request) {
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
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantaoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlantaoResponse> listar(Pageable pageable,
                                       UUID profissionalId,
                                       UUID medicoId,
                                       UUID estabelecimentoId,
                                       TipoPlantaoEnum tipoPlantao,
                                       OffsetDateTime dataInicio,
                                       OffsetDateTime dataFim,
                                       Boolean emAndamento) {
        UUID tenantId = tenantService.validarTenantAtual();

        if (Boolean.TRUE.equals(emAndamento)) {
            if (profissionalId == null) {
                throw new BadRequestException("profissionalId é obrigatório quando emAndamento=true");
            }
            OffsetDateTime agora = OffsetDateTime.now();
            List<Plantao> list = repository.findByProfissionalIdAndTenantIdAndDataHoraInicioLessThanEqualAndDataHoraFimGreaterThanEqual(
                profissionalId, tenantId, agora, agora);
            org.springframework.data.domain.Pageable safePageable = (pageable == null)
                ? org.springframework.data.domain.Pageable.unpaged()
                : pageable;
            return new org.springframework.data.domain.PageImpl<>(list, safePageable, list.size()).map(responseBuilder::build);
        }

        Page<Plantao> page;
        if (profissionalId != null && dataInicio != null && dataFim != null) {
            page = repository.findByProfissionalIdAndDataHoraInicioBetweenAndTenantIdOrderByDataHoraInicioAsc(
                profissionalId, dataInicio, dataFim, tenantId, pageable);
        } else if (estabelecimentoId != null && dataInicio != null && dataFim != null) {
            page = repository.findByEstabelecimentoIdAndDataHoraInicioBetweenAndTenantIdOrderByDataHoraInicioAsc(
                estabelecimentoId, dataInicio, dataFim, tenantId, pageable);
        } else if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDataHoraInicioDesc(profissionalId, tenantId, pageable);
        } else if (medicoId != null) {
            page = repository.findByMedicoIdAndTenantIdOrderByDataHoraInicioDesc(medicoId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(estabelecimentoId, tenantId, pageable);
        } else if (tipoPlantao != null) {
            page = repository.findByTipoPlantaoAndTenantIdOrderByDataHoraInicioDesc(tipoPlantao, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do plantão é obrigatório");
        }

        Plantao entity = tenantEnforcer.validarAcesso(id, tenantId);
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

