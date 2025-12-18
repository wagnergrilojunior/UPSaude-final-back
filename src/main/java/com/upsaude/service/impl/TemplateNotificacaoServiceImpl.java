package com.upsaude.service.impl;

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

import com.upsaude.api.request.sistema.notificacao.TemplateNotificacaoRequest;
import com.upsaude.api.response.sistema.notificacao.TemplateNotificacaoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.sistema.notificacao.TemplateNotificacaoRepository;
import com.upsaude.service.sistema.notificacao.TemplateNotificacaoService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.templatenotificacao.TemplateNotificacaoCreator;
import com.upsaude.service.support.templatenotificacao.TemplateNotificacaoResponseBuilder;
import com.upsaude.service.support.templatenotificacao.TemplateNotificacaoTenantEnforcer;
import com.upsaude.service.support.templatenotificacao.TemplateNotificacaoUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateNotificacaoServiceImpl implements TemplateNotificacaoService {

    private final TemplateNotificacaoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final TemplateNotificacaoCreator creator;
    private final TemplateNotificacaoUpdater updater;
    private final TemplateNotificacaoResponseBuilder responseBuilder;
    private final TemplateNotificacaoTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public TemplateNotificacaoResponse criar(TemplateNotificacaoRequest request) {
        log.debug("Criando novo template de notificação");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            TemplateNotificacao saved = creator.criar(request, tenantId, tenant);
            TemplateNotificacaoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_TEMPLATES_NOTIFICACAO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.templateNotificacao(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar template de notificação", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_TEMPLATES_NOTIFICACAO, keyGenerator = "templateNotificacaoCacheKeyGenerator")
    public TemplateNotificacaoResponse obterPorId(UUID id) {
        log.debug("Buscando template de notificação por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do template de notificação é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        TemplateNotificacao entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateNotificacaoResponse> listar(Pageable pageable, UUID estabelecimentoId, TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal, String nome) {
        log.debug("Listando templates de notificação paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<TemplateNotificacao> page;

        if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByNomeAsc(estabelecimentoId, tenantId, pageable);
        } else if (tipoNotificacao != null) {
            page = repository.findByTipoNotificacaoAndTenantId(tipoNotificacao, tenantId, pageable);
        } else if (canal != null) {
            page = repository.findByCanalAndTenantId(canal, tenantId, pageable);
        } else if (nome != null && !nome.isBlank()) {
            page = repository.findByNomeContainingIgnoreCaseAndTenantId(nome, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_TEMPLATES_NOTIFICACAO, keyGenerator = "templateNotificacaoCacheKeyGenerator")
    public TemplateNotificacaoResponse atualizar(UUID id, TemplateNotificacaoRequest request) {
        log.debug("Atualizando template de notificação. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do template de notificação é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        TemplateNotificacao updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_TEMPLATES_NOTIFICACAO, keyGenerator = "templateNotificacaoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo template de notificação. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do template de notificação é obrigatório");
        }

        TemplateNotificacao entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Template de notificação excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
