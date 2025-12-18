package com.upsaude.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.referencia.fabricante.FabricantesMedicamentoRequest;
import com.upsaude.api.response.referencia.fabricante.FabricantesMedicamentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.referencia.fabricante.FabricantesMedicamento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.fabricante.FabricantesMedicamentoRepository;
import com.upsaude.service.referencia.fabricante.FabricantesMedicamentoService;
import com.upsaude.service.support.fabricantesmedicamento.FabricantesMedicamentoCreator;
import com.upsaude.service.support.fabricantesmedicamento.FabricantesMedicamentoDomainService;
import com.upsaude.service.support.fabricantesmedicamento.FabricantesMedicamentoResponseBuilder;
import com.upsaude.service.support.fabricantesmedicamento.FabricantesMedicamentoUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesMedicamentoServiceImpl implements FabricantesMedicamentoService {

    private final FabricantesMedicamentoRepository fabricantesMedicamentoRepository;
    private final CacheManager cacheManager;

    private final FabricantesMedicamentoCreator creator;
    private final FabricantesMedicamentoUpdater updater;
    private final FabricantesMedicamentoResponseBuilder responseBuilder;
    private final FabricantesMedicamentoDomainService domainService;

    @Override
    @Transactional
    public FabricantesMedicamentoResponse criar(FabricantesMedicamentoRequest request) {
        log.debug("Criando novo fabricantesmedicamento");
        FabricantesMedicamento saved = creator.criar(request);
        FabricantesMedicamentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_FABRICANTES_MEDICAMENTO);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.fabricanteMedicamento(saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_MEDICAMENTO, keyGenerator = "fabricantesMedicamentoCacheKeyGenerator")
    public FabricantesMedicamentoResponse obterPorId(UUID id) {
        log.debug("Buscando fabricantesmedicamento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        FabricantesMedicamento fabricantesMedicamento = fabricantesMedicamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        return responseBuilder.build(fabricantesMedicamento);
    }

    @Override
    public Page<FabricantesMedicamentoResponse> listar(Pageable pageable) {
        log.debug("Listando FabricantesMedicamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesMedicamento> fabricantesMedicamentos = fabricantesMedicamentoRepository.findAll(pageable);
        return fabricantesMedicamentos.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_MEDICAMENTO, keyGenerator = "fabricantesMedicamentoCacheKeyGenerator")
    public FabricantesMedicamentoResponse atualizar(UUID id, FabricantesMedicamentoRequest request) {
        log.debug("Atualizando fabricantesmedicamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        FabricantesMedicamento updated = updater.atualizar(id, request);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_MEDICAMENTO, keyGenerator = "fabricantesMedicamentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo fabricantesmedicamento. ID: {}", id);
        inativarInternal(id);
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        FabricantesMedicamento entity = fabricantesMedicamentoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        fabricantesMedicamentoRepository.save(Objects.requireNonNull(entity));
        log.info("FabricantesMedicamento excluído (desativado) com sucesso. ID: {}", id);
    }
}
