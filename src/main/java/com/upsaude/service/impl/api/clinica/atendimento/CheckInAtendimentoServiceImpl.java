package com.upsaude.service.impl.api.clinica.atendimento;

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

import com.upsaude.api.request.clinica.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.CheckInAtendimentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.clinica.atendimento.CheckInAtendimentoRepository;
import com.upsaude.service.api.clinica.atendimento.CheckInAtendimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.checkinatendimento.CheckInAtendimentoCreator;
import com.upsaude.service.api.support.checkinatendimento.CheckInAtendimentoDomainService;
import com.upsaude.service.api.support.checkinatendimento.CheckInAtendimentoResponseBuilder;
import com.upsaude.service.api.support.checkinatendimento.CheckInAtendimentoTenantEnforcer;
import com.upsaude.service.api.support.checkinatendimento.CheckInAtendimentoUpdater;
import com.upsaude.service.api.support.checkinatendimento.CheckInAtendimentoValidationService;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final CheckInAtendimentoDomainService domainService;

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
        log.debug("Excluindo check-in permanentemente. ID: {}", id);
        validationService.validarId(id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            CheckInAtendimento entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Check-in excluído permanentemente com sucesso. ID: {}, tenant: {}", id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir CheckInAtendimento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir CheckInAtendimento. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir CheckInAtendimento", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CHECKIN_ATENDIMENTO, keyGenerator = "checkInAtendimentoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando check-in. ID: {}", id);
        validationService.validarId(id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar CheckInAtendimento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar CheckInAtendimento. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar CheckInAtendimento", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        CheckInAtendimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(entity);
        log.info("Check-in inativado com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
