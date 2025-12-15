package com.upsaude.service.impl;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.api.response.PerfisUsuariosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.PerfisUsuariosRepository;
import com.upsaude.service.PerfisUsuariosService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.perfisusuarios.PerfisUsuariosCreator;
import com.upsaude.service.support.perfisusuarios.PerfisUsuariosDomainService;
import com.upsaude.service.support.perfisusuarios.PerfisUsuariosResponseBuilder;
import com.upsaude.service.support.perfisusuarios.PerfisUsuariosTenantEnforcer;
import com.upsaude.service.support.perfisusuarios.PerfisUsuariosUpdater;
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
public class PerfisUsuariosServiceImpl implements PerfisUsuariosService {

    private final PerfisUsuariosRepository perfisUsuariosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final PerfisUsuariosCreator creator;
    private final PerfisUsuariosUpdater updater;
    private final PerfisUsuariosResponseBuilder responseBuilder;
    private final PerfisUsuariosDomainService domainService;
    private final PerfisUsuariosTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public PerfisUsuariosResponse criar(PerfisUsuariosRequest request) {
        log.debug("Criando novo perfisusuarios");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            PerfisUsuarios saved = creator.criar(request, tenantId, tenant);
            PerfisUsuariosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PERFIS_USUARIOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.perfilUsuario(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar perfil de usuário", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PERFIS_USUARIOS, keyGenerator = "perfisUsuariosCacheKeyGenerator")
    public PerfisUsuariosResponse obterPorId(UUID id) {
        log.debug("Buscando perfisusuarios por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do perfisusuarios é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        PerfisUsuarios perfisUsuarios = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(perfisUsuarios);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerfisUsuariosResponse> listar(Pageable pageable) {
        return listar(pageable, null, null);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PERFIS_USUARIOS, keyGenerator = "perfisUsuariosCacheKeyGenerator")
    public PerfisUsuariosResponse atualizar(UUID id, PerfisUsuariosRequest request) {
        log.debug("Atualizando perfisusuarios. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do perfisusuarios é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        PerfisUsuarios updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PERFIS_USUARIOS, keyGenerator = "perfisUsuariosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo perfisusuarios. ID: {}", id);

        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerfisUsuariosResponse> listar(Pageable pageable, UUID usuarioId, UUID estabelecimentoId) {
        log.debug("Listando PerfisUsuarios. Página: {}, Tamanho: {}, usuarioId: {}, estabelecimentoId: {}",
            pageable.getPageNumber(), pageable.getPageSize(), usuarioId, estabelecimentoId);

        UUID tenantId = tenantService.validarTenantAtual();

        Page<PerfisUsuarios> page;
        if (usuarioId != null) {
            page = perfisUsuariosRepository.findByUsuarioIdAndTenantId(usuarioId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = perfisUsuariosRepository.findByEstabelecimentoIdAndTenantId(estabelecimentoId, tenantId, pageable);
        } else {
            page = perfisUsuariosRepository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do perfisusuarios é obrigatório");
        }

        PerfisUsuarios entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        perfisUsuariosRepository.save(Objects.requireNonNull(entity));
        log.info("PerfisUsuarios excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
