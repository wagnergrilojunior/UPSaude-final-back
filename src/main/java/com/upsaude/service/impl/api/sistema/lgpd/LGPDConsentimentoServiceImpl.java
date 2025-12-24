package com.upsaude.service.impl.api.sistema.lgpd;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.sistema.lgpd.LGPDConsentimentoRequest;
import com.upsaude.api.response.sistema.lgpd.LGPDConsentimentoResponse;
import com.upsaude.entity.sistema.lgpd.LGPDConsentimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.lgpd.LGPDConsentimentoRepository;
import com.upsaude.service.api.sistema.lgpd.LGPDConsentimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.lgpdconsentimento.LGPDConsentimentoCreator;
import com.upsaude.service.api.support.lgpdconsentimento.LGPDConsentimentoResponseBuilder;
import com.upsaude.service.api.support.lgpdconsentimento.LGPDConsentimentoTenantEnforcer;
import com.upsaude.service.api.support.lgpdconsentimento.LGPDConsentimentoUpdater;
import com.upsaude.service.api.support.lgpdconsentimento.LGPDConsentimentoValidationService;
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
public class LGPDConsentimentoServiceImpl implements LGPDConsentimentoService {

    private final LGPDConsentimentoRepository repository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final LGPDConsentimentoValidationService validationService;
    private final LGPDConsentimentoTenantEnforcer tenantEnforcer;
    private final LGPDConsentimentoCreator creator;
    private final LGPDConsentimentoUpdater updater;
    private final LGPDConsentimentoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public LGPDConsentimentoResponse criar(LGPDConsentimentoRequest request) {
        log.debug("Criando consentimento LGPD. Request: {}", request);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        LGPDConsentimento saved = creator.criar(request, tenantId, tenant);
        LGPDConsentimentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_LGPD_CONSENTIMENTO);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.lgpdConsentimento(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_LGPD_CONSENTIMENTO, keyGenerator = "lgpdConsentimentoCacheKeyGenerator")
    public LGPDConsentimentoResponse obterPorId(UUID id) {
        log.debug("Buscando consentimento LGPD por ID: {} (cache miss)", id);
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public LGPDConsentimentoResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando consentimento LGPD por paciente ID: {}", pacienteId);

        validationService.validarPacienteId(pacienteId);
        UUID tenantId = tenantService.validarTenantAtual();

        LGPDConsentimento entity = repository.findByPacienteIdAndTenantId(pacienteId, tenantId)
            .orElseThrow(() -> new NotFoundException("Consentimento LGPD não encontrado para o paciente: " + pacienteId));

        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LGPDConsentimentoResponse> listar(Pageable pageable) {
        log.debug("Listando consentimentos LGPD paginados. Pageable: {}", pageable);
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_LGPD_CONSENTIMENTO, keyGenerator = "lgpdConsentimentoCacheKeyGenerator")
    public LGPDConsentimentoResponse atualizar(UUID id, LGPDConsentimentoRequest request) {
        log.debug("Atualizando consentimento LGPD. ID: {}", id);

        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        LGPDConsentimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_LGPD_CONSENTIMENTO, keyGenerator = "lgpdConsentimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo consentimento LGPD. ID: {}", id);

        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        LGPDConsentimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Consentimento LGPD já está inativo");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Consentimento LGPD excluído (desativado) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
