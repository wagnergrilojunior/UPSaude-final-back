package com.upsaude.service.impl;

import com.upsaude.api.request.paciente.ResponsavelLegalRequest;
import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.paciente.ResponsavelLegalRepository;
import com.upsaude.service.paciente.ResponsavelLegalService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.responsavellegal.ResponsavelLegalCreator;
import com.upsaude.service.support.responsavellegal.ResponsavelLegalDomainService;
import com.upsaude.service.support.responsavellegal.ResponsavelLegalResponseBuilder;
import com.upsaude.service.support.responsavellegal.ResponsavelLegalTenantEnforcer;
import com.upsaude.service.support.responsavellegal.ResponsavelLegalUpdater;
import com.upsaude.service.support.responsavellegal.ResponsavelLegalValidationService;
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
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponsavelLegalServiceImpl implements ResponsavelLegalService {

    private final ResponsavelLegalRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ResponsavelLegalCreator creator;
    private final ResponsavelLegalUpdater updater;
    private final ResponsavelLegalResponseBuilder responseBuilder;
    private final ResponsavelLegalDomainService domainService;
    private final ResponsavelLegalTenantEnforcer tenantEnforcer;
    private final ResponsavelLegalValidationService validationService;

    @Override
    @Transactional
    public ResponsavelLegalResponse criar(ResponsavelLegalRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ResponsavelLegal saved = creator.criar(request, tenantId, tenant);
        ResponsavelLegalResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_RESPONSAVEIS_LEGAIS);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.responsavelLegal(tenantId, saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_RESPONSAVEIS_LEGAIS, keyGenerator = "responsavelLegalCacheKeyGenerator")
    public ResponsavelLegalResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do responsável legal é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsavelLegalResponse obterPorPacienteId(UUID pacienteId) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        ResponsavelLegal entity = repository.findByPacienteIdAndTenantId(pacienteId, tenantId)
            .orElseThrow(() -> new com.upsaude.exception.NotFoundException("Responsável legal não encontrado para o paciente: " + pacienteId));
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelLegalResponse> listar(Pageable pageable, UUID estabelecimentoId, String cpf, String nome) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<ResponsavelLegal> page;
        if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantId(estabelecimentoId, tenantId, pageable);
        } else if (StringUtils.hasText(cpf)) {
            String cpfLimpo = validationService.normalizarCpf(cpf);
            page = repository.findByCpfContainingIgnoreCaseAndTenantId(cpfLimpo, tenantId, pageable);
        } else if (StringUtils.hasText(nome)) {
            page = repository.findByNomeContainingIgnoreCaseAndTenantId(nome, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_RESPONSAVEIS_LEGAIS, keyGenerator = "responsavelLegalCacheKeyGenerator")
    public ResponsavelLegalResponse atualizar(UUID id, ResponsavelLegalRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do responsável legal é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        return responseBuilder.build(updater.atualizar(id, request, tenantId, tenant));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_RESPONSAVEIS_LEGAIS, keyGenerator = "responsavelLegalCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do responsável legal é obrigatório");
        }

        ResponsavelLegal entity = tenantEnforcer.validarAcesso(id, tenantId);
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
