package com.upsaude.service.impl;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.api.response.agendamento.FilaEsperaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.FilaEsperaRepository;
import com.upsaude.service.agendamento.FilaEsperaService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.filaespera.FilaEsperaCreator;
import com.upsaude.service.support.filaespera.FilaEsperaDomainService;
import com.upsaude.service.support.filaespera.FilaEsperaResponseBuilder;
import com.upsaude.service.support.filaespera.FilaEsperaTenantEnforcer;
import com.upsaude.service.support.filaespera.FilaEsperaUpdater;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilaEsperaServiceImpl implements FilaEsperaService {

    private final FilaEsperaRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final FilaEsperaCreator creator;
    private final FilaEsperaUpdater updater;
    private final FilaEsperaResponseBuilder responseBuilder;
    private final FilaEsperaDomainService domainService;
    private final FilaEsperaTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public FilaEsperaResponse criar(FilaEsperaRequest request) {
        log.debug("Criando item na fila de espera. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            FilaEspera saved = creator.criar(request, tenantId, tenant);
            FilaEsperaResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_FILA_ESPERA);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.filaEspera(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar item na fila de espera. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir fila de espera", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_FILA_ESPERA, keyGenerator = "filaEsperaCacheKeyGenerator")
    public FilaEsperaResponse obterPorId(UUID id) {
        log.debug("Buscando item da fila de espera por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID do item da fila de espera é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        FilaEspera entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilaEsperaResponse> listar(Pageable pageable,
                                          UUID pacienteId,
                                          UUID profissionalId,
                                          UUID estabelecimentoId,
                                          PrioridadeAtendimentoEnum prioridade,
                                          OffsetDateTime dataInicio,
                                          OffsetDateTime dataFim) {
        log.debug("Listando fila de espera. pageable: {}, pacienteId: {}, profissionalId: {}, estabelecimentoId: {}, prioridade: {}, dataInicio: {}, dataFim: {}",
            pageable, pacienteId, profissionalId, estabelecimentoId, prioridade, dataInicio, dataFim);

        try {
            UUID tenantId = tenantService.validarTenantAtual();

            Page<FilaEspera> page;
            if (pacienteId != null) {
                page = repository.findByPacienteIdAndTenantIdOrderByDataEntradaDesc(pacienteId, tenantId, pageable);
            } else if (profissionalId != null) {
                page = repository.findByProfissionalIdAndTenantIdOrderByPrioridadeDescDataEntradaAsc(profissionalId, tenantId, pageable);
            } else if (estabelecimentoId != null) {
                page = repository.findByEstabelecimentoIdAndTenantIdOrderByPrioridadeDescDataEntradaAsc(estabelecimentoId, tenantId, pageable);
            } else if (prioridade != null) {
                page = repository.findByPrioridadeAndTenantIdOrderByDataEntradaAsc(prioridade, tenantId, pageable);
            } else if (dataInicio != null && dataFim != null) {
                page = repository.findByDataEntradaBetweenAndTenantIdOrderByPrioridadeDescDataEntradaAsc(dataInicio, dataFim, tenantId, pageable);
            } else {
                page = repository.findAllByTenant(tenantId, pageable);
            }

            return page.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar fila de espera", e);
            throw new InternalServerErrorException("Erro ao listar fila de espera", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilaEsperaResponse> listarPendentesSemAgendamento(UUID estabelecimentoId) {
        log.debug("Listando pendentes sem agendamento. estabelecimentoId: {}", estabelecimentoId);

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            List<FilaEspera> itens = repository.findByEstabelecimentoIdAndAgendamentoIdIsNullAndActiveTrueAndTenantIdOrderByPrioridadeDescDataEntradaAsc(estabelecimentoId, tenantId);
            return itens.stream().map(responseBuilder::build).toList();
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar pendentes sem agendamento", e);
            throw new InternalServerErrorException("Erro ao listar pendentes sem agendamento", e);
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_FILA_ESPERA, keyGenerator = "filaEsperaCacheKeyGenerator")
    public FilaEsperaResponse atualizar(UUID id, FilaEsperaRequest request) {
        log.debug("Atualizando item da fila de espera. ID: {}, request: {}", id, request);

        if (id == null) {
            throw new BadRequestException("ID do item da fila de espera é obrigatório");
        }
        if (request == null) {
            throw new BadRequestException("Dados da fila de espera são obrigatórios");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        FilaEspera updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FILA_ESPERA, keyGenerator = "filaEsperaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo item da fila de espera. ID: {}", id);

        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do item da fila de espera é obrigatório");
        }

        FilaEspera entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Item da fila de espera excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

