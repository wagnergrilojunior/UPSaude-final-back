package com.upsaude.service.impl;

import com.upsaude.api.request.saude_publica.planejamento.PreNatalRequest;
import com.upsaude.api.response.saude_publica.planejamento.PreNatalResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.saude_publica.planejamento.PreNatal;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.StatusPreNatalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.planejamento.PreNatalRepository;
import com.upsaude.service.saude_publica.planejamento.PreNatalService;
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

import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.prenatal.PreNatalCreator;
import com.upsaude.service.support.prenatal.PreNatalDomainService;
import com.upsaude.service.support.prenatal.PreNatalResponseBuilder;
import com.upsaude.service.support.prenatal.PreNatalTenantEnforcer;
import com.upsaude.service.support.prenatal.PreNatalUpdater;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreNatalServiceImpl implements PreNatalService {

    private final PreNatalRepository preNatalRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final PreNatalCreator creator;
    private final PreNatalUpdater updater;
    private final PreNatalResponseBuilder responseBuilder;
    private final PreNatalDomainService domainService;
    private final PreNatalTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public PreNatalResponse criar(PreNatalRequest request) {
        log.debug("Criando novo pré-natal");
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            PreNatal saved = creator.criar(request, tenantId, tenant);
            PreNatalResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PRE_NATAL);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.preNatal(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar pré-natal", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PRE_NATAL, keyGenerator = "preNatalCacheKeyGenerator")
    public PreNatalResponse obterPorId(UUID id) {
        log.debug("Buscando pré-natal por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do pré-natal é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        PreNatal preNatal = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(preNatal);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreNatalResponse> listar(Pageable pageable) {
        log.debug("Listando pré-natais paginados");

        UUID tenantId = tenantService.validarTenantAtual();
        Page<PreNatal> preNatais = preNatalRepository.findAllByTenant(tenantId, pageable);
        return preNatais.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreNatalResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando pré-natais por estabelecimento: {}", estabelecimentoId);

        UUID tenantId = tenantService.validarTenantAtual();
        Page<PreNatal> preNatais = preNatalRepository.findByEstabelecimentoIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, tenantId, pageable);
        return preNatais.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreNatalResponse> listarPorPaciente(UUID pacienteId) {
        log.debug("Listando pré-natais por paciente: {}", pacienteId);

        UUID tenantId = tenantService.validarTenantAtual();
        List<PreNatal> preNatais = preNatalRepository.findByPacienteIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(pacienteId, tenantId);
        return preNatais.stream().map(responseBuilder::build).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreNatalResponse> listarEmAcompanhamento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando pré-natais em acompanhamento: {}", estabelecimentoId);

        return listarPorStatus(estabelecimentoId, StatusPreNatalEnum.EM_ACOMPANHAMENTO, pageable);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PRE_NATAL, keyGenerator = "preNatalCacheKeyGenerator")
    public PreNatalResponse atualizar(UUID id, PreNatalRequest request) {
        log.debug("Atualizando pré-natal. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do pré-natal é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        PreNatal updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PRE_NATAL, keyGenerator = "preNatalCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo pré-natal. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreNatalResponse> listarPorStatus(UUID estabelecimentoId, StatusPreNatalEnum status, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<PreNatal> preNatais = preNatalRepository.findByStatusPreNatalAndEstabelecimentoIdAndTenantId(status, estabelecimentoId, tenantId, pageable);
        return preNatais.map(responseBuilder::build);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do pré-natal é obrigatório");
        }

        PreNatal entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        preNatalRepository.save(Objects.requireNonNull(entity));
        log.info("Pré-natal excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
