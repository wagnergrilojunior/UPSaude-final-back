package com.upsaude.service.impl.api.sistema.auditoria;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.sistema.auditoria.LogsAuditoriaRequest;
import com.upsaude.api.response.sistema.auditoria.LogsAuditoriaResponse;
import com.upsaude.entity.sistema.auditoria.LogsAuditoria;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.sistema.auditoria.LogsAuditoriaRepository;
import com.upsaude.service.api.sistema.auditoria.LogsAuditoriaService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.logsauditoria.LogsAuditoriaCreator;
import com.upsaude.service.api.support.logsauditoria.LogsAuditoriaResponseBuilder;
import com.upsaude.service.api.support.logsauditoria.LogsAuditoriaTenantEnforcer;
import com.upsaude.service.api.support.logsauditoria.LogsAuditoriaUpdater;
import com.upsaude.service.api.support.logsauditoria.LogsAuditoriaValidationService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogsAuditoriaServiceImpl implements LogsAuditoriaService {

    private final LogsAuditoriaRepository logsAuditoriaRepository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final LogsAuditoriaValidationService validationService;
    private final LogsAuditoriaTenantEnforcer tenantEnforcer;
    private final LogsAuditoriaCreator creator;
    private final LogsAuditoriaUpdater updater;
    private final LogsAuditoriaResponseBuilder responseBuilder;

    @Override
    @Transactional
    public LogsAuditoriaResponse criar(LogsAuditoriaRequest request) {
        log.debug("Criando novo LogsAuditoria");
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        LogsAuditoria saved = creator.criar(request, tenantId, tenant);
        LogsAuditoriaResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_LOGS_AUDITORIA);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.logAuditoria(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_LOGS_AUDITORIA, keyGenerator = "logsAuditoriaCacheKeyGenerator")
    public LogsAuditoriaResponse obterPorId(UUID id) {
        log.debug("Buscando LogsAuditoria por ID: {} (cache miss)", id);

        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogsAuditoriaResponse> listar(Pageable pageable) {
        log.debug("Listando LogsAuditorias paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<LogsAuditoria> logsAuditorias = logsAuditoriaRepository.findAllByTenant(tenantId, pageable);
        log.debug("Listagem de LogsAuditorias concluída. Total de elementos: {}", logsAuditorias.getTotalElements());
        return logsAuditorias.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_LOGS_AUDITORIA, keyGenerator = "logsAuditoriaCacheKeyGenerator")
    public LogsAuditoriaResponse atualizar(UUID id, LogsAuditoriaRequest request) {
        log.debug("Atualizando LogsAuditoria. ID: {}", id);

        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        LogsAuditoria updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_LOGS_AUDITORIA, keyGenerator = "logsAuditoriaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo LogsAuditoria. ID: {}", id);

        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        LogsAuditoria entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Log de auditoria já está inativo");
        }

        entity.setActive(false);
        logsAuditoriaRepository.save(entity);
        log.info("Log de auditoria excluído (desativado) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
