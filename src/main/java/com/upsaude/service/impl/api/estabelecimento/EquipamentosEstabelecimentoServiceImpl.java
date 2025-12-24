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

import com.upsaude.api.request.estabelecimento.EquipamentosEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.EquipamentosEstabelecimentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosEstabelecimentoRepository;
import com.upsaude.service.api.estabelecimento.EquipamentosEstabelecimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.equipamentosestabelecimento.EquipamentosEstabelecimentoCreator;
import com.upsaude.service.api.support.equipamentosestabelecimento.EquipamentosEstabelecimentoResponseBuilder;
import com.upsaude.service.api.support.equipamentosestabelecimento.EquipamentosEstabelecimentoTenantEnforcer;
import com.upsaude.service.api.support.equipamentosestabelecimento.EquipamentosEstabelecimentoUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        log.debug("Criando novo equipamento do estabelecimento");

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
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar equipamento do estabelecimento", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS_ESTABELECIMENTO, keyGenerator = "equipamentosEstabelecimentoCacheKeyGenerator")
    public EquipamentosEstabelecimentoResponse obterPorId(UUID id) {
        log.debug("Buscando equipamento do estabelecimento por ID: {} (cache miss)", id);
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
        log.debug("Listando equipamentos do estabelecimento paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<EquipamentosEstabelecimento> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS_ESTABELECIMENTO, keyGenerator = "equipamentosEstabelecimentoCacheKeyGenerator")
    public EquipamentosEstabelecimentoResponse atualizar(UUID id, EquipamentosEstabelecimentoRequest request) {
        log.debug("Atualizando equipamento do estabelecimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do equipamento do estabelecimento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        EquipamentosEstabelecimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_EQUIPAMENTOS_ESTABELECIMENTO, keyGenerator = "equipamentosEstabelecimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo equipamento do estabelecimento. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do equipamento do estabelecimento é obrigatório");
        }

        EquipamentosEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);
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
