package com.upsaude.service.impl;

import com.upsaude.api.request.ServicosEstabelecimentoRequest;
import com.upsaude.api.response.ServicosEstabelecimentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.ServicosEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.ServicosEstabelecimentoRepository;
import com.upsaude.service.ServicosEstabelecimentoService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.servicosestabelecimento.ServicosEstabelecimentoCreator;
import com.upsaude.service.support.servicosestabelecimento.ServicosEstabelecimentoDomainService;
import com.upsaude.service.support.servicosestabelecimento.ServicosEstabelecimentoResponseBuilder;
import com.upsaude.service.support.servicosestabelecimento.ServicosEstabelecimentoTenantEnforcer;
import com.upsaude.service.support.servicosestabelecimento.ServicosEstabelecimentoUpdater;
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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicosEstabelecimentoServiceImpl implements ServicosEstabelecimentoService {

    private final ServicosEstabelecimentoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ServicosEstabelecimentoCreator creator;
    private final ServicosEstabelecimentoUpdater updater;
    private final ServicosEstabelecimentoResponseBuilder responseBuilder;
    private final ServicosEstabelecimentoDomainService domainService;
    private final ServicosEstabelecimentoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ServicosEstabelecimentoResponse criar(ServicosEstabelecimentoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ServicosEstabelecimento saved = creator.criar(request, tenantId, tenant);
        ServicosEstabelecimentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_SERVICOS_ESTABELECIMENTO);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.servicoEstabelecimento(tenantId, saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_SERVICOS_ESTABELECIMENTO, keyGenerator = "servicosEstabelecimentoCacheKeyGenerator")
    public ServicosEstabelecimentoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do serviço do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServicosEstabelecimentoResponse> listar(Pageable pageable,
                                                        UUID estabelecimentoId,
                                                        String nome,
                                                        String codigoCnes,
                                                        Boolean apenasAtivos) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<ServicosEstabelecimento> page;

        if (estabelecimentoId != null && Boolean.TRUE.equals(apenasAtivos)) {
            page = repository.findByEstabelecimentoIdAndActiveTrueAndTenantIdOrderByNomeAsc(estabelecimentoId, tenantId, pageable);
        } else if (estabelecimentoId != null && StringUtils.hasText(nome)) {
            page = repository.findByEstabelecimentoIdAndNomeContainingIgnoreCaseAndTenantIdOrderByNomeAsc(estabelecimentoId, nome, tenantId, pageable);
        } else if (estabelecimentoId != null && StringUtils.hasText(codigoCnes)) {
            List<ServicosEstabelecimento> list = repository.findByEstabelecimentoIdAndCodigoCnesAndTenantId(estabelecimentoId, codigoCnes, tenantId);
            Pageable safePageable = (pageable == null) ? Pageable.unpaged() : pageable;
            return new org.springframework.data.domain.PageImpl<>(list, safePageable, list.size()).map(responseBuilder::build);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByNomeAsc(estabelecimentoId, tenantId, pageable);
        } else if (StringUtils.hasText(nome)) {
            page = repository.findByNomeContainingIgnoreCaseAndTenantIdOrderByNomeAsc(nome, tenantId, pageable);
        } else if (StringUtils.hasText(codigoCnes)) {
            page = repository.findByCodigoCnesAndTenantIdOrderByNomeAsc(codigoCnes, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_SERVICOS_ESTABELECIMENTO, keyGenerator = "servicosEstabelecimentoCacheKeyGenerator")
    public ServicosEstabelecimentoResponse atualizar(UUID id, ServicosEstabelecimentoRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do serviço do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ServicosEstabelecimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_SERVICOS_ESTABELECIMENTO, keyGenerator = "servicosEstabelecimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do serviço do estabelecimento é obrigatório");
        }

        ServicosEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);
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

