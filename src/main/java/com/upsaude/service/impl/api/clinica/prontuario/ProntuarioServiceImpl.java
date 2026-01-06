package com.upsaude.service.impl.api.clinica.prontuario;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.clinica.prontuario.ProntuarioRequest;
import com.upsaude.api.response.clinica.prontuario.ProntuarioResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.prontuario.ProntuarioRepository;
import com.upsaude.service.api.clinica.prontuario.ProntuarioService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.prontuarios.ProntuarioCreator;
import com.upsaude.service.api.support.prontuarios.ProntuarioDomainService;
import com.upsaude.service.api.support.prontuarios.ProntuarioResponseBuilder;
import com.upsaude.service.api.support.prontuarios.ProntuarioTenantEnforcer;
import com.upsaude.service.api.support.prontuarios.ProntuarioUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuarioServiceImpl implements ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ProntuarioCreator creator;
    private final ProntuarioUpdater updater;
    private final ProntuarioResponseBuilder responseBuilder;
    private final ProntuarioDomainService domainService;
    private final ProntuarioTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ProntuarioResponse criar(ProntuarioRequest request) {
        log.debug("Criando novo prontuário");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Prontuario saved = creator.criar(request, tenantId, tenant);
            ProntuarioResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PRONTUARIOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.prontuario(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar prontuário", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator")
    public ProntuarioResponse obterPorId(UUID id) {
        log.debug("Buscando prontuário por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do prontuário é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Prontuario entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProntuarioResponse> listar(Pageable pageable) {
        return listar(pageable, null, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProntuarioResponse> listar(Pageable pageable, UUID pacienteId, UUID estabelecimentoId, UUID criadoPor) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<Prontuario> page;
        if (pacienteId != null) {
            page = prontuarioRepository.findByPacienteIdAndTenantId(pacienteId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = prontuarioRepository.findByEstabelecimentoIdAndTenantId(estabelecimentoId, tenantId, pageable);
        } else if (criadoPor != null) {
            page = prontuarioRepository.findByCriadoPorAndTenantId(criadoPor, tenantId, pageable);
        } else {
            page = prontuarioRepository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProntuarioResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        return listar(pageable, null, estabelecimentoId, null);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator")
    public ProntuarioResponse atualizar(UUID id, ProntuarioRequest request) {
        log.debug("Atualizando prontuário. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do prontuário é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Prontuario updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo prontuário permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Prontuario entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            prontuarioRepository.delete(Objects.requireNonNull(entity));
            log.info("Prontuário excluído permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir Prontuário. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Prontuário. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Prontuário", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuarioCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando prontuário. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar Prontuário. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Prontuário. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Prontuário", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do prontuário é obrigatório");
        }

        Prontuario entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        prontuarioRepository.save(Objects.requireNonNull(entity));
        log.info("Prontuário inativado com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

