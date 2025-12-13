package com.upsaude.service.impl;

import com.upsaude.api.request.CirurgiaRequest;
import com.upsaude.api.response.CirurgiaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusCirurgiaEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CirurgiaRepository;
import com.upsaude.service.CirurgiaService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.cirurgia.CirurgiaCreator;
import com.upsaude.service.support.cirurgia.CirurgiaDomainService;
import com.upsaude.service.support.cirurgia.CirurgiaResponseBuilder;
import com.upsaude.service.support.cirurgia.CirurgiaTenantEnforcer;
import com.upsaude.service.support.cirurgia.CirurgiaUpdater;
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

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CirurgiaServiceImpl implements CirurgiaService {

    private final CirurgiaRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final CirurgiaCreator creator;
    private final CirurgiaUpdater updater;
    private final CirurgiaTenantEnforcer tenantEnforcer;
    private final CirurgiaResponseBuilder responseBuilder;
    private final CirurgiaDomainService domainService;

    @Override
    @Transactional
    public CirurgiaResponse criar(CirurgiaRequest request) {
        log.debug("Criando cirurgia. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            Cirurgia saved = creator.criar(request, tenantId, tenant);
            CirurgiaResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CIRURGIAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.cirurgia(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar cirurgia. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar cirurgia. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir cirurgia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar cirurgia. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CIRURGIAS, keyGenerator = "cirurgiaCacheKeyGenerator")
    public CirurgiaResponse obterPorId(UUID id) {
        log.debug("Buscando cirurgia por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID da cirurgia é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Cirurgia entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
            return responseBuilder.build(entity);
        } catch (NotFoundException e) {
            log.warn("Cirurgia não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar cirurgia. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar cirurgia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar cirurgia. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CirurgiaResponse> listar(Pageable pageable) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar cirurgias. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar cirurgias", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar cirurgias. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CirurgiaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            return repository.findByPacienteIdAndTenantIdOrderByDataHoraPrevistaDesc(pacienteId, tenantId, pageable)
                .map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar cirurgias por paciente. PacienteId: {}, Pageable: {}", pacienteId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar cirurgias por paciente", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CirurgiaResponse> listarPorCirurgiaoPrincipal(UUID cirurgiaoPrincipalId, Pageable pageable) {
        if (cirurgiaoPrincipalId == null) {
            throw new BadRequestException("ID do cirurgião principal é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            return repository.findByCirurgiaoPrincipalIdAndTenantIdOrderByDataHoraPrevistaAsc(cirurgiaoPrincipalId, tenantId, pageable)
                .map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar cirurgias por cirurgião principal. CirurgiaoId: {}, Pageable: {}", cirurgiaoPrincipalId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar cirurgias por cirurgião principal", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CirurgiaResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            return repository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraPrevistaAsc(estabelecimentoId, tenantId, pageable)
                .map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar cirurgias por estabelecimento. EstabelecimentoId: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar cirurgias por estabelecimento", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CirurgiaResponse> listarPorStatus(StatusCirurgiaEnum status, Pageable pageable) {
        if (status == null) {
            throw new BadRequestException("Status da cirurgia é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            return repository.findByStatusAndTenantIdOrderByDataHoraPrevistaAsc(status, tenantId, pageable)
                .map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar cirurgias por status. Status: {}, Pageable: {}", status, pageable, e);
            throw new InternalServerErrorException("Erro ao listar cirurgias por status", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CirurgiaResponse> listarPorPeriodo(OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable) {
        if (inicio == null || fim == null) {
            throw new BadRequestException("Parâmetros inicio e fim são obrigatórios");
        }
        if (fim.isBefore(inicio)) {
            throw new BadRequestException("Parâmetro fim não pode ser anterior ao início");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            return repository.findByDataHoraPrevistaBetweenAndTenantIdOrderByDataHoraPrevistaAsc(inicio, fim, tenantId, pageable)
                .map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar cirurgias por período. Inicio: {}, Fim: {}, Pageable: {}", inicio, fim, pageable, e);
            throw new InternalServerErrorException("Erro ao listar cirurgias por período", e);
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CIRURGIAS, keyGenerator = "cirurgiaCacheKeyGenerator")
    public CirurgiaResponse atualizar(UUID id, CirurgiaRequest request) {
        log.debug("Atualizando cirurgia. ID: {}, Request: {}", id, request);

        if (id == null) {
            throw new BadRequestException("ID da cirurgia é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            Cirurgia updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar cirurgia. ID: {}, erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar cirurgia. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar cirurgia", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CIRURGIAS, keyGenerator = "cirurgiaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo cirurgia. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir cirurgia. ID: {}, erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir cirurgia. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir cirurgia", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da cirurgia é obrigatório");
        }
        Cirurgia entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Cirurgia excluída (desativada) com sucesso. ID: {}", id);
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}

