package com.upsaude.service.impl;

import com.upsaude.api.request.CuidadosEnfermagemRequest;
import com.upsaude.api.response.CuidadosEnfermagemResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CuidadosEnfermagemRepository;
import com.upsaude.service.CuidadosEnfermagemService;
import com.upsaude.service.TenantService;
import com.upsaude.entity.Tenant;
import com.upsaude.service.support.cuidadosenfermagem.CuidadosEnfermagemCreator;
import com.upsaude.service.support.cuidadosenfermagem.CuidadosEnfermagemResponseBuilder;
import com.upsaude.service.support.cuidadosenfermagem.CuidadosEnfermagemTenantEnforcer;
import com.upsaude.service.support.cuidadosenfermagem.CuidadosEnfermagemUpdater;
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
public class CuidadosEnfermagemServiceImpl implements CuidadosEnfermagemService {

    private final CuidadosEnfermagemRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final CuidadosEnfermagemCreator creator;
    private final CuidadosEnfermagemUpdater updater;
    private final CuidadosEnfermagemResponseBuilder responseBuilder;
    private final CuidadosEnfermagemTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public CuidadosEnfermagemResponse criar(CuidadosEnfermagemRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            CuidadosEnfermagem saved = creator.criar(request, tenantId, tenant);
            CuidadosEnfermagemResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CUIDADOS_ENFERMAGEM);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.cuidadoEnfermagem(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar cuidado de enfermagem", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CUIDADOS_ENFERMAGEM, keyGenerator = "cuidadosEnfermagemCacheKeyGenerator")
    public CuidadosEnfermagemResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do cuidado de enfermagem é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        CuidadosEnfermagem entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CuidadosEnfermagemResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<CuidadosEnfermagem> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CuidadosEnfermagemResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Page<CuidadosEnfermagem> page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CuidadosEnfermagemResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Page<CuidadosEnfermagem> page = repository.findByPacienteIdAndTenantIdOrderByDataHoraDesc(pacienteId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CuidadosEnfermagemResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Page<CuidadosEnfermagem> page = repository.findByProfissionalIdAndTenantIdOrderByDataHoraDesc(profissionalId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CUIDADOS_ENFERMAGEM, keyGenerator = "cuidadosEnfermagemCacheKeyGenerator")
    public CuidadosEnfermagemResponse atualizar(UUID id, CuidadosEnfermagemRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do cuidado de enfermagem é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            CuidadosEnfermagem updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar cuidado de enfermagem", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CUIDADOS_ENFERMAGEM, keyGenerator = "cuidadosEnfermagemCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do cuidado de enfermagem é obrigatório");
        }

        CuidadosEnfermagem entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Cuidado de enfermagem já está inativo");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Cuidado de enfermagem excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
