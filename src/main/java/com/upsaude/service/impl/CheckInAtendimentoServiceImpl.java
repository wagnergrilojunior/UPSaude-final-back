package com.upsaude.service.impl;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.atendimento.CheckInAtendimentoResponse;
import com.upsaude.entity.atendimento.CheckInAtendimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.atendimento.CheckInAtendimentoRepository;
import com.upsaude.service.atendimento.CheckInAtendimentoService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.checkinatendimento.CheckInAtendimentoCreator;
import com.upsaude.service.support.checkinatendimento.CheckInAtendimentoResponseBuilder;
import com.upsaude.service.support.checkinatendimento.CheckInAtendimentoTenantEnforcer;
import com.upsaude.service.support.checkinatendimento.CheckInAtendimentoUpdater;
import com.upsaude.service.support.checkinatendimento.CheckInAtendimentoValidationService;
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

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInAtendimentoServiceImpl implements CheckInAtendimentoService {

    private final CheckInAtendimentoRepository repository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final CheckInAtendimentoValidationService validationService;
    private final CheckInAtendimentoTenantEnforcer tenantEnforcer;
    private final CheckInAtendimentoCreator creator;
    private final CheckInAtendimentoUpdater updater;
    private final CheckInAtendimentoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public CheckInAtendimentoResponse criar(CheckInAtendimentoRequest request) {
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CheckInAtendimento saved = creator.criar(request, tenantId, tenant);
        CheckInAtendimentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CHECKIN_ATENDIMENTO);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.checkInAtendimento(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CHECKIN_ATENDIMENTO, keyGenerator = "checkInAtendimentoCacheKeyGenerator")
    public CheckInAtendimentoResponse obterPorId(UUID id) {
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CheckInAtendimentoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CHECKIN_ATENDIMENTO, keyGenerator = "checkInAtendimentoCacheKeyGenerator")
    public CheckInAtendimentoResponse atualizar(UUID id, CheckInAtendimentoRequest request) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CheckInAtendimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CHECKIN_ATENDIMENTO, keyGenerator = "checkInAtendimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        CheckInAtendimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Check-in já está inativo");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Check-in excluído (desativado) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
