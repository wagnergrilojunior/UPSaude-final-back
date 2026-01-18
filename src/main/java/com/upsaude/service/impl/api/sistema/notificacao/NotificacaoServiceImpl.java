package com.upsaude.service.impl.api.sistema.notificacao;

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

import com.upsaude.api.request.sistema.notificacao.NotificacaoRequest;
import com.upsaude.api.response.sistema.notificacao.NotificacaoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.sistema.notificacao.NotificacaoRepository;
import com.upsaude.service.api.sistema.notificacao.NotificacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.notificacao.NotificacaoCreator;
import com.upsaude.service.api.support.notificacao.NotificacaoDomainService;
import com.upsaude.service.api.support.notificacao.NotificacaoResponseBuilder;
import com.upsaude.service.api.support.notificacao.NotificacaoTenantEnforcer;
import com.upsaude.service.api.support.notificacao.NotificacaoUpdater;
import org.springframework.dao.DataAccessException;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacaoServiceImpl implements NotificacaoService {

    private final NotificacaoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final NotificacaoCreator creator;
    private final NotificacaoUpdater updater;
    private final NotificacaoResponseBuilder responseBuilder;
    private final NotificacaoTenantEnforcer tenantEnforcer;
    private final NotificacaoDomainService domainService;

    @Override
    @Transactional
    public NotificacaoResponse criar(NotificacaoRequest request) {
        log.debug("Criando nova notificação");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Notificacao saved = creator.criar(request, tenantId, tenant);
            NotificacaoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_NOTIFICACOES);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.notificacao(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar notificação", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_NOTIFICACOES, keyGenerator = "notificacaoCacheKeyGenerator")
    public NotificacaoResponse obterPorId(UUID id) {
        log.debug("Buscando notificação por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da notificação é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Notificacao entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificacaoResponse> listar(Pageable pageable, UUID estabelecimentoId, UUID pacienteId, UUID profissionalId, UUID agendamentoId, String statusEnvio, OffsetDateTime inicio, OffsetDateTime fim, Boolean usarPrevista) {
        log.debug("Listando notificações paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Notificacao> page;

        if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataEnvioDesc(estabelecimentoId, tenantId, pageable);
        } else if (pacienteId != null) {
            page = repository.findByPacienteIdAndTenantIdOrderByDataEnvioDesc(pacienteId, tenantId, pageable);
        } else if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDataEnvioDesc(profissionalId, tenantId, pageable);
        } else if (agendamentoId != null) {
            page = repository.findByAgendamentoIdAndTenantIdOrderByDataEnvioDesc(agendamentoId, tenantId, pageable);
        } else if (statusEnvio != null && !statusEnvio.isBlank()) {
            page = repository.findByStatusEnvioAndTenantIdOrderByDataEnvioDesc(statusEnvio, tenantId, pageable);
        } else if (inicio != null && fim != null) {
            if (Boolean.TRUE.equals(usarPrevista)) {
                page = repository.findByDataEnvioPrevistaBetweenAndTenantIdOrderByDataEnvioPrevistaDesc(inicio, fim, tenantId, pageable);
            } else {
                page = repository.findByDataEnvioBetweenAndTenantIdOrderByDataEnvioDesc(inicio, fim, tenantId, pageable);
            }
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_NOTIFICACOES, keyGenerator = "notificacaoCacheKeyGenerator")
    public NotificacaoResponse atualizar(UUID id, NotificacaoRequest request) {
        log.debug("Atualizando notificação. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da notificação é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Notificacao updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_NOTIFICACOES, keyGenerator = "notificacaoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo notificação permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Notificacao entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Notificação excluída permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao excluir Notificacao. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Notificacao. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Notificacao", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_NOTIFICACOES, keyGenerator = "notificacaoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando notificação. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao inativar Notificacao. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Notificacao. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Notificacao", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da notificação é obrigatório");
        }

        Notificacao entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Notificação inativada com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
