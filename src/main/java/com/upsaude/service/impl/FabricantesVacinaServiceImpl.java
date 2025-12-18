package com.upsaude.service.impl;

import com.upsaude.api.request.vacina.FabricantesVacinaRequest;
import com.upsaude.api.response.vacina.FabricantesVacinaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.vacina.FabricantesVacina;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.vacina.FabricantesVacinaRepository;
import com.upsaude.service.vacina.FabricantesVacinaService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.fabricantesvacina.FabricantesVacinaCreator;
import com.upsaude.service.support.fabricantesvacina.FabricantesVacinaDomainService;
import com.upsaude.service.support.fabricantesvacina.FabricantesVacinaResponseBuilder;
import com.upsaude.service.support.fabricantesvacina.FabricantesVacinaUpdater;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesVacinaServiceImpl implements FabricantesVacinaService {

    private final FabricantesVacinaRepository fabricantesVacinaRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final FabricantesVacinaCreator creator;
    private final FabricantesVacinaUpdater updater;
    private final FabricantesVacinaResponseBuilder responseBuilder;
    private final FabricantesVacinaDomainService domainService;

    @Override
    @Transactional
    public FabricantesVacinaResponse criar(FabricantesVacinaRequest request) {
        log.debug("Criando novo FabricantesVacina");
        UUID tenantId = tenantService.validarTenantAtual();
        var tenant = tenantService.obterTenantDoUsuarioAutenticado();

        FabricantesVacina saved = creator.criar(request, tenantId, tenant);
        FabricantesVacinaResponse response = responseBuilder.build(saved, tenantId);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_FABRICANTES_VACINA);
        if (cache != null) {
            Object key = Objects.requireNonNull((Object) CacheKeyUtil.fabricanteVacina(saved.getId()));
            cache.put(key, response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_VACINA, keyGenerator = "fabricantesVacinaCacheKeyGenerator")
    public FabricantesVacinaResponse obterPorId(UUID id) {
        log.debug("Buscando FabricantesVacina por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de FabricantesVacina");
            throw new BadRequestException("ID do fabricante de vacina é obrigatório");
        }

        FabricantesVacina fabricantesVacina = fabricantesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        log.debug("FabricantesVacina encontrado. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        return responseBuilder.build(fabricantesVacina, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricantesVacinaResponse> listar(Pageable pageable) {
        log.debug("Listando FabricantesVacinas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesVacina> fabricantesVacinas = fabricantesVacinaRepository.findAll(pageable);
        log.debug("Listagem de FabricantesVacinas concluída. Total de elementos: {}", fabricantesVacinas.getTotalElements());
        UUID tenantId = tenantService.validarTenantAtual();
        return fabricantesVacinas.map(e -> responseBuilder.build(e, tenantId));
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_VACINA, keyGenerator = "fabricantesVacinaCacheKeyGenerator")
    public FabricantesVacinaResponse atualizar(UUID id, FabricantesVacinaRequest request) {
        log.debug("Atualizando FabricantesVacina. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de FabricantesVacina");
            throw new BadRequestException("ID do fabricante de vacina é obrigatório");
        }

        if (request == null) {
            log.warn("Request nulo recebido para atualização de FabricantesVacina. ID: {}", id);
            throw new BadRequestException("Dados do fabricante de vacina são obrigatórios");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        var tenant = tenantService.obterTenantDoUsuarioAutenticado();

        FabricantesVacina updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated, tenantId);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FABRICANTES_VACINA, keyGenerator = "fabricantesVacinaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo FabricantesVacina. ID: {}", id);

        inativarInternal(id);
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de FabricantesVacina");
            throw new BadRequestException("ID do fabricante de vacina é obrigatório");
        }

        FabricantesVacina entity = fabricantesVacinaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        fabricantesVacinaRepository.save(Objects.requireNonNull(entity));
        log.info("FabricantesVacina excluído (desativado) com sucesso. ID: {}", id);
    }
}
