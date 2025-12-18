package com.upsaude.service.impl;

import com.upsaude.api.request.equipe.FaltaRequest;
import com.upsaude.api.response.equipe.FaltaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.equipe.Falta;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.TipoFaltaEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.FaltaRepository;
import com.upsaude.service.equipe.FaltaService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.falta.FaltaCreator;
import com.upsaude.service.support.falta.FaltaDomainService;
import com.upsaude.service.support.falta.FaltaResponseBuilder;
import com.upsaude.service.support.falta.FaltaTenantEnforcer;
import com.upsaude.service.support.falta.FaltaUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaltaServiceImpl implements FaltaService {

    private final FaltaRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final FaltaCreator creator;
    private final FaltaUpdater updater;
    private final FaltaResponseBuilder responseBuilder;
    private final FaltaDomainService domainService;
    private final FaltaTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public FaltaResponse criar(FaltaRequest request) {
        log.debug("Criando nova falta. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Falta saved = creator.criar(request, tenantId, tenant);
            FaltaResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_FALTAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.falta(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar falta. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir falta", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltaCacheKeyGenerator")
    public FaltaResponse obterPorId(UUID id) {
        log.debug("Buscando falta por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID da falta é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Falta entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FaltaResponse> listar(Pageable pageable,
                                     UUID profissionalId,
                                     UUID medicoId,
                                     UUID estabelecimentoId,
                                     TipoFaltaEnum tipoFalta,
                                     LocalDate dataInicio,
                                     LocalDate dataFim) {
        log.debug("Listando faltas. pageable: {}, profissionalId: {}, medicoId: {}, estabelecimentoId: {}, tipoFalta: {}, dataInicio: {}, dataFim: {}",
            pageable, profissionalId, medicoId, estabelecimentoId, tipoFalta, dataInicio, dataFim);

        try {
            UUID tenantId = tenantService.validarTenantAtual();

            Page<Falta> page;
            if (profissionalId != null && dataInicio != null && dataFim != null) {
                page = repository.findByProfissionalIdAndDataFaltaBetweenAndTenantIdOrderByDataFaltaDesc(profissionalId, dataInicio, dataFim, tenantId, pageable);
            } else if (estabelecimentoId != null && dataInicio != null && dataFim != null) {
                page = repository.findByEstabelecimentoIdAndDataFaltaBetweenAndTenantIdOrderByDataFaltaDesc(estabelecimentoId, dataInicio, dataFim, tenantId, pageable);
            } else if (profissionalId != null) {
                page = repository.findByProfissionalIdAndTenantIdOrderByDataFaltaDesc(profissionalId, tenantId, pageable);
            } else if (medicoId != null) {
                page = repository.findByMedicoIdAndTenantIdOrderByDataFaltaDesc(medicoId, tenantId, pageable);
            } else if (estabelecimentoId != null) {
                page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataFaltaDesc(estabelecimentoId, tenantId, pageable);
            } else if (tipoFalta != null) {
                page = repository.findByTipoFaltaAndTenantIdOrderByDataFaltaDesc(tipoFalta, tenantId, pageable);
            } else {
                page = repository.findAllByTenant(tenantId, pageable);
            }

            return page.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar faltas", e);
            throw new InternalServerErrorException("Erro ao listar faltas", e);
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltaCacheKeyGenerator")
    public FaltaResponse atualizar(UUID id, FaltaRequest request) {
        log.debug("Atualizando falta. ID: {}, request: {}", id, request);

        if (id == null) {
            throw new BadRequestException("ID da falta é obrigatório");
        }
        if (request == null) {
            throw new BadRequestException("Dados da falta são obrigatórios");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Falta updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo falta. ID: {}", id);

        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da falta é obrigatório");
        }

        Falta entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Falta excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

