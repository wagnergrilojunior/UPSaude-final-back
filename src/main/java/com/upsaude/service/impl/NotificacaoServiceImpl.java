package com.upsaude.service.impl;

import com.upsaude.api.request.NotificacaoRequest;
import com.upsaude.api.response.NotificacaoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Notificacao;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.NotificacaoRepository;
import com.upsaude.service.NotificacaoService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.notificacao.NotificacaoCreator;
import com.upsaude.service.support.notificacao.NotificacaoDomainService;
import com.upsaude.service.support.notificacao.NotificacaoResponseBuilder;
import com.upsaude.service.support.notificacao.NotificacaoTenantEnforcer;
import com.upsaude.service.support.notificacao.NotificacaoUpdater;
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

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

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
    private final NotificacaoDomainService domainService;
    private final NotificacaoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public NotificacaoResponse criar(NotificacaoRequest request) {
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
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_NOTIFICACOES, keyGenerator = "notificacaoCacheKeyGenerator")
    public NotificacaoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da notificação é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificacaoResponse> listar(Pageable pageable,
                                           UUID estabelecimentoId,
                                           UUID pacienteId,
                                           UUID profissionalId,
                                           UUID agendamentoId,
                                           String statusEnvio,
                                           OffsetDateTime inicio,
                                           OffsetDateTime fim,
                                           Boolean usarPrevista) {
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
        } else if (inicio != null || fim != null) {
            if (inicio == null || fim == null) {
                throw new BadRequestException("Para filtrar por período, informe 'inicio' e 'fim'");
            }
            boolean prevista = Boolean.TRUE.equals(usarPrevista);
            page = prevista
                ? repository.findByDataEnvioPrevistaBetweenAndTenantIdOrderByDataEnvioPrevistaDesc(inicio, fim, tenantId, pageable)
                : repository.findByDataEnvioBetweenAndTenantIdOrderByDataEnvioDesc(inicio, fim, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_NOTIFICACOES, keyGenerator = "notificacaoCacheKeyGenerator")
    public NotificacaoResponse atualizar(UUID id, NotificacaoRequest request) {
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
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da notificação é obrigatório");
        }

        Notificacao entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

