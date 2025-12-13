package com.upsaude.service.impl;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.api.response.CidDoencasResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.CidDoencas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.service.CidDoencasService;
import com.upsaude.service.support.ciddoencas.CidDoencasCreator;
import com.upsaude.service.support.ciddoencas.CidDoencasDomainService;
import com.upsaude.service.support.ciddoencas.CidDoencasResponseBuilder;
import com.upsaude.service.support.ciddoencas.CidDoencasUpdater;
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
public class CidDoencasServiceImpl implements CidDoencasService {

    private final CidDoencasRepository cidDoencasRepository;
    private final CacheManager cacheManager;
    private final CidDoencasCreator creator;
    private final CidDoencasUpdater updater;
    private final CidDoencasResponseBuilder responseBuilder;
    private final CidDoencasDomainService domainService;

    @Override
    @Transactional
    public CidDoencasResponse criar(CidDoencasRequest request) {
        log.debug("Criando novo CID de doença. Request: {}", request);

        try {
            CidDoencas saved = creator.criar(request);
            CidDoencasResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CID_DOENCAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.cidDoenca(saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao criar CID de doença. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar CID de doença. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir CID de doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar CID de doença. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CID_DOENCAS, keyGenerator = "cidDoencasCacheKeyGenerator")
    public CidDoencasResponse obterPorId(UUID id) {
        log.debug("Buscando CID de doença por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de CID de doença");
            throw new BadRequestException("ID do CID de doença é obrigatório");
        }

        try {
            CidDoencas cidDoencas = cidDoencasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("CID de doença não encontrado com ID: " + id));

            log.debug("CID de doença encontrado. ID: {}", id);
            return responseBuilder.build(cidDoencas);
        } catch (NotFoundException e) {
            log.warn("CID de doença não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar CID de doença. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar CID de doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar CID de doença. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CidDoencasResponse> listar(Pageable pageable) {
        log.debug("Listando CID de doenças paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<CidDoencas> cidDoencas = cidDoencasRepository.findAll(pageable);
            log.debug("Listagem de CID de doenças concluída. Total de elementos: {}", cidDoencas.getTotalElements());
            return cidDoencas.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar CID de doenças. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar CID de doenças", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar CID de doenças. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CID_DOENCAS, keyGenerator = "cidDoencasCacheKeyGenerator")
    public CidDoencasResponse atualizar(UUID id, CidDoencasRequest request) {
        log.debug("Atualizando CID de doença. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de CID de doença");
            throw new BadRequestException("ID do CID de doença é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de CID de doença. ID: {}", id);
            throw new BadRequestException("Dados do CID de doença são obrigatórios");
        }

        try {
            CidDoencas updated = updater.atualizar(id, request);
            return responseBuilder.build(updated);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar CID de doença não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao atualizar CID de doença. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar CID de doença. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar CID de doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar CID de doença. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CID_DOENCAS, keyGenerator = "cidDoencasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo CID de doença. ID: {}", id);

        try {
            inativarInternal(id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir CID de doença não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir CID de doença. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir CID de doença. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir CID de doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir CID de doença. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de CID de doença");
            throw new BadRequestException("ID do CID de doença é obrigatório");
        }

        CidDoencas cidDoencas = cidDoencasRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("CID de doença não encontrado com ID: " + id));

        domainService.validarPodeInativar(cidDoencas);
        cidDoencas.setActive(false);
        cidDoencasRepository.save(Objects.requireNonNull(cidDoencas));
        log.info("CID de doença excluído (desativado) com sucesso. ID: {}", id);
    }

}
