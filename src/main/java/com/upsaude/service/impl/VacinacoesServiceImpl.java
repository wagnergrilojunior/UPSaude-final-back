package com.upsaude.service.impl;

import com.upsaude.api.request.VacinacoesRequest;
import com.upsaude.api.response.VacinacoesResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.Vacinacoes;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.VacinacoesRepository;
import com.upsaude.service.TenantService;
import com.upsaude.service.VacinacoesService;
import com.upsaude.service.support.vacinacoes.VacinacoesCreator;
import com.upsaude.service.support.vacinacoes.VacinacoesDomainService;
import com.upsaude.service.support.vacinacoes.VacinacoesResponseBuilder;
import com.upsaude.service.support.vacinacoes.VacinacoesTenantEnforcer;
import com.upsaude.service.support.vacinacoes.VacinacoesUpdater;
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
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacinacoesServiceImpl implements VacinacoesService {

    private final VacinacoesRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final VacinacoesCreator creator;
    private final VacinacoesUpdater updater;
    private final VacinacoesResponseBuilder responseBuilder;
    private final VacinacoesDomainService domainService;
    private final VacinacoesTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public VacinacoesResponse criar(VacinacoesRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Vacinacoes saved = creator.criar(request, tenantId, tenant);
        VacinacoesResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_VACINACOES);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.vacinacao(tenantId, saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_VACINACOES, keyGenerator = "vacinacoesCacheKeyGenerator")
    public VacinacoesResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da vacinação é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VacinacoesResponse> listar(Pageable pageable,
                                          UUID estabelecimentoId,
                                          UUID pacienteId,
                                          UUID vacinaId,
                                          OffsetDateTime inicio,
                                          OffsetDateTime fim) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<Vacinacoes> page;
        if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataAplicacaoDesc(estabelecimentoId, tenantId, pageable);
        } else if (pacienteId != null) {
            page = repository.findByPacienteIdAndTenantIdOrderByDataAplicacaoDesc(pacienteId, tenantId, pageable);
        } else if (vacinaId != null) {
            page = repository.findByVacinaIdAndTenantIdOrderByDataAplicacaoDesc(vacinaId, tenantId, pageable);
        } else if (inicio != null || fim != null) {
            if (inicio == null || fim == null) {
                throw new BadRequestException("Para filtrar por período, informe 'inicio' e 'fim'");
            }
            page = repository.findByDataAplicacaoBetweenAndTenantIdOrderByDataAplicacaoDesc(inicio, fim, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_VACINACOES, keyGenerator = "vacinacoesCacheKeyGenerator")
    public VacinacoesResponse atualizar(UUID id, VacinacoesRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da vacinação é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Vacinacoes updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_VACINACOES, keyGenerator = "vacinacoesCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da vacinação é obrigatório");
        }

        Vacinacoes entity = tenantEnforcer.validarAcesso(id, tenantId);
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
