package com.upsaude.service.impl.api.estabelecimento.equipamento;

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

import com.upsaude.api.request.estabelecimento.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.api.response.estabelecimento.equipamento.FabricantesEquipamentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.equipamento.FabricantesEquipamentoRepository;
import com.upsaude.service.api.estabelecimento.equipamento.FabricantesEquipamentoService;
import com.upsaude.service.api.support.fabricantesequipamento.FabricantesEquipamentoCreator;
import com.upsaude.service.api.support.fabricantesequipamento.FabricantesEquipamentoResponseBuilder;
import com.upsaude.service.api.support.fabricantesequipamento.FabricantesEquipamentoUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoServiceImpl implements FabricantesEquipamentoService {

    private final FabricantesEquipamentoRepository repository;
    private final CacheManager cacheManager;

    private final FabricantesEquipamentoCreator creator;
    private final FabricantesEquipamentoUpdater updater;
    private final FabricantesEquipamentoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public FabricantesEquipamentoResponse criar(FabricantesEquipamentoRequest request) {
        log.debug("Criando novo fabricante de equipamento");

        try {
            FabricantesEquipamento saved = creator.criar(request);
            FabricantesEquipamentoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_FABRICANTES_EQUIPAMENTO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.fabricanteEquipamento(saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar fabricante de equipamento", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_EQUIPAMENTO, keyGenerator = "fabricantesEquipamentoCacheKeyGenerator")
    public FabricantesEquipamentoResponse obterPorId(UUID id) {
        log.debug("Buscando fabricante de equipamento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do fabricante de equipamento é obrigatório");
        }

        FabricantesEquipamento entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricantesEquipamentoResponse> listar(Pageable pageable) {
        log.debug("Listando fabricantes de equipamento paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesEquipamento> page = repository.findAll(pageable);
        return page.map(responseBuilder::build);
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

        FabricantesEquipamento entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Fabricante de equipamento excluído (desativado) com sucesso. ID: {}", id);
    }
}
