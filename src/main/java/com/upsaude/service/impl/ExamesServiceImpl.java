package com.upsaude.service.impl;

import java.time.OffsetDateTime;
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

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import com.upsaude.entity.Exames;
import com.upsaude.entity.Tenant;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.AtendimentoRepository;
import com.upsaude.repository.ConsultasRepository;
import com.upsaude.repository.ExamesRepository;
import com.upsaude.service.ExamesService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.exames.ExamesCreator;
import com.upsaude.service.support.exames.ExamesDomainService;
import com.upsaude.service.support.exames.ExamesResponseBuilder;
import com.upsaude.service.support.exames.ExamesTenantEnforcer;
import com.upsaude.service.support.exames.ExamesUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamesServiceImpl implements ExamesService {

    private final ExamesRepository examesRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    // Mantidos para handler/validações (injeção indireta em support, mas alguns projetos usam direto)
    @SuppressWarnings("unused")
    private final AtendimentoRepository atendimentoRepository;
    @SuppressWarnings("unused")
    private final ConsultasRepository consultasRepository;

    private final ExamesCreator creator;
    private final ExamesUpdater updater;
    private final ExamesResponseBuilder responseBuilder;
    private final ExamesDomainService domainService;
    private final ExamesTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ExamesResponse criar(ExamesRequest request) {
        log.debug("Criando novo Exame");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Exames saved = creator.criar(request, tenantId, tenant);
            ExamesResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_EXAMES);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.exame(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar exame", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_EXAMES, keyGenerator = "examesCacheKeyGenerator")
    public ExamesResponse obterPorId(UUID id) {
        log.debug("Buscando Exame por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de Exame");
            throw new BadRequestException("ID do exame é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Exames exames = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        log.debug("Exame encontrado. ID: {}", id);
        return responseBuilder.build(exames);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamesResponse> listar(Pageable pageable) {
        return listar(pageable, null, null, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamesResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        return listar(pageable, estabelecimentoId, null, null, null);
    }

    @Transactional(readOnly = true)
    public Page<ExamesResponse> listar(Pageable pageable,
                                      UUID estabelecimentoId,
                                      UUID pacienteId,
                                      OffsetDateTime dataInicio,
                                      OffsetDateTime dataFim) {
        log.debug("Listando exames. Página: {}, Tamanho: {}, estabelecimentoId: {}, pacienteId: {}, dataInicio: {}, dataFim: {}",
            pageable.getPageNumber(), pageable.getPageSize(), estabelecimentoId, pacienteId, dataInicio, dataFim);

        UUID tenantId = tenantService.validarTenantAtual();

        Page<Exames> page;
        if (pacienteId != null && dataInicio != null && dataFim != null) {
            page = examesRepository.findByPacienteIdAndDataExameBetweenAndTenantIdOrderByDataExameDesc(pacienteId, dataInicio, dataFim, tenantId, pageable);
        } else if (pacienteId != null) {
            page = examesRepository.findByPacienteIdAndTenantIdOrderByDataExameDesc(pacienteId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = examesRepository.findByEstabelecimentoIdAndTenantIdOrderByDataExameDesc(estabelecimentoId, tenantId, pageable);
        } else if (dataInicio != null && dataFim != null) {
            page = examesRepository.findByDataExameBetweenAndTenantIdOrderByDataExameDesc(dataInicio, dataFim, tenantId, pageable);
        } else {
            page = examesRepository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_EXAMES, keyGenerator = "examesCacheKeyGenerator")
    public ExamesResponse atualizar(UUID id, ExamesRequest request) {
        log.debug("Atualizando Exame. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de Exame");
            throw new BadRequestException("ID do exame é obrigatório");
        }

        if (request == null) {
            log.warn("Request nulo recebido para atualização de Exame. ID: {}", id);
            throw new BadRequestException("Dados do exame são obrigatórios");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Exames updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_EXAMES, keyGenerator = "examesCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo Exame. ID: {}", id);

        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de Exame");
            throw new BadRequestException("ID do exame é obrigatório");
        }

        Exames exames = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(exames);
        exames.setActive(false);
        examesRepository.save(Objects.requireNonNull(exames));
        log.info("Exame excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
