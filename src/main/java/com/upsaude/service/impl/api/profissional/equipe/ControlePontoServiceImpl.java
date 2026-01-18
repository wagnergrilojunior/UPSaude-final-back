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

import com.upsaude.api.request.profissional.equipe.ControlePontoRequest;
import com.upsaude.api.response.profissional.equipe.ControlePontoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.equipe.ControlePonto;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.profissional.equipe.ControlePontoRepository;
import com.upsaude.service.api.profissional.equipe.ControlePontoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.controleponto.ControlePontoCreator;
import com.upsaude.service.api.support.controleponto.ControlePontoDomainService;
import com.upsaude.service.api.support.controleponto.ControlePontoResponseBuilder;
import com.upsaude.service.api.support.controleponto.ControlePontoTenantEnforcer;
import com.upsaude.service.api.support.controleponto.ControlePontoUpdater;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ControlePontoServiceImpl implements ControlePontoService {

    private final ControlePontoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ControlePontoCreator creator;
    private final ControlePontoUpdater updater;
    private final ControlePontoResponseBuilder responseBuilder;
    private final ControlePontoTenantEnforcer tenantEnforcer;
    private final ControlePontoDomainService domainService;

    @Override
    @Transactional
    public ControlePontoResponse criar(ControlePontoRequest request) {
        log.debug("Criando novo controle de ponto");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            ControlePonto saved = creator.criar(request, tenantId, tenant);
            ControlePontoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CONTROLE_PONTO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.controlePonto(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar controle de ponto", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONTROLE_PONTO, keyGenerator = "controlePontoCacheKeyGenerator")
    public ControlePontoResponse obterPorId(UUID id) {
        log.debug("Buscando controle de ponto por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do controle de ponto é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        ControlePonto entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listar(Pageable pageable) {
        log.debug("Listando controles de ponto paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<ControlePonto> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<ControlePonto> page = repository.findByProfissionalIdAndTenantIdOrderByDataHoraDesc(profissionalId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorMedico(UUID medicoId, Pageable pageable) {
        if (medicoId == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<ControlePonto> page = repository.findByMedicoIdAndTenantIdOrderByDataHoraDesc(medicoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<ControlePonto> page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorProfissionalEData(UUID profissionalId, LocalDate data, Pageable pageable) {
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (data == null) {
            throw new BadRequestException("Data é obrigatória");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<ControlePonto> page = repository.findByProfissionalIdAndDataPontoAndTenantIdOrderByDataHoraDesc(profissionalId, data, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            throw new BadRequestException("Data de início e data de fim são obrigatórias");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<ControlePonto> page = repository.findByProfissionalIdAndDataPontoBetweenAndTenantIdOrderByDataHoraDesc(profissionalId, dataInicio, dataFim, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONTROLE_PONTO, keyGenerator = "controlePontoCacheKeyGenerator")
    public ControlePontoResponse atualizar(UUID id, ControlePontoRequest request) {
        log.debug("Atualizando controle de ponto. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do controle de ponto é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ControlePonto updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONTROLE_PONTO, keyGenerator = "controlePontoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo controle de ponto permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            ControlePonto entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Controle de ponto excluído permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao excluir ControlePonto. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir ControlePonto. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir ControlePonto", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONTROLE_PONTO, keyGenerator = "controlePontoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando controle de ponto. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao inativar ControlePonto. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar ControlePonto. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar ControlePonto", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do controle de ponto é obrigatório");
        }

        ControlePonto entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Controle de ponto inativado com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
