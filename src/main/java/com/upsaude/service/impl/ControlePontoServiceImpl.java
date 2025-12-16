package com.upsaude.service.impl;

import com.upsaude.api.request.ControlePontoRequest;
import com.upsaude.api.response.ControlePontoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.ControlePonto;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.ControlePontoRepository;
import com.upsaude.service.ControlePontoService;
import com.upsaude.service.TenantService;
import com.upsaude.entity.Tenant;
import com.upsaude.service.support.controleponto.ControlePontoCreator;
import com.upsaude.service.support.controleponto.ControlePontoResponseBuilder;
import com.upsaude.service.support.controleponto.ControlePontoTenantEnforcer;
import com.upsaude.service.support.controleponto.ControlePontoUpdater;
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

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

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

    @Override
    @Transactional
    public ControlePontoResponse criar(ControlePontoRequest request) {
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
            throw new InternalServerErrorException("Erro ao criar registro de ponto", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONTROLE_PONTO, keyGenerator = "controlePontoCacheKeyGenerator")
    public ControlePontoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        ControlePonto entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listar(Pageable pageable) {
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
        Page<ControlePonto> page = repository.findByProfissionalIdAndTenantIdAndDataPontoOrderByDataHoraAsc(profissionalId, tenantId, data, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<ControlePonto> page = repository.findByProfissionalIdAndTenantIdAndDataPontoBetweenOrderByDataHoraAsc(profissionalId, tenantId, dataInicio, dataFim, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONTROLE_PONTO, keyGenerator = "controlePontoCacheKeyGenerator")
    public ControlePontoResponse atualizar(UUID id, ControlePontoRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            ControlePonto updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar registro de ponto", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONTROLE_PONTO, keyGenerator = "controlePontoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }

        ControlePonto entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Registro de ponto já está inativo");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Registro de ponto excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
