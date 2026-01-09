package com.upsaude.service.impl.api.estabelecimento.equipamento;

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

import com.upsaude.api.request.estabelecimento.equipamento.EquipamentosRequest;
import com.upsaude.api.response.estabelecimento.equipamento.EquipamentosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosRepository;
import org.springframework.dao.DataAccessException;
import com.upsaude.service.api.estabelecimento.equipamento.EquipamentosService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.equipamentos.EquipamentosCreator;
import com.upsaude.service.api.support.equipamentos.EquipamentosResponseBuilder;
import com.upsaude.service.api.support.equipamentos.EquipamentosTenantEnforcer;
import com.upsaude.service.api.support.equipamentos.EquipamentosUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipamentosServiceImpl implements EquipamentosService {

    private final EquipamentosRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final EquipamentosCreator creator;
    private final EquipamentosUpdater updater;
    private final EquipamentosResponseBuilder responseBuilder;
    private final EquipamentosTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public EquipamentosResponse criar(EquipamentosRequest request) {
        log.debug("Criando novo equipamento");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Equipamentos saved = creator.criar(request, tenantId, tenant);
            EquipamentosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_EQUIPAMENTOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.equipamento(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar equipamento", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS, keyGenerator = "equipamentosCacheKeyGenerator")
    public EquipamentosResponse obterPorId(UUID id) {
        log.debug("Buscando equipamento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do equipamento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Equipamentos entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipamentosResponse> listar(Pageable pageable) {
        log.debug("Listando equipamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Equipamentos> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS, keyGenerator = "equipamentosCacheKeyGenerator")
    public EquipamentosResponse atualizar(UUID id, EquipamentosRequest request) {
        log.debug("Atualizando equipamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do equipamento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Equipamentos updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS, keyGenerator = "equipamentosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo equipamento permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Equipamentos entity = tenantEnforcer.validarAcesso(id, tenantId);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Equipamento excluído permanentemente com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir equipamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir equipamento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir equipamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir equipamento", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao excluir equipamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS, keyGenerator = "equipamentosCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando equipamento. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (NotFoundException e) {
            log.warn("Tentativa de inativar equipamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao inativar equipamento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar equipamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao inativar equipamento", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao inativar equipamento. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do equipamento é obrigatório");
        }

        Equipamentos entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento inativado com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
