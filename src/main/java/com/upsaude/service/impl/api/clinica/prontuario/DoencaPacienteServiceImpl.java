package com.upsaude.service.impl.api.clinica.prontuario;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.clinica.prontuario.DoencaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.DoencaPacienteResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.prontuario.DoencaPaciente;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.clinica.prontuario.DoencaPacienteMapper;
import com.upsaude.repository.clinica.prontuario.DoencaPacienteRepository;
import com.upsaude.repository.clinica.prontuario.ProntuarioRepository;
import com.upsaude.service.api.clinica.prontuario.DoencaPacienteService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoencaPacienteServiceImpl implements DoencaPacienteService {

    private final DoencaPacienteRepository repository;
    private final ProntuarioRepository prontuarioRepository;
    private final TenantService tenantService;
    private final DoencaPacienteMapper mapper;
    private final CacheManager cacheManager;

    @Override
    @Transactional
    public DoencaPacienteResponse criar(DoencaPacienteRequest request) {
        log.debug("Criando novo diagnóstico do paciente");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

            DoencaPaciente entity = mapper.fromRequest(request);
            entity.setActive(true);
            entity.setTenant(tenant);

            Prontuario prontuario = prontuarioRepository.findByIdAndTenant(request.getProntuario(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Prontuário não encontrado"));
            entity.setProntuario(prontuario);

            DoencaPaciente saved = repository.save(entity);

            evictCache(tenantId, prontuario.getId());

            log.info("Diagnóstico do paciente criado com sucesso. ID: {}", saved.getId());
            return mapper.toResponse(saved);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao criar diagnóstico do paciente", e);
            throw new InternalServerErrorException("Erro ao criar diagnóstico do paciente", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator")
    public DoencaPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando diagnóstico do paciente por ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        DoencaPaciente entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Diagnóstico do paciente não encontrado"));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoencaPacienteResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoencaPacienteResponse> listarPorProntuario(UUID prontuarioId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findByProntuarioIdAndTenantId(prontuarioId, tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator")
    public DoencaPacienteResponse atualizar(UUID id, DoencaPacienteRequest request) {
        log.debug("Atualizando diagnóstico do paciente. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            DoencaPaciente entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Diagnóstico do paciente não encontrado"));

            mapper.updateFromRequest(request, entity);

            DoencaPaciente saved = repository.save(entity);

            if (entity.getProntuario() != null) {
                evictCache(tenantId, (java.util.UUID) entity.getProntuario().getId());
            }

            log.info("Diagnóstico do paciente atualizado com sucesso. ID: {}", id);
            return mapper.toResponse(saved);
        } catch (Exception e) {
            log.error("Erro ao atualizar diagnóstico do paciente. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao atualizar diagnóstico do paciente", e);
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo diagnóstico do paciente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            DoencaPaciente entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Diagnóstico do paciente não encontrado"));

            UUID prontuarioId = entity.getProntuario() != null ? entity.getProntuario().getId() : null;
            repository.delete(entity);

            if (prontuarioId != null) {
                evictCache(tenantId, prontuarioId);
            }
            log.info("Diagnóstico do paciente excluído com sucesso. ID: {}", id);
        } catch (Exception e) {
            log.error("Erro ao excluir diagnóstico do paciente. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir diagnóstico do paciente", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        log.debug("Inativando diagnóstico do paciente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            DoencaPaciente entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Diagnóstico do paciente não encontrado"));

            entity.setActive(false);
            repository.save(entity);

            if (entity.getProntuario() != null) {
                evictCache(tenantId, entity.getProntuario().getId());
            }
            log.info("Diagnóstico do paciente inativado com sucesso. ID: {}", id);
        } catch (Exception e) {
            log.error("Erro ao inativar diagnóstico do paciente. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao inativar diagnóstico do paciente", e);
        }
    }

    private void evictCache(UUID tenantId, UUID prontuarioId) {
        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PRONTUARIOS);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.prontuario(tenantId, prontuarioId));
            cache.evict(key);
        }
    }
}
