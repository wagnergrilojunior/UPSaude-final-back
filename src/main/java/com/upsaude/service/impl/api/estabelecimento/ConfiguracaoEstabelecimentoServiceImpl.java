package com.upsaude.service.impl.api.estabelecimento;

import com.upsaude.api.request.estabelecimento.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ConfiguracaoEstabelecimentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.estabelecimento.ConfiguracaoEstabelecimentoRepository;
import com.upsaude.service.api.estabelecimento.ConfiguracaoEstabelecimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.configuracaoestabelecimento.ConfiguracaoEstabelecimentoCreator;
import com.upsaude.service.api.support.configuracaoestabelecimento.ConfiguracaoEstabelecimentoDomainService;
import com.upsaude.service.api.support.configuracaoestabelecimento.ConfiguracaoEstabelecimentoResponseBuilder;
import com.upsaude.service.api.support.configuracaoestabelecimento.ConfiguracaoEstabelecimentoTenantEnforcer;
import com.upsaude.service.api.support.configuracaoestabelecimento.ConfiguracaoEstabelecimentoUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfiguracaoEstabelecimentoServiceImpl implements ConfiguracaoEstabelecimentoService {

    private final ConfiguracaoEstabelecimentoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ConfiguracaoEstabelecimentoCreator creator;
    private final ConfiguracaoEstabelecimentoUpdater updater;
    private final ConfiguracaoEstabelecimentoResponseBuilder responseBuilder;
    private final ConfiguracaoEstabelecimentoDomainService domainService;
    private final ConfiguracaoEstabelecimentoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ConfiguracaoEstabelecimentoResponse criar(ConfiguracaoEstabelecimentoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ConfiguracaoEstabelecimento saved = creator.criar(request, tenantId, tenant);
        ConfiguracaoEstabelecimentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CONFIGURACOES_ESTABELECIMENTO);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.configuracaoEstabelecimento(tenantId, saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONFIGURACOES_ESTABELECIMENTO, keyGenerator = "configuracaoEstabelecimentoCacheKeyGenerator")
    public ConfiguracaoEstabelecimentoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da configuração do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracaoEstabelecimentoResponse obterPorEstabelecimento(UUID estabelecimentoId) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.obterPorEstabelecimentoOrThrow(estabelecimentoId, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfiguracaoEstabelecimentoResponse> listar(Pageable pageable, UUID estabelecimentoId) {
        UUID tenantId = tenantService.validarTenantAtual();

        if (estabelecimentoId != null) {
            ConfiguracaoEstabelecimento entity = tenantEnforcer.obterPorEstabelecimentoOrThrow(estabelecimentoId, tenantId);
            List<ConfiguracaoEstabelecimento> list = List.of(entity);
            Pageable safePageable = (pageable == null) ? Pageable.unpaged() : pageable;
            return new PageImpl<>(list, safePageable, list.size()).map(responseBuilder::build);
        }

        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONFIGURACOES_ESTABELECIMENTO, keyGenerator = "configuracaoEstabelecimentoCacheKeyGenerator")
    public ConfiguracaoEstabelecimentoResponse atualizar(UUID id, ConfiguracaoEstabelecimentoRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da configuração do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ConfiguracaoEstabelecimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONFIGURACOES_ESTABELECIMENTO, keyGenerator = "configuracaoEstabelecimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da configuração do estabelecimento é obrigatório");
        }

        ConfiguracaoEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);
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

