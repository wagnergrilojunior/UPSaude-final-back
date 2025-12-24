package com.upsaude.service.impl.api.deficiencia;

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

import com.upsaude.api.request.deficiencia.DeficienciasRequest;
import com.upsaude.api.response.deficiencia.DeficienciasResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.paciente.deficiencia.Deficiencias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.deficiencia.DeficienciasRepository;
import com.upsaude.service.api.deficiencia.DeficienciasService;
import com.upsaude.service.api.support.deficiencias.DeficienciasCreator;
import com.upsaude.service.api.support.deficiencias.DeficienciasDomainService;
import com.upsaude.service.api.support.deficiencias.DeficienciasResponseBuilder;
import com.upsaude.service.api.support.deficiencias.DeficienciasUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeficienciasServiceImpl implements DeficienciasService {

    private final DeficienciasRepository deficienciasRepository;
    private final CacheManager cacheManager;

    private final DeficienciasCreator creator;
    private final DeficienciasUpdater updater;
    private final DeficienciasResponseBuilder responseBuilder;
    private final DeficienciasDomainService domainService;

    @Override
    @Transactional
    public DeficienciasResponse criar(DeficienciasRequest request) {
        log.debug("Criando nova deficiência");
        Deficiencias saved = creator.criar(request);
        DeficienciasResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_DEFICIENCIAS);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.deficiencia(saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_DEFICIENCIAS, keyGenerator = "deficienciasCacheKeyGenerator")
    public DeficienciasResponse obterPorId(UUID id) {
        log.debug("Buscando deficiência por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }

        Deficiencias deficiencias = deficienciasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + id));

        return responseBuilder.build(deficiencias);
    }

    @Override
    public Page<DeficienciasResponse> listar(Pageable pageable) {
        log.debug("Listando deficiências paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Deficiencias> deficiencias = deficienciasRepository.findAll(pageable);
        return deficiencias.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_DEFICIENCIAS, keyGenerator = "deficienciasCacheKeyGenerator")
    public DeficienciasResponse atualizar(UUID id, DeficienciasRequest request) {
        log.debug("Atualizando deficiência. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }

        Deficiencias updated = updater.atualizar(id, request);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_DEFICIENCIAS, keyGenerator = "deficienciasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo deficiência. ID: {}", id);
        inativarInternal(id);
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }

        Deficiencias entity = deficienciasRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + id));

        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        deficienciasRepository.save(Objects.requireNonNull(entity));
        log.info("Deficiência excluída (desativada) com sucesso. ID: {}", id);
    }
}
