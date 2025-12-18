package com.upsaude.service.impl;

import java.util.UUID;
import java.util.Objects;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.service.profissional.MedicosService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.medico.MedicoCreator;
import com.upsaude.service.support.medico.MedicoDomainService;
import com.upsaude.service.support.medico.MedicoResponseBuilder;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.medico.MedicoUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicosServiceImpl implements MedicosService {

    private final MedicosRepository medicosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final MedicoCreator medicoCreator;
    private final MedicoUpdater medicoUpdater;
    private final MedicoTenantEnforcer tenantEnforcer;
    private final MedicoResponseBuilder responseBuilder;
    private final MedicoDomainService domainService;

    @Override
    @Transactional
    public MedicosResponse criar(MedicosRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            Medicos medico = medicoCreator.criar(request, tenantId, tenant);
            MedicosResponse response = responseBuilder.build(medico);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_MEDICOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.medico(tenantId, medico.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir Medico", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator")
    public MedicosResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Medicos medico = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(medico);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicosResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Medicos> medicos = medicosRepository.findAllByTenant(tenantId, pageable);
        return medicos.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator")
    public MedicosResponse atualizar(UUID id, MedicosRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            Medicos medico = medicoUpdater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(medico);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar Medico", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Medico", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Medico", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator", beforeInvocation = false)
    public void deletarPermanentemente(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Medicos medico = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(medico);
            medicosRepository.delete(Objects.requireNonNull(medico));
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao deletar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao deletar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao deletar Medico", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        Medicos medico = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(medico);
        medico.setActive(false);
        medicosRepository.save(Objects.requireNonNull(medico));
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
