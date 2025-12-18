package com.upsaude.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.estabelecimento.departamento.DepartamentosRequest;
import com.upsaude.api.response.estabelecimento.departamento.DepartamentosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.estabelecimento.departamento.Departamentos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.departamento.DepartamentosRepository;
import com.upsaude.service.estabelecimento.departamento.DepartamentosService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.departamentos.DepartamentosCreator;
import com.upsaude.service.support.departamentos.DepartamentosDomainService;
import com.upsaude.service.support.departamentos.DepartamentosResponseBuilder;
import com.upsaude.service.support.departamentos.DepartamentosTenantEnforcer;
import com.upsaude.service.support.departamentos.DepartamentosUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartamentosServiceImpl implements DepartamentosService {

    private final DepartamentosRepository departamentosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final DepartamentosCreator creator;
    private final DepartamentosUpdater updater;
    private final DepartamentosResponseBuilder responseBuilder;
    private final DepartamentosDomainService domainService;
    private final DepartamentosTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public DepartamentosResponse criar(DepartamentosRequest request) {
        log.debug("Criando novo departamento. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Departamentos saved = creator.criar(request, tenantId, tenant);
            DepartamentosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_DEPARTAMENTOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.departamento(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar departamento. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar departamento. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir departamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar departamento. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_DEPARTAMENTOS, keyGenerator = "departamentosCacheKeyGenerator")
    public DepartamentosResponse obterPorId(UUID id) {
        log.debug("Buscando departamento por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de departamento");
            throw new BadRequestException("ID do departamento é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Departamentos departamentos = tenantEnforcer.validarAcessoCompleto(id, tenantId);

            log.debug("Departamento encontrado. ID: {}", id);
            return responseBuilder.build(departamentos);
        } catch (NotFoundException e) {
            log.warn("Departamento não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar departamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar departamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar departamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartamentosResponse> listar(Pageable pageable) {
        log.debug("Listando departamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Page<Departamentos> departamentos = departamentosRepository.findAllByTenant(tenantId, pageable);
            log.debug("Listagem de departamentos concluída. Total de elementos: {}", departamentos.getTotalElements());
            return departamentos.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar departamentos. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar departamentos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar departamentos. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_DEPARTAMENTOS, keyGenerator = "departamentosCacheKeyGenerator")
    public DepartamentosResponse atualizar(UUID id, DepartamentosRequest request) {
        log.debug("Atualizando departamento. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de departamento");
            throw new BadRequestException("ID do departamento é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de departamento. ID: {}", id);
            throw new BadRequestException("Dados do departamento são obrigatórios");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Departamentos updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar departamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar departamento. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar departamento. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar departamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar departamento. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_DEPARTAMENTOS, keyGenerator = "departamentosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo departamento. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir departamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir departamento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir departamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir departamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir departamento. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de departamento");
            throw new BadRequestException("ID do departamento é obrigatório");
        }

        Departamentos entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        departamentosRepository.save(Objects.requireNonNull(entity));
        log.info("Departamento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }

}
