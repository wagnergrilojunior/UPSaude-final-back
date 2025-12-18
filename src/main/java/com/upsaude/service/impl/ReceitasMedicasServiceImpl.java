package com.upsaude.service.impl;

import com.upsaude.api.request.clinica.medicacao.ReceitasMedicasRequest;
import com.upsaude.api.response.clinica.medicacao.ReceitasMedicasResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.medicacao.ReceitasMedicas;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.medicacao.ReceitasMedicasRepository;
import com.upsaude.service.clinica.medicacao.ReceitasMedicasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.enums.StatusReceitaEnum;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.receitasmedicas.ReceitasMedicasCreator;
import com.upsaude.service.support.receitasmedicas.ReceitasMedicasDomainService;
import com.upsaude.service.support.receitasmedicas.ReceitasMedicasResponseBuilder;
import com.upsaude.service.support.receitasmedicas.ReceitasMedicasTenantEnforcer;
import com.upsaude.service.support.receitasmedicas.ReceitasMedicasUpdater;
import com.upsaude.service.support.receitasmedicas.ReceitasMedicasValidationService;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceitasMedicasServiceImpl implements ReceitasMedicasService {

    private final ReceitasMedicasRepository receitasMedicasRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ReceitasMedicasCreator creator;
    private final ReceitasMedicasUpdater updater;
    private final ReceitasMedicasResponseBuilder responseBuilder;
    private final ReceitasMedicasDomainService domainService;
    private final ReceitasMedicasTenantEnforcer tenantEnforcer;
    private final ReceitasMedicasValidationService validationService;

    @Override
    @Transactional
    public ReceitasMedicasResponse criar(ReceitasMedicasRequest request) {
        log.debug("Criando novo receitasmedicas");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            ReceitasMedicas saved = creator.criar(request, tenantId, tenant);
            ReceitasMedicasResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_RECEITAS_MEDICAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.receitaMedica(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar receita médica", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_RECEITAS_MEDICAS, keyGenerator = "receitasMedicasCacheKeyGenerator")
    public ReceitasMedicasResponse obterPorId(UUID id) {
        log.debug("Buscando receitasmedicas por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        ReceitasMedicas entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReceitasMedicasResponse> listar(Pageable pageable) {
        return listar(pageable, null, null, null, null, null, null, null, null, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReceitasMedicasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        return listar(pageable, estabelecimentoId, null, null, null, null, null, null, null, null, null);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_RECEITAS_MEDICAS, keyGenerator = "receitasMedicasCacheKeyGenerator")
    public ReceitasMedicasResponse atualizar(UUID id, ReceitasMedicasRequest request) {
        log.debug("Atualizando receitasmedicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        ReceitasMedicas updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_RECEITAS_MEDICAS, keyGenerator = "receitasMedicasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo receitasmedicas. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReceitasMedicasResponse> listar(Pageable pageable,
        UUID estabelecimentoId,
        UUID pacienteId,
        UUID medicoId,
        StatusReceitaEnum status,
        OffsetDateTime inicio,
        OffsetDateTime fim,
        String numeroReceita,
        Boolean usoContinuo,
        String origemReceita,
        UUID cidPrincipalId) {

        UUID tenantId = tenantService.validarTenantAtual();

        Page<ReceitasMedicas> page;
        if (estabelecimentoId != null) {
            page = receitasMedicasRepository.findByEstabelecimentoIdAndTenantIdOrderByDataPrescricaoDesc(estabelecimentoId, tenantId, pageable);
        } else if (pacienteId != null) {
            page = receitasMedicasRepository.findByPacienteIdAndTenantIdOrderByDataPrescricaoDesc(pacienteId, tenantId, pageable);
        } else if (medicoId != null) {
            page = receitasMedicasRepository.findByMedicoIdAndTenantIdOrderByDataPrescricaoDesc(medicoId, tenantId, pageable);
        } else if (status != null) {
            page = receitasMedicasRepository.findByStatusAndTenantIdOrderByDataPrescricaoDesc(status, tenantId, pageable);
        } else if (inicio != null || fim != null) {
            validationService.validarPeriodo(inicio, fim);
            page = receitasMedicasRepository.findByDataPrescricaoBetweenAndTenantIdOrderByDataPrescricaoDesc(inicio, fim, tenantId, pageable);
        } else if (numeroReceita != null && !numeroReceita.isBlank()) {
            page = receitasMedicasRepository.findByNumeroReceitaContainingIgnoreCaseAndTenantId(numeroReceita, tenantId, pageable);
        } else if (usoContinuo != null) {
            page = receitasMedicasRepository.findByUsoContinuoAndTenantId(usoContinuo, tenantId, pageable);
        } else if (origemReceita != null && !origemReceita.isBlank()) {
            page = receitasMedicasRepository.findByOrigemReceitaContainingIgnoreCaseAndTenantId(origemReceita, tenantId, pageable);
        } else if (cidPrincipalId != null) {
            page = receitasMedicasRepository.findByCidPrincipalIdAndTenantId(cidPrincipalId, tenantId, pageable);
        } else {
            page = receitasMedicasRepository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        ReceitasMedicas entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        receitasMedicasRepository.save(Objects.requireNonNull(entity));
        log.info("ReceitasMedicas excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
