package com.upsaude.service.impl;
import com.upsaude.entity.sistema.Tenant;

import com.upsaude.api.request.educacao.AcaoPromocaoPrevencaoRequest;
import com.upsaude.api.response.educacao.AcaoPromocaoPrevencaoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.educacao.AcaoPromocaoPrevencao;
import com.upsaude.repository.saude_publica.educacao.AcaoPromocaoPrevencaoRepository;
import com.upsaude.service.educacao.AcaoPromocaoPrevencaoService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.acaopromocaoprevencao.AcaoPromocaoPrevencaoCreator;
import com.upsaude.service.support.acaopromocaoprevencao.AcaoPromocaoPrevencaoDomainService;
import com.upsaude.service.support.acaopromocaoprevencao.AcaoPromocaoPrevencaoResponseBuilder;
import com.upsaude.service.support.acaopromocaoprevencao.AcaoPromocaoPrevencaoTenantEnforcer;
import com.upsaude.service.support.acaopromocaoprevencao.AcaoPromocaoPrevencaoUpdater;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcaoPromocaoPrevencaoServiceImpl implements AcaoPromocaoPrevencaoService {

    private final AcaoPromocaoPrevencaoRepository acaoPromocaoPrevencaoRepository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final AcaoPromocaoPrevencaoCreator creator;
    private final AcaoPromocaoPrevencaoUpdater updater;
    private final AcaoPromocaoPrevencaoTenantEnforcer tenantEnforcer;
    private final AcaoPromocaoPrevencaoDomainService domainService;
    private final AcaoPromocaoPrevencaoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public AcaoPromocaoPrevencaoResponse criar(AcaoPromocaoPrevencaoRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            AcaoPromocaoPrevencao acao = creator.criar(request, tenantId);
            AcaoPromocaoPrevencaoResponse response = responseBuilder.build(acao);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_ACOES_PROMOCAO_PREVENCAO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.acaoPromocaoPrevencao(tenantId, acao.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de persistência ao criar ação promoção/prevenção.", e);
            throw new InternalServerErrorException("Erro ao criar ação de promoção e prevenção");
        } catch (Exception e) {
            log.error("Erro inesperado ao criar ação promoção/prevenção.", e);
            throw e;
        }
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ACOES_PROMOCAO_PREVENCAO, keyGenerator = "acaoPromocaoPrevencaoCacheKeyGenerator")
    public AcaoPromocaoPrevencaoResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (id == null) throw new BadRequestException("ID da ação de promoção e prevenção é obrigatório");

        AcaoPromocaoPrevencao acao = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(acao);
    }

    @Override
    @Transactional
    public Page<AcaoPromocaoPrevencaoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return acaoPromocaoPrevencaoRepository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public Page<AcaoPromocaoPrevencaoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return acaoPromocaoPrevencaoRepository.findByEstabelecimentoIdAndTenantIdOrderByDataInicioDesc(estabelecimentoId, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional
    public Page<AcaoPromocaoPrevencaoResponse> listarPorProfissionalResponsavel(UUID profissionalId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return acaoPromocaoPrevencaoRepository.findByProfissionalResponsavelIdAndTenantIdOrderByDataInicioDesc(profissionalId, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional
    public Page<AcaoPromocaoPrevencaoResponse> listarPorStatus(String status, UUID estabelecimentoId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return acaoPromocaoPrevencaoRepository
                .findByStatusAcaoAndEstabelecimentoIdAndTenantIdOrderByDataInicioDesc(status, estabelecimentoId, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional
    public Page<AcaoPromocaoPrevencaoResponse> listarContinuas(UUID estabelecimentoId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return acaoPromocaoPrevencaoRepository
                .findByAcaoContinuaAndEstabelecimentoIdAndTenantIdOrderByDataInicioDesc(true, estabelecimentoId, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ACOES_PROMOCAO_PREVENCAO, keyGenerator = "acaoPromocaoPrevencaoCacheKeyGenerator")
    public AcaoPromocaoPrevencaoResponse atualizar(UUID id, AcaoPromocaoPrevencaoRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            if (id == null) throw new BadRequestException("ID da ação de promoção e prevenção é obrigatório");

            AcaoPromocaoPrevencao atualizado = updater.atualizar(id, request, tenantId);
            return responseBuilder.build(atualizado);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de persistência ao atualizar ação promoção/prevenção. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao atualizar ação de promoção e prevenção");
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar ação promoção/prevenção. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ACOES_PROMOCAO_PREVENCAO, keyGenerator = "acaoPromocaoPrevencaoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de persistência ao excluir(inativar) ação promoção/prevenção. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir ação de promoção e prevenção");
        } catch (Exception e) {
            log.error("Erro inesperado ao excluir(inativar) ação promoção/prevenção. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) throw new BadRequestException("ID da ação de promoção e prevenção é obrigatório");

        AcaoPromocaoPrevencao acao = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(acao);

        acao.setActive(false);
        acaoPromocaoPrevencaoRepository.save(Objects.requireNonNull(acao));
        log.info("Ação de promoção e prevenção inativada com sucesso. ID: {}, Tenant: {}", id, tenantId);
    }
}
