package com.upsaude.service.impl;

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

import com.upsaude.api.request.saude_publica.vacina.VacinasRequest;
import com.upsaude.api.response.saude_publica.vacina.VacinasResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.saude_publica.vacina.Vacinas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.vacina.VacinasRepository;
import com.upsaude.service.saude_publica.vacina.VacinasService;
import com.upsaude.service.support.vacinas.VacinasCreator;
import com.upsaude.service.support.vacinas.VacinasDomainService;
import com.upsaude.service.support.vacinas.VacinasResponseBuilder;
import com.upsaude.service.support.vacinas.VacinasUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacinasServiceImpl implements VacinasService {

    private final VacinasRepository vacinasRepository;
    private final CacheManager cacheManager;

    private final VacinasCreator creator;
    private final VacinasUpdater updater;
    private final VacinasDomainService domainService;
    private final VacinasResponseBuilder responseBuilder;

    @Override
    @Transactional
    public VacinasResponse criar(VacinasRequest request) {
        try {
            Vacinas vacina = creator.criar(request);
            VacinasResponse response = responseBuilder.build(vacina);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_VACINAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.vacina(vacina.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar vacina. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar vacina. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir vacina", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_VACINAS, keyGenerator = "vacinasCacheKeyGenerator")
    public VacinasResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da vacina é obrigatório");
        }

        Vacinas vacina = vacinasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacina não encontrada com ID: " + id));

        return responseBuilder.build(vacina);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VacinasResponse> listar(Pageable pageable) {
        try {
            return vacinasRepository.findAll(Objects.requireNonNull(pageable, "pageable")).map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar vacinas.", e);
            throw new InternalServerErrorException("Erro ao listar vacinas", e);
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_VACINAS, keyGenerator = "vacinasCacheKeyGenerator")
    public VacinasResponse atualizar(UUID id, VacinasRequest request) {
        try {
            if (id == null) {
                throw new BadRequestException("ID da vacina é obrigatório");
            }
            if (request == null) {
                throw new BadRequestException("Dados da vacina são obrigatórios");
            }

            Vacinas vacina = updater.atualizar(id, request);
            return responseBuilder.build(vacina);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar vacina. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar vacina. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar vacina", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_VACINAS, keyGenerator = "vacinasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            inativarInternal(id);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir(inativar) vacina. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir(inativar) vacina. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir vacina", e);
        }
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da vacina é obrigatório");
        }

        Vacinas vacina = vacinasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacina não encontrada com ID: " + id));

        domainService.validarPodeInativar(vacina);
        vacina.setActive(false);
        vacinasRepository.save(Objects.requireNonNull(vacina));
    }
}
