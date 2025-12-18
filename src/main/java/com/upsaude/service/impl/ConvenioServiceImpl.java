package com.upsaude.service.impl;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.service.convenio.ConvenioService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.convenio.ConvenioCreator;
import com.upsaude.service.support.convenio.ConvenioDomainService;
import com.upsaude.service.support.convenio.ConvenioResponseBuilder;
import com.upsaude.service.support.convenio.ConvenioTenantEnforcer;
import com.upsaude.service.support.convenio.ConvenioUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioServiceImpl implements ConvenioService {

    private final ConvenioRepository convenioRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ConvenioCreator creator;
    private final ConvenioUpdater updater;
    private final ConvenioTenantEnforcer tenantEnforcer;
    private final ConvenioResponseBuilder responseBuilder;
    private final ConvenioDomainService domainService;

    @Override
    @Transactional
    public ConvenioResponse criar(ConvenioRequest request) {
        log.debug("Criando novo convênio. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            Convenio convenio = creator.criar(request, tenantId, tenant);
            ConvenioResponse response = responseBuilder.build(convenio);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CONVENIOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.convenio(tenantId, convenio.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar convênio. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar convênio. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir convênio", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar convênio. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONVENIOS, keyGenerator = "convenioCacheKeyGenerator")
    public ConvenioResponse obterPorId(UUID id) {
        log.debug("Buscando convênio por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de convênio");
            throw new BadRequestException("ID do convênio é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Convenio convenio = tenantEnforcer.validarAcessoCompleto(id, tenantId);

            log.debug("Convênio encontrado. ID: {}", id);
            return responseBuilder.build(convenio);
        } catch (NotFoundException e) {
            log.warn("Convênio não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar convênio. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar convênio", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar convênio. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConvenioResponse> listar(Pageable pageable) {
        log.debug("Listando convênios paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Page<Convenio> convenios = convenioRepository.findAllByTenant(tenantId, pageable);
            log.debug("Listagem de convênios concluída. Total de elementos: {}", convenios.getTotalElements());
            return convenios.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar convênios. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar convênios", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar convênios. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONVENIOS, keyGenerator = "convenioCacheKeyGenerator")
    public ConvenioResponse atualizar(UUID id, ConvenioRequest request) {
        log.debug("Atualizando convênio. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de convênio");
            throw new BadRequestException("ID do convênio é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de convênio. ID: {}", id);
            throw new BadRequestException("Dados do convênio são obrigatórios");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            Convenio convenioAtualizado = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(convenioAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar convênio não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar convênio. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar convênio. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar convênio", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar convênio. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONVENIOS, keyGenerator = "convenioCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo convênio. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir convênio não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir convênio. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir convênio. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir convênio", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir convênio. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de convênio");
            throw new BadRequestException("ID do convênio é obrigatório");
        }

        Convenio convenio = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(convenio);
        convenio.setActive(false);
        convenioRepository.save(Objects.requireNonNull(convenio));
        log.info("Convênio excluído (desativado) com sucesso. ID: {}", id);
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
