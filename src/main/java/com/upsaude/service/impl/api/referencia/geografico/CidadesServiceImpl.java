package com.upsaude.service.impl.api.referencia.geografico;

import com.upsaude.api.request.referencia.geografico.CidadesRequest;
import com.upsaude.api.response.referencia.geografico.CidadesResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.service.api.referencia.geografico.CidadesService;
import com.upsaude.service.api.support.cidades.CidadesCreator;
import com.upsaude.service.api.support.cidades.CidadesDomainService;
import com.upsaude.service.api.support.cidades.CidadesResponseBuilder;
import com.upsaude.service.api.support.cidades.CidadesUpdater;
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
public class CidadesServiceImpl implements CidadesService {

    private final CidadesRepository cidadesRepository;
    private final CacheManager cacheManager;
    private final CidadesCreator creator;
    private final CidadesUpdater updater;
    private final CidadesResponseBuilder responseBuilder;
    private final CidadesDomainService domainService;

    @Override
    @Transactional
    public CidadesResponse criar(CidadesRequest request) {
        log.debug("Criando nova cidade. Request: {}", request);

        try {
            Cidades saved = creator.criar(request);
            CidadesResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CIDADES);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.cidade(saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar cidade. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar cidade. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir cidade", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar cidade. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CIDADES, keyGenerator = "cidadesCacheKeyGenerator")
    public CidadesResponse obterPorId(UUID id) {
        log.debug("Buscando cidade por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de cidade");
            throw new BadRequestException("ID da cidade é obrigatório");
        }

        try {
            Cidades cidades = cidadesRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + id));

            log.debug("Cidade encontrada. ID: {}", id);
            return responseBuilder.build(cidades);
        } catch (NotFoundException e) {
            log.warn("Cidade não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar cidade. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar cidade", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar cidade. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CidadesResponse> listar(Pageable pageable) {
        log.debug("Listando cidades paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Cidades> cidades = cidadesRepository.findAll(pageable);
            log.debug("Listagem de cidades concluída. Total de elementos: {}", cidades.getTotalElements());
            return cidades.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar cidades. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar cidades", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar cidades. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CidadesResponse> listarPorEstado(UUID estadoId, Pageable pageable) {
        log.debug("Listando cidades por estado. Estado ID: {}, Página: {}, Tamanho: {}",
                estadoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estadoId == null) {
            log.warn("ID de estado nulo recebido para listagem de cidades");
            throw new BadRequestException("ID do estado é obrigatório");
        }

        try {
            Page<Cidades> cidades = cidadesRepository.findByEstadoId(estadoId, pageable);

            log.debug("Listagem de cidades por estado concluída. Estado ID: {}, Total: {}", estadoId, cidades.getTotalElements());
            return cidades.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar cidades por estado. Estado ID: {}, Pageable: {}", estadoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar cidades por estado", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar cidades por estado. Estado ID: {}, Pageable: {}", estadoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CIDADES, keyGenerator = "cidadesCacheKeyGenerator")
    public CidadesResponse atualizar(UUID id, CidadesRequest request) {
        log.debug("Atualizando cidade. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de cidade");
            throw new BadRequestException("ID da cidade é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de cidade. ID: {}", id);
            throw new BadRequestException("Dados da cidade são obrigatórios");
        }

        try {
            Cidades updated = updater.atualizar(id, request);
            return responseBuilder.build(updated);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar cidade não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar cidade. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar cidade. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar cidade", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar cidade. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CIDADES, keyGenerator = "cidadesCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo cidade permanentemente. ID: {}", id);
        try {
            if (id == null) {
                log.warn("ID nulo recebido para exclusão de cidade");
                throw new BadRequestException("ID da cidade é obrigatório");
            }

            Cidades cidades = cidadesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + id));

            domainService.validarPodeDeletar(cidades);
            cidadesRepository.delete(Objects.requireNonNull(cidades));
            log.info("Cidade excluída permanentemente com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir cidade não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir cidade. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir cidade. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir cidade", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir cidade. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CIDADES, keyGenerator = "cidadesCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando cidade. ID: {}", id);
        try {
            inativarInternal(id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de inativar cidade não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao inativar cidade. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar cidade. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao inativar cidade", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao inativar cidade. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido para inativação de cidade");
            throw new BadRequestException("ID da cidade é obrigatório");
        }

        Cidades cidades = cidadesRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + id));

        domainService.validarPodeInativar(cidades);
        cidades.setActive(false);
        cidadesRepository.save(Objects.requireNonNull(cidades));
        log.info("Cidade inativada com sucesso. ID: {}", id);
    }

}
