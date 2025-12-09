package com.upsaude.service.impl;

import com.upsaude.api.request.EstadosRequest;
import com.upsaude.api.response.EstadosResponse;
import com.upsaude.entity.Estados;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EstadosMapper;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.service.EstadosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Estados.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EstadosServiceImpl implements EstadosService {

    private final EstadosRepository estadosRepository;
    private final EstadosMapper estadosMapper;

    @Override
    @Transactional
    @CacheEvict(value = "estados", allEntries = true)
    public EstadosResponse criar(EstadosRequest request) {
        log.debug("Criando novo estado. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar estado com request nulo");
            throw new BadRequestException("Dados do estado são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Estados estados = estadosMapper.fromRequest(request);
            estados.setActive(true);

            Estados estadosSalvo = estadosRepository.save(estados);
            log.info("Estado criado com sucesso. ID: {}", estadosSalvo.getId());

            return estadosMapper.toResponse(estadosSalvo);
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
    @Cacheable(value = "estados", key = "#id")
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
            return estadosMapper.toResponse(estados);
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
            return estados.map(estadosMapper::toResponse);
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
    @CacheEvict(value = "estados", key = "#id")
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
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Estados estadosExistente = estadosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + id));

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            estadosMapper.updateFromRequest(request, estadosExistente);

            Estados estadosAtualizado = estadosRepository.save(estadosExistente);
            log.info("Estado atualizado com sucesso. ID: {}", estadosAtualizado.getId());

            return estadosMapper.toResponse(estadosAtualizado);
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
    @CacheEvict(value = "estados", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo estado. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de estado");
            throw new BadRequestException("ID do estado é obrigatório");
        }

        try {
            Estados estados = estadosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(estados.getActive())) {
                log.warn("Tentativa de excluir estado já inativo. ID: {}", id);
                throw new BadRequestException("Estado já está inativo");
            }

            estados.setActive(false);
            estadosRepository.save(estados);
            log.info("Estado excluído (desativado) com sucesso. ID: {}", id);
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

        // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    // Método removido - agora usa estadosMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
}
