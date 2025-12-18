package com.upsaude.service.impl;

import com.upsaude.api.request.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.api.response.equipamento.FabricantesEquipamentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.equipamento.FabricantesEquipamento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.equipamento.FabricantesEquipamentoRepository;
import com.upsaude.service.equipamento.FabricantesEquipamentoService;
import com.upsaude.service.support.fabricantesequipamento.FabricantesEquipamentoCreator;
import com.upsaude.service.support.fabricantesequipamento.FabricantesEquipamentoDomainService;
import com.upsaude.service.support.fabricantesequipamento.FabricantesEquipamentoResponseBuilder;
import com.upsaude.service.support.fabricantesequipamento.FabricantesEquipamentoUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoServiceImpl implements FabricantesEquipamentoService {

    private final FabricantesEquipamentoRepository fabricantesEquipamentoRepository;
    private final CacheManager cacheManager;

    private final FabricantesEquipamentoCreator creator;
    private final FabricantesEquipamentoUpdater updater;
    private final FabricantesEquipamentoResponseBuilder responseBuilder;
    private final FabricantesEquipamentoDomainService domainService;

    @Override
    @Transactional
    public FabricantesEquipamentoResponse criar(FabricantesEquipamentoRequest request) {
        log.debug("Criando novo fabricante de equipamento");
        FabricantesEquipamento saved = creator.criar(request);
        FabricantesEquipamentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_FABRICANTES_EQUIPAMENTO);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.fabricanteEquipamento(saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_EQUIPAMENTO, keyGenerator = "fabricantesEquipamentoCacheKeyGenerator")
    public FabricantesEquipamentoResponse obterPorId(UUID id) {
        log.debug("Buscando fabricante de equipamento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do fabricante de equipamento é obrigatório");
        }

        FabricantesEquipamento fabricantesEquipamento = fabricantesEquipamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));

        return responseBuilder.build(fabricantesEquipamento);
    }

    @Override
    public Page<FabricantesEquipamentoResponse> listar(Pageable pageable) {
        log.debug("Listando FabricantesEquipamento paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesEquipamento> fabricantesEquipamentos = fabricantesEquipamentoRepository.findAll(pageable);
        return fabricantesEquipamentos.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_EQUIPAMENTO, keyGenerator = "fabricantesEquipamentoCacheKeyGenerator")
    public FabricantesEquipamentoResponse atualizar(UUID id, FabricantesEquipamentoRequest request) {
        log.debug("Atualizando fabricante de equipamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricante de equipamento é obrigatório");
        }

        FabricantesEquipamento updated = updater.atualizar(id, request);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_EQUIPAMENTO, keyGenerator = "fabricantesEquipamentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo fabricante de equipamento. ID: {}", id);
        inativarInternal(id);
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do fabricante de equipamento é obrigatório");
        }

        FabricantesEquipamento entity = fabricantesEquipamentoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));

        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        fabricantesEquipamentoRepository.save(Objects.requireNonNull(entity));
        log.info("Fabricante de equipamento excluído (desativado) com sucesso. ID: {}", id);
    }
}
