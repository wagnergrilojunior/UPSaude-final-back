package com.upsaude.service.impl;

import com.upsaude.api.request.equipamento.EquipamentosRequest;
import com.upsaude.api.response.equipamento.EquipamentosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.equipamento.Equipamentos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosRepository;
import com.upsaude.service.equipamento.EquipamentosService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.equipamentos.EquipamentosCreator;
import com.upsaude.service.support.equipamentos.EquipamentosResponseBuilder;
import com.upsaude.service.support.equipamentos.EquipamentosTenantEnforcer;
import com.upsaude.service.support.equipamentos.EquipamentosUpdater;
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

import java.util.Objects;
import java.util.UUID;

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
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar equipamento", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS, keyGenerator = "equipamentosCacheKeyGenerator")
    public EquipamentosResponse obterPorId(UUID id) {
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
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Equipamentos> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS, keyGenerator = "equipamentosCacheKeyGenerator")
    public EquipamentosResponse atualizar(UUID id, EquipamentosRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do equipamento é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Equipamentos updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar equipamento", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS, keyGenerator = "equipamentosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do equipamento é obrigatório");
        }

        Equipamentos entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Equipamento já está inativo");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
