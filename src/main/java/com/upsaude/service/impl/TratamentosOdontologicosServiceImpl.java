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

import com.upsaude.api.request.odontologia.TratamentosOdontologicosRequest;
import com.upsaude.api.response.odontologia.TratamentosOdontologicosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.entity.odontologia.TratamentosOdontologicos.StatusTratamento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.odontologia.TratamentosOdontologicosRepository;
import com.upsaude.service.odontologia.TratamentosOdontologicosService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.tratamentosodontologicos.TratamentosOdontologicosCreator;
import com.upsaude.service.support.tratamentosodontologicos.TratamentosOdontologicosResponseBuilder;
import com.upsaude.service.support.tratamentosodontologicos.TratamentosOdontologicosTenantEnforcer;
import com.upsaude.service.support.tratamentosodontologicos.TratamentosOdontologicosUpdater;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TratamentosOdontologicosServiceImpl implements TratamentosOdontologicosService {

    private final TratamentosOdontologicosRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final TratamentosOdontologicosCreator creator;
    private final TratamentosOdontologicosUpdater updater;
    private final TratamentosOdontologicosResponseBuilder responseBuilder;
    private final TratamentosOdontologicosTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public TratamentosOdontologicosResponse criar(TratamentosOdontologicosRequest request) {
        log.debug("Criando novo tratamento odontológico");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            TratamentosOdontologicos saved = creator.criar(request, tenantId, tenant);
            TratamentosOdontologicosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_TRATAMENTOS_ODONTOLOGICOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.tratamentoOdontologico(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar tratamento odontológico", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_TRATAMENTOS_ODONTOLOGICOS, keyGenerator = "tratamentosOdontologicosCacheKeyGenerator")
    public TratamentosOdontologicosResponse obterPorId(UUID id) {
        log.debug("Buscando tratamento odontológico por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do tratamento odontológico é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        TratamentosOdontologicos entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TratamentosOdontologicosResponse> listar(Pageable pageable, UUID estabelecimentoId, UUID pacienteId, UUID profissionalId, StatusTratamento status, OffsetDateTime inicio, OffsetDateTime fim) {
        log.debug("Listando tratamentos odontológicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<TratamentosOdontologicos> page;

        if (pacienteId != null) {
            page = repository.findByPacienteIdAndTenantIdOrderByDataInicioDesc(pacienteId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataInicioDesc(estabelecimentoId, tenantId, pageable);
        } else if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDataInicioDesc(profissionalId, tenantId, pageable);
        } else if (status != null) {
            page = repository.findByStatusAndTenantIdOrderByDataInicioDesc(status, tenantId, pageable);
        } else if (inicio != null && fim != null) {
            page = repository.findByDataInicioBetweenAndTenantIdOrderByDataInicioDesc(inicio, fim, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_TRATAMENTOS_ODONTOLOGICOS, keyGenerator = "tratamentosOdontologicosCacheKeyGenerator")
    public TratamentosOdontologicosResponse atualizar(UUID id, TratamentosOdontologicosRequest request) {
        log.debug("Atualizando tratamento odontológico. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do tratamento odontológico é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        TratamentosOdontologicos updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_TRATAMENTOS_ODONTOLOGICOS, keyGenerator = "tratamentosOdontologicosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo tratamento odontológico. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do tratamento odontológico é obrigatório");
        }

        TratamentosOdontologicos entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Tratamento odontológico excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
