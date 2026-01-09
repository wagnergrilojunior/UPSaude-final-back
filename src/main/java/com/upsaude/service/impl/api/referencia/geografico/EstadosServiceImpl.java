package com.upsaude.service.impl.api.referencia.geografico;

import com.upsaude.api.request.referencia.geografico.EstadosRequest;
import com.upsaude.api.response.referencia.geografico.EstadosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.geografico.EstadosRepository;
import com.upsaude.service.api.referencia.geografico.EstadosService;
import com.upsaude.service.api.support.estados.EstadosCreator;
import com.upsaude.service.api.support.estados.EstadosDomainService;
import com.upsaude.service.api.support.estados.EstadosResponseBuilder;
import com.upsaude.service.api.support.estados.EstadosUpdater;
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
public class EstadosServiceImpl implements EstadosService {

    private final EstadosRepository estadosRepository;
    private final CacheManager cacheManager;
    private final EstadosCreator creator;
    private final EstadosUpdater updater;
    private final EstadosResponseBuilder responseBuilder;
    private final EstadosDomainService domainService;

    @Override
    @Transactional
    public EstadosResponse criar(EstadosRequest request) {
        log.debug("Criando novo estado. Request: {}", request);

        try {
            Estados saved = creator.criar(request);
            EstadosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_ESTADOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.estado(saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar estado. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar estado. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir estado", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar estado. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ESTADOS, keyGenerator = "estadosCacheKeyGenerator")
    public EstadosResponse obterPorId(UUID id) {
        log.debug("Buscando estado por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de estado");
            throw new BadRequestException("ID do estado é obrigatório");
        }

        try {
            Estados estados = estadosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + id));

            log.debug("Estado encontrado. ID: {}", id);
            return responseBuilder.build(estados);
        } catch (NotFoundException e) {
            log.warn("Estado não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar estado. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar estado", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar estado. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadosResponse> listar(Pageable pageable) {
        log.debug("Listando estados paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Estados> estados = estadosRepository.findAll(pageable);
            log.debug("Listagem de estados concluída. Total de elementos: {}", estados.getTotalElements());
            return estados.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar estados. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar estados", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar estados. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ESTADOS, keyGenerator = "estadosCacheKeyGenerator")
    public EstadosResponse atualizar(UUID id, EstadosRequest request) {
        log.debug("Atualizando estado. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de estado");
            throw new BadRequestException("ID do estado é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de estado. ID: {}", id);
            throw new BadRequestException("Dados do estado são obrigatórios");
        }

        try {
            Estados updated = updater.atualizar(id, request);
            return responseBuilder.build(updated);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar estado não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar estado. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar estado. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar estado", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar estado. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ESTADOS, keyGenerator = "estadosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo estado permanentemente. ID: {}", id);
        try {
            if (id == null) {
                log.warn("ID nulo recebido para exclusão de estado");
                throw new BadRequestException("ID do estado é obrigatório");
            }

            Estados entity = estadosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + id));

            domainService.validarPodeDeletar(entity);
            estadosRepository.delete(Objects.requireNonNull(entity));
            log.info("Estado excluído permanentemente com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir estado não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir estado. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir estado. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir estado", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir estado. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ESTADOS, keyGenerator = "estadosCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando estado. ID: {}", id);
        try {
            inativarInternal(id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de inativar estado não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao inativar estado. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar estado. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao inativar estado", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao inativar estado. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido para inativação de estado");
            throw new BadRequestException("ID do estado é obrigatório");
        }

        Estados entity = estadosRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + id));

        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        estadosRepository.save(Objects.requireNonNull(entity));
        log.info("Estado inativado com sucesso. ID: {}", id);
    }

}
