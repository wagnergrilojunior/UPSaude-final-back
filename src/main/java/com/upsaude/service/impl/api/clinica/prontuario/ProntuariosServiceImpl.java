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

import com.upsaude.api.request.clinica.prontuario.ProntuariosRequest;
import com.upsaude.api.response.clinica.prontuario.ProntuariosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.prontuario.ProntuariosRepository;
import com.upsaude.service.api.clinica.prontuario.ProntuariosService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.prontuarios.ProntuariosCreator;
import com.upsaude.service.api.support.prontuarios.ProntuariosDomainService;
import com.upsaude.service.api.support.prontuarios.ProntuariosResponseBuilder;
import com.upsaude.service.api.support.prontuarios.ProntuariosTenantEnforcer;
import com.upsaude.service.api.support.prontuarios.ProntuariosUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuariosServiceImpl implements ProntuariosService {

    private final ProntuariosRepository prontuariosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final ProntuariosCreator creator;
    private final ProntuariosUpdater updater;
    private final ProntuariosResponseBuilder responseBuilder;
    private final ProntuariosDomainService domainService;
    private final ProntuariosTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public ProntuariosResponse criar(ProntuariosRequest request) {
        log.debug("Criando novo prontuarios");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Prontuarios saved = creator.criar(request, tenantId, tenant);
            ProntuariosResponse response = responseBuilder.build(saved);

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
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuariosCacheKeyGenerator")
    public ProntuariosResponse obterPorId(UUID id) {
        log.debug("Buscando prontuarios por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do prontuarios é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Prontuarios entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProntuariosResponse> listar(Pageable pageable) {
        return listar(pageable, null, null, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProntuariosResponse> listar(Pageable pageable, UUID pacienteId, UUID estabelecimentoId, String tipoRegistro, UUID criadoPor) {
        UUID tenantId = tenantService.validarTenantAtual();

        Page<Prontuarios> page;
        if (pacienteId != null) {
            page = prontuariosRepository.findByPacienteIdAndTenantId(pacienteId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = prontuariosRepository.findByEstabelecimentoIdAndTenantId(estabelecimentoId, tenantId, pageable);
        } else if (tipoRegistro != null && !tipoRegistro.isBlank()) {
            page = prontuariosRepository.findByTipoRegistroContainingIgnoreCaseAndTenantId(tipoRegistro, tenantId, pageable);
        } else if (criadoPor != null) {
            page = prontuariosRepository.findByCriadoPorAndTenantId(criadoPor, tenantId, pageable);
        } else {
            page = prontuariosRepository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProntuariosResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        return listar(pageable, null, estabelecimentoId, null, null);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuariosCacheKeyGenerator")
    public ProntuariosResponse atualizar(UUID id, ProntuariosRequest request) {
        log.debug("Atualizando prontuarios. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do prontuarios é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Prontuarios updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuariosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo prontuário permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Prontuarios entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            prontuariosRepository.delete(Objects.requireNonNull(entity));
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
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PRONTUARIOS, keyGenerator = "prontuariosCacheKeyGenerator", beforeInvocation = false)
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

        Prontuarios entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        prontuariosRepository.save(Objects.requireNonNull(entity));
        log.info("Prontuário inativado com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
