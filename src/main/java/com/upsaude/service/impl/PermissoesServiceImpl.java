package com.upsaude.service.impl;

import com.upsaude.api.request.sistema.PermissoesRequest;
import com.upsaude.api.response.sistema.PermissoesResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.sistema.Permissoes;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.PermissoesRepository;
import com.upsaude.service.sistema.PermissoesService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.permissoes.PermissoesCreator;
import com.upsaude.service.support.permissoes.PermissoesDomainService;
import com.upsaude.service.support.permissoes.PermissoesResponseBuilder;
import com.upsaude.service.support.permissoes.PermissoesTenantEnforcer;
import com.upsaude.service.support.permissoes.PermissoesUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissoesServiceImpl implements PermissoesService {

    private final PermissoesRepository permissoesRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final PermissoesCreator creator;
    private final PermissoesUpdater updater;
    private final PermissoesResponseBuilder responseBuilder;
    private final PermissoesDomainService domainService;
    private final PermissoesTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public PermissoesResponse criar(PermissoesRequest request) {
        log.debug("Criando novo permissoes");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Permissoes saved = creator.criar(request, tenantId, tenant);
            PermissoesResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PERMISSOES);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.permissao(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar permissão. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir permissão", e);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar permissão", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PERMISSOES, keyGenerator = "permissoesCacheKeyGenerator")
    public PermissoesResponse obterPorId(UUID id) {
        log.debug("Buscando permissoes por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do permissoes é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Permissoes permissoes = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(permissoes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissoesResponse> listar(Pageable pageable) {
        return listar(pageable, null, null, null);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PERMISSOES, keyGenerator = "permissoesCacheKeyGenerator")
    public PermissoesResponse atualizar(UUID id, PermissoesRequest request) {
        log.debug("Atualizando permissoes. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do permissoes é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Permissoes updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PERMISSOES, keyGenerator = "permissoesCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo permissoes. ID: {}", id);

        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissoesResponse> listar(Pageable pageable, UUID estabelecimentoId, String modulo, String nome) {
        log.debug("Listando permissões. pageable: {}, estabelecimentoId: {}, modulo: {}, nome: {}", pageable, estabelecimentoId, modulo, nome);

        UUID tenantId = tenantService.validarTenantAtual();

        Page<Permissoes> page;
        if (estabelecimentoId != null) {
            page = permissoesRepository.findByEstabelecimentoIdAndTenantId(estabelecimentoId, tenantId, pageable);
        } else if (modulo != null && !modulo.isBlank()) {
            page = permissoesRepository.findByModuloAndTenantId(modulo, tenantId, pageable);
        } else if (nome != null && !nome.isBlank()) {
            page = permissoesRepository.findByNomeContainingIgnoreCaseAndTenantId(nome, tenantId, pageable);
        } else {
            page = permissoesRepository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do permissoes é obrigatório");
        }

        Permissoes entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        permissoesRepository.save(Objects.requireNonNull(entity));
        log.info("Permissoes excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
