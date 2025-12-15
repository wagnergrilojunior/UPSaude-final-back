package com.upsaude.service.impl;

import com.upsaude.api.request.EquipamentosEstabelecimentoRequest;
import com.upsaude.api.response.EquipamentosEstabelecimentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.EquipamentosEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EquipamentosEstabelecimentoRepository;
import com.upsaude.service.EquipamentosEstabelecimentoService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.equipamentosestabelecimento.EquipamentosEstabelecimentoCreator;
import com.upsaude.service.support.equipamentosestabelecimento.EquipamentosEstabelecimentoResponseBuilder;
import com.upsaude.service.support.equipamentosestabelecimento.EquipamentosEstabelecimentoTenantEnforcer;
import com.upsaude.service.support.equipamentosestabelecimento.EquipamentosEstabelecimentoUpdater;
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
public class EquipamentosEstabelecimentoServiceImpl implements EquipamentosEstabelecimentoService {

    private final EquipamentosEstabelecimentoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final EquipamentosEstabelecimentoCreator creator;
    private final EquipamentosEstabelecimentoUpdater updater;
    private final EquipamentosEstabelecimentoResponseBuilder responseBuilder;
    private final EquipamentosEstabelecimentoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public EquipamentosEstabelecimentoResponse criar(EquipamentosEstabelecimentoRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            EquipamentosEstabelecimento saved = creator.criar(request, tenantId, tenant);
            EquipamentosEstabelecimentoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_EQUIPAMENTOS_ESTABELECIMENTO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.equipamentoEstabelecimento(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar equipamento do estabelecimento", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS_ESTABELECIMENTO, keyGenerator = "equipamentosEstabelecimentoCacheKeyGenerator")
    public EquipamentosEstabelecimentoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do equipamento do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        EquipamentosEstabelecimento entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipamentosEstabelecimentoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<EquipamentosEstabelecimento> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS_ESTABELECIMENTO, keyGenerator = "equipamentosEstabelecimentoCacheKeyGenerator")
    public EquipamentosEstabelecimentoResponse atualizar(UUID id, EquipamentosEstabelecimentoRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do equipamento do estabelecimento é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            EquipamentosEstabelecimento updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar equipamento do estabelecimento", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS_ESTABELECIMENTO, keyGenerator = "equipamentosEstabelecimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do equipamento do estabelecimento é obrigatório");
        }

        EquipamentosEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Equipamento do estabelecimento já está inativo");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento do estabelecimento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
