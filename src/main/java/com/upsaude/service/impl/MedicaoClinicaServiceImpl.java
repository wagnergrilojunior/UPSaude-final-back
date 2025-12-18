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

import com.upsaude.api.request.profissional.medicao.MedicaoClinicaRequest;
import com.upsaude.api.response.profissional.medicao.MedicaoClinicaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.medicao.MedicaoClinica;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.profissional.medicao.MedicaoClinicaRepository;
import com.upsaude.service.profissional.medicao.MedicaoClinicaService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaCreator;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaResponseBuilder;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaTenantEnforcer;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicaoClinicaServiceImpl implements MedicaoClinicaService {

    private final MedicaoClinicaRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final MedicaoClinicaCreator creator;
    private final MedicaoClinicaUpdater updater;
    private final MedicaoClinicaResponseBuilder responseBuilder;
    private final MedicaoClinicaTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public MedicaoClinicaResponse criar(MedicaoClinicaRequest request) {
        log.debug("Criando nova medição clínica");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            MedicaoClinica saved = creator.criar(request, tenantId, tenant);
            MedicaoClinicaResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_MEDICAO_CLINICA);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.medicaoClinica(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar medição clínica", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_MEDICAO_CLINICA, keyGenerator = "medicaoClinicaCacheKeyGenerator")
    public MedicaoClinicaResponse obterPorId(UUID id) {
        log.debug("Buscando medição clínica por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da medição clínica é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        MedicaoClinica entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicaoClinicaResponse> listar(Pageable pageable) {
        log.debug("Listando medições clínicas paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<MedicaoClinica> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicaoClinicaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<MedicaoClinica> page = repository.findByPacienteIdAndTenantIdOrderByDataHoraDesc(pacienteId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_MEDICAO_CLINICA, keyGenerator = "medicaoClinicaCacheKeyGenerator")
    public MedicaoClinicaResponse atualizar(UUID id, MedicaoClinicaRequest request) {
        log.debug("Atualizando medição clínica. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da medição clínica é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        MedicaoClinica updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICAO_CLINICA, keyGenerator = "medicaoClinicaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo medição clínica. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da medição clínica é obrigatório");
        }

        MedicaoClinica entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Medição clínica excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
