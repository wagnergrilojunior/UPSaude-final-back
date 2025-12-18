package com.upsaude.service.impl;

import com.upsaude.api.request.medicacao.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.medicacao.DispensacoesMedicamentosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.medicacao.DispensacoesMedicamentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.medicacao.DispensacoesMedicamentosRepository;
import com.upsaude.service.medicacao.DispensacoesMedicamentosService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.dispensacoesmedicamentos.DispensacoesMedicamentosCreator;
import com.upsaude.service.support.dispensacoesmedicamentos.DispensacoesMedicamentosResponseBuilder;
import com.upsaude.service.support.dispensacoesmedicamentos.DispensacoesMedicamentosTenantEnforcer;
import com.upsaude.service.support.dispensacoesmedicamentos.DispensacoesMedicamentosUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacoesMedicamentosServiceImpl implements DispensacoesMedicamentosService {

    private final DispensacoesMedicamentosRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final DispensacoesMedicamentosCreator creator;
    private final DispensacoesMedicamentosUpdater updater;
    private final DispensacoesMedicamentosResponseBuilder responseBuilder;
    private final DispensacoesMedicamentosTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public DispensacoesMedicamentosResponse criar(DispensacoesMedicamentosRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            DispensacoesMedicamentos saved = creator.criar(request, tenantId, tenant);
            DispensacoesMedicamentosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_DISPENSACOES_MEDICAMENTOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.dispensacaoMedicamento(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar dispensação de medicamento", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_DISPENSACOES_MEDICAMENTOS, keyGenerator = "dispensacoesMedicamentosCacheKeyGenerator")
    public DispensacoesMedicamentosResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da dispensação de medicamento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        DispensacoesMedicamentos entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispensacoesMedicamentosResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<DispensacoesMedicamentos> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispensacoesMedicamentosResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Page<DispensacoesMedicamentos> page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataDispensacaoDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_DISPENSACOES_MEDICAMENTOS, keyGenerator = "dispensacoesMedicamentosCacheKeyGenerator")
    public DispensacoesMedicamentosResponse atualizar(UUID id, DispensacoesMedicamentosRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da dispensação de medicamento é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            DispensacoesMedicamentos updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar dispensação de medicamento", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_DISPENSACOES_MEDICAMENTOS, keyGenerator = "dispensacoesMedicamentosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da dispensação de medicamento é obrigatório");
        }

        DispensacoesMedicamentos entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Dispensação de medicamento já está inativa");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Dispensação de medicamento excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
