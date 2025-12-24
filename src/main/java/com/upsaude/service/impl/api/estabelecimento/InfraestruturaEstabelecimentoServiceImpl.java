package com.upsaude.service.impl.api.estabelecimento;

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

import com.upsaude.api.request.estabelecimento.InfraestruturaEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.InfraestruturaEstabelecimentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.estabelecimento.InfraestruturaEstabelecimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.estabelecimento.InfraestruturaEstabelecimentoRepository;
import com.upsaude.service.api.estabelecimento.InfraestruturaEstabelecimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoCreator;
import com.upsaude.service.api.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoResponseBuilder;
import com.upsaude.service.api.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoTenantEnforcer;
import com.upsaude.service.api.support.infraestruturaestabelecimento.InfraestruturaEstabelecimentoUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfraestruturaEstabelecimentoServiceImpl implements InfraestruturaEstabelecimentoService {

    private final InfraestruturaEstabelecimentoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final InfraestruturaEstabelecimentoCreator creator;
    private final InfraestruturaEstabelecimentoUpdater updater;
    private final InfraestruturaEstabelecimentoResponseBuilder responseBuilder;
    private final InfraestruturaEstabelecimentoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public InfraestruturaEstabelecimentoResponse criar(InfraestruturaEstabelecimentoRequest request) {
        log.debug("Criando nova infraestrutura do estabelecimento");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            InfraestruturaEstabelecimento saved = creator.criar(request, tenantId, tenant);
            InfraestruturaEstabelecimentoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_INFRAESTRUTURA_ESTABELECIMENTO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.infraestruturaEstabelecimento(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar infraestrutura do estabelecimento", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_INFRAESTRUTURA_ESTABELECIMENTO, keyGenerator = "infraestruturaEstabelecimentoCacheKeyGenerator")
    public InfraestruturaEstabelecimentoResponse obterPorId(UUID id) {
        log.debug("Buscando infraestrutura do estabelecimento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da infraestrutura do estabelecimento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        InfraestruturaEstabelecimento entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InfraestruturaEstabelecimentoResponse> listar(Pageable pageable) {
        log.debug("Listando infraestruturas do estabelecimento paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<InfraestruturaEstabelecimento> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_INFRAESTRUTURA_ESTABELECIMENTO, keyGenerator = "infraestruturaEstabelecimentoCacheKeyGenerator")
    public InfraestruturaEstabelecimentoResponse atualizar(UUID id, InfraestruturaEstabelecimentoRequest request) {
        log.debug("Atualizando infraestrutura do estabelecimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da infraestrutura do estabelecimento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        InfraestruturaEstabelecimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_INFRAESTRUTURA_ESTABELECIMENTO, keyGenerator = "infraestruturaEstabelecimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo infraestrutura do estabelecimento. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da infraestrutura do estabelecimento é obrigatório");
        }

        InfraestruturaEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Infraestrutura do estabelecimento excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
