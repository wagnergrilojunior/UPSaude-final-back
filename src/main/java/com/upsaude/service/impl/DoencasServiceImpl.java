package com.upsaude.service.impl;

import com.upsaude.api.request.doencas.DoencasRequest;
import com.upsaude.api.response.doencas.DoencasResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.doencas.Doencas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.doencas.DoencasRepository;
import com.upsaude.service.doencas.DoencasService;
import com.upsaude.service.support.doencas.DoencasCreator;
import com.upsaude.service.support.doencas.DoencasDomainService;
import com.upsaude.service.support.doencas.DoencasResponseBuilder;
import com.upsaude.service.support.doencas.DoencasUpdater;
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
public class DoencasServiceImpl implements DoencasService {

    private final DoencasRepository doencasRepository;
    private final CacheManager cacheManager;

    private final DoencasCreator creator;
    private final DoencasUpdater updater;
    private final DoencasDomainService domainService;
    private final DoencasResponseBuilder responseBuilder;

    @Override
    @Transactional
    public DoencasResponse criar(DoencasRequest request) {
        log.debug("Criando nova doença. Request: {}", request);

        try {
            Doencas doenca = creator.criar(request);
            DoencasResponse response = responseBuilder.build(doenca);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_DOENCAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.doenca(doenca.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar doença. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar doença. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar doença. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_DOENCAS, keyGenerator = "doencasCacheKeyGenerator")
    public DoencasResponse obterPorId(UUID id) {
        log.debug("Buscando doença por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de doença");
            throw new BadRequestException("ID da doença é obrigatório");
        }

        try {
            Doencas doenca = doencasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

            log.debug("Doença encontrada. ID: {}", id);
            return responseBuilder.build(doenca);
        } catch (NotFoundException e) {
            log.warn("Doença não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar doença. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar doença. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoencasResponse> listar(Pageable pageable) {
        log.debug("Listando doenças paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Doencas> doencas = doencasRepository.findAll(pageable);
            log.debug("Listagem de doenças concluída. Total de elementos: {}", doencas.getTotalElements());
            return doencas.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar doenças. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar doenças", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar doenças. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoencasResponse> listarPorNome(String nome, Pageable pageable) {
        log.debug("Listando doenças por nome: {}. Página: {}, Tamanho: {}",
                nome, pageable.getPageNumber(), pageable.getPageSize());

        if (nome == null || nome.trim().isEmpty()) {
            log.warn("Nome vazio ou nulo recebido para busca de doenças");
            throw new BadRequestException("Nome é obrigatório para busca");
        }

        try {
            Page<Doencas> doencas = doencasRepository.findByNomeContainingIgnoreCase(nome, pageable);
            log.debug("Listagem de doenças por nome concluída. Nome: {}, Total: {}", nome, doencas.getTotalElements());
            return doencas.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar doenças por nome. Nome: {}, Pageable: {}", nome, pageable, e);
            throw new InternalServerErrorException("Erro ao listar doenças por nome", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar doenças por nome. Nome: {}, Pageable: {}", nome, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoencasResponse> listarPorCodigoCid(String codigoCid, Pageable pageable) {
        log.debug("Listando doenças por código CID: {}. Página: {}, Tamanho: {}",
                codigoCid, pageable.getPageNumber(), pageable.getPageSize());

        if (codigoCid == null || codigoCid.trim().isEmpty()) {
            log.warn("Código CID vazio ou nulo recebido para busca de doenças");
            throw new BadRequestException("Código CID é obrigatório para busca");
        }

        try {
            Page<Doencas> doencas = doencasRepository.findByCodigoCid(codigoCid, pageable);
            log.debug("Listagem de doenças por código CID concluída. Código CID: {}, Total: {}", codigoCid, doencas.getTotalElements());
            return doencas.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar doenças por código CID. Código CID: {}, Pageable: {}", codigoCid, pageable, e);
            throw new InternalServerErrorException("Erro ao listar doenças por código CID", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar doenças por código CID. Código CID: {}, Pageable: {}", codigoCid, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_DOENCAS, keyGenerator = "doencasCacheKeyGenerator")
    public DoencasResponse atualizar(UUID id, DoencasRequest request) {
        log.debug("Atualizando doença. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de doença");
            throw new BadRequestException("ID da doença é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de doença. ID: {}", id);
            throw new BadRequestException("Dados da doença são obrigatórios");
        }

        try {
            Doencas doencaAtualizada = updater.atualizar(id, request);
            return responseBuilder.build(doencaAtualizada);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar doença não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar doença. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar doença. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar doença. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_DOENCAS, keyGenerator = "doencasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo doença. ID: {}", id);

        try {
            inativarInternal(id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir doença não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir doença. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir doença. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir doença. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de doença");
            throw new BadRequestException("ID da doença é obrigatório");
        }

        Doencas doenca = doencasRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

        domainService.validarPodeInativar(doenca);
        doenca.setActive(false);
        doencasRepository.save(Objects.requireNonNull(doenca));
        log.info("Doença excluída (desativada) com sucesso. ID: {}", id);
    }
}
