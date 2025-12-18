package com.upsaude.service.impl;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.medicao.MedicaoClinicaRequest;
import com.upsaude.api.response.medicao.MedicaoClinicaResponse;
import com.upsaude.entity.medicao.MedicaoClinica;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.profissional.medicao.MedicaoClinicaRepository;
import com.upsaude.service.medicao.MedicaoClinicaService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaCreator;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaResponseBuilder;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaTenantEnforcer;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaUpdater;
import com.upsaude.service.support.medicaoclinica.MedicaoClinicaValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
public class MedicaoClinicaServiceImpl implements MedicaoClinicaService {

    private final MedicaoClinicaRepository medicaoClinicaRepository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final MedicaoClinicaValidationService validationService;
    private final MedicaoClinicaTenantEnforcer tenantEnforcer;
    private final MedicaoClinicaCreator creator;
    private final MedicaoClinicaUpdater updater;
    private final MedicaoClinicaResponseBuilder responseBuilder;

    @Override
    @Transactional
    public MedicaoClinicaResponse criar(MedicaoClinicaRequest request) {
        log.debug("Criando nova medição clínica. Request: {}", request);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        MedicaoClinica saved = creator.criar(request, tenantId, tenant);
        MedicaoClinicaResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_MEDICAO_CLINICA);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.medicaoClinica(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_MEDICAO_CLINICA, keyGenerator = "medicaoClinicaCacheKeyGenerator")
    public MedicaoClinicaResponse obterPorId(UUID id) {
        log.debug("Buscando medição clínica por ID: {} (cache miss)", id);
        validationService.validarId(id);

        UUID tenantId = tenantService.validarTenantAtual();
        MedicaoClinica medicaoClinica = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(medicaoClinica);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicaoClinicaResponse> listar(Pageable pageable) {
        log.debug("Listando medições clínicas paginadas. Pageable: {}", pageable);
        UUID tenantId = tenantService.validarTenantAtual();
        return medicaoClinicaRepository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicaoClinicaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando medições clínicas do paciente: {}. Pageable: {}", pacienteId, pageable);
        validationService.validarPacienteId(pacienteId);
        UUID tenantId = tenantService.validarTenantAtual();
        return medicaoClinicaRepository.findByPacienteIdAndTenantIdOrderByDataHoraDesc(pacienteId, tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_MEDICAO_CLINICA, keyGenerator = "medicaoClinicaCacheKeyGenerator")
    public MedicaoClinicaResponse atualizar(UUID id, MedicaoClinicaRequest request) {
        log.debug("Atualizando medição clínica. ID: {}", id);

        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        MedicaoClinica updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICAO_CLINICA, keyGenerator = "medicaoClinicaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo medição clínica. ID: {}", id);

        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        MedicaoClinica entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Medição clínica já está inativa");
        }

        entity.setActive(false);
        medicaoClinicaRepository.save(entity);
        log.info("Medição clínica excluída (desativada) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
