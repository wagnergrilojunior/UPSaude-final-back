package com.upsaude.service.impl;

import com.upsaude.api.request.AlergiasRequest;
import com.upsaude.api.response.AlergiasResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Alergias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.AlergiasRepository;
import com.upsaude.service.AlergiasService;
import com.upsaude.service.support.alergias.AlergiasCreator;
import com.upsaude.service.support.alergias.AlergiasDomainService;
import com.upsaude.service.support.alergias.AlergiasResponseBuilder;
import com.upsaude.service.support.alergias.AlergiasUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiasServiceImpl implements AlergiasService {

    private final AlergiasRepository alergiasRepository;
    private final CacheManager cacheManager;

    private final AlergiasCreator creator;
    private final AlergiasUpdater updater;
    private final AlergiasDomainService domainService;
    private final AlergiasResponseBuilder responseBuilder;

    @Override
    @Transactional
    public AlergiasResponse criar(AlergiasRequest request) {
        try {
            Alergias alergia = creator.criar(request);
            AlergiasResponse response = responseBuilder.build(alergia);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_ALERGIAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.alergia(alergia.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar alergia. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao criar Alergia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar alergia. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ALERGIAS, keyGenerator = "alergiasCacheKeyGenerator")
    public AlergiasResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da alergia é obrigatório");
        }

        Alergias alergia = alergiasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + id));

        return responseBuilder.build(alergia);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlergiasResponse> listar(Pageable pageable) {
        try {
            return alergiasRepository.findAll(Objects.requireNonNull(pageable, "pageable")).map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar alergias.", e);
            throw new InternalServerErrorException("Erro ao listar Alergias", e);
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ALERGIAS, keyGenerator = "alergiasCacheKeyGenerator")
    public AlergiasResponse atualizar(UUID id, AlergiasRequest request) {
        try {
            if (id == null) {
                throw new BadRequestException("ID da alergia é obrigatório");
            }
            if (request == null) {
                throw new BadRequestException("Dados da alergia são obrigatórios");
            }

            Alergias atualizado = updater.atualizar(id, request);
            return responseBuilder.build(atualizado);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar alergia. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao atualizar Alergia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar alergia. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ALERGIAS, keyGenerator = "alergiasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            inativarInternal(id);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir(inativar) alergia. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir Alergia", e);
        }
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da alergia é obrigatório");
        }

        Alergias existente = alergiasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + id));

        domainService.validarPodeInativar(existente);

        existente.setActive(false);
        alergiasRepository.save(Objects.requireNonNull(existente));
        log.info("Alergia inativada. ID: {}", id);
    }
}
