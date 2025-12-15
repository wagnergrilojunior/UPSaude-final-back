package com.upsaude.service.impl;

import com.upsaude.api.request.EducacaoSaudeRequest;
import com.upsaude.api.response.EducacaoSaudeResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.EducacaoSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EducacaoSaudeRepository;
import com.upsaude.service.EducacaoSaudeService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.educacaosaude.EducacaoSaudeCreator;
import com.upsaude.service.support.educacaosaude.EducacaoSaudeResponseBuilder;
import com.upsaude.service.support.educacaosaude.EducacaoSaudeTenantEnforcer;
import com.upsaude.service.support.educacaosaude.EducacaoSaudeUpdater;
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
public class EducacaoSaudeServiceImpl implements EducacaoSaudeService {

    private final EducacaoSaudeRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final EducacaoSaudeCreator creator;
    private final EducacaoSaudeUpdater updater;
    private final EducacaoSaudeResponseBuilder responseBuilder;
    private final EducacaoSaudeTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public EducacaoSaudeResponse criar(EducacaoSaudeRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            EducacaoSaude saved = creator.criar(request, tenantId, tenant);
            EducacaoSaudeResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_EDUCACAO_SAUDE);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.educacaoSaude(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar educação em saúde", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_EDUCACAO_SAUDE, keyGenerator = "educacaoSaudeCacheKeyGenerator")
    public EducacaoSaudeResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da educação em saúde é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        EducacaoSaude entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EducacaoSaudeResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<EducacaoSaude> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EducacaoSaudeResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Page<EducacaoSaude> page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EducacaoSaudeResponse> listarPorProfissionalResponsavel(UUID profissionalId, Pageable pageable) {
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Page<EducacaoSaude> page = repository.findByProfissionalResponsavelIdAndTenantIdOrderByDataHoraInicioDesc(profissionalId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EducacaoSaudeResponse> listarRealizadas(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Page<EducacaoSaude> page = repository.findByAtividadeRealizadaAndEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(true, estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_EDUCACAO_SAUDE, keyGenerator = "educacaoSaudeCacheKeyGenerator")
    public EducacaoSaudeResponse atualizar(UUID id, EducacaoSaudeRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da educação em saúde é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            EducacaoSaude updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar educação em saúde", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_EDUCACAO_SAUDE, keyGenerator = "educacaoSaudeCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da educação em saúde é obrigatório");
        }

        EducacaoSaude entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Educação em saúde já está inativa");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Educação em saúde excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
