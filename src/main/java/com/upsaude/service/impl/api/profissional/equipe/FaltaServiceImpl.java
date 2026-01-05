package com.upsaude.service.impl.api.profissional.equipe;

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

import com.upsaude.api.request.profissional.equipe.FaltaRequest;
import com.upsaude.api.response.profissional.equipe.FaltaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.equipe.Falta;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoFaltaEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.profissional.equipe.FaltaRepository;
import com.upsaude.service.api.profissional.equipe.FaltaService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.falta.FaltaCreator;
import com.upsaude.service.api.support.falta.FaltaDomainService;
import com.upsaude.service.api.support.falta.FaltaResponseBuilder;
import com.upsaude.service.api.support.falta.FaltaTenantEnforcer;
import com.upsaude.service.api.support.falta.FaltaUpdater;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final FaltaTenantEnforcer tenantEnforcer;
    private final FaltaDomainService domainService;

    @Override
    @Transactional
    public FaltaResponse criar(FaltaRequest request) {
        log.debug("Criando nova falta");

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
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar falta", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltasCacheKeyGenerator")
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
    public Page<FaltaResponse> listar(Pageable pageable, UUID profissionalId, UUID medicoId, UUID estabelecimentoId, TipoFaltaEnum tipoFalta, LocalDate dataInicio, LocalDate dataFim) {
        log.debug("Listando faltas paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Falta> page;

        if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDataFaltaDesc(profissionalId, tenantId, pageable);
        } else if (medicoId != null) {
            page = repository.findByMedicoIdAndTenantIdOrderByDataFaltaDesc(medicoId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataFaltaDesc(estabelecimentoId, tenantId, pageable);
        } else if (tipoFalta != null) {
            page = repository.findByTipoFaltaAndTenantIdOrderByDataFaltaDesc(tipoFalta, tenantId, pageable);
        } else if (dataInicio != null && dataFim != null) {
            page = repository.findByDataFaltaBetweenAndTenantIdOrderByDataFaltaDesc(dataInicio, dataFim, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltasCacheKeyGenerator")
    public FaltaResponse atualizar(UUID id, FaltaRequest request) {
        log.debug("Atualizando falta. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da falta é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Falta updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo falta permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Falta entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Falta excluída permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao excluir Falta. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Falta. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Falta", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltasCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando falta. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao inativar Falta. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Falta. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Falta", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da falta é obrigatório");
        }

        Falta entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Falta inativada com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
