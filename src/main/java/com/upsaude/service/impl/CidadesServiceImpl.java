package com.upsaude.service.impl;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import com.upsaude.entity.Cidades;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CidadesMapper;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.service.CidadesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Cidades.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CidadesServiceImpl implements CidadesService {

    private final CidadesRepository cidadesRepository;
    private final CidadesMapper cidadesMapper;

    @Override
    @Transactional
    @CacheEvict(value = "cidades", allEntries = true)
    public CidadesResponse criar(CidadesRequest request) {
        log.debug("Criando nova cidade. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar cidade com request nulo");
            throw new BadRequestException("Dados da cidade são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Cidades cidades = cidadesMapper.fromRequest(request);
            cidades.setActive(true);

            Cidades cidadesSalvo = cidadesRepository.save(cidades);
            log.info("Cidade criada com sucesso. ID: {}", cidadesSalvo.getId());

            return cidadesMapper.toResponse(cidadesSalvo);
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
    @Cacheable(value = "cidades", key = "#id")
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
            return cidadesMapper.toResponse(cidades);
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
            return cidades.map(cidadesMapper::toResponse);
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
            
            // Forçar inicialização do relacionamento estado antes de mapear
            cidades.getContent().forEach(cidade -> {
                if (cidade.getEstado() != null) {
                    Hibernate.initialize(cidade.getEstado());
                }
            });
            
            log.debug("Listagem de cidades por estado concluída. Estado ID: {}, Total: {}", estadoId, cidades.getTotalElements());
            return cidades.map(cidadesMapper::toResponse);
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
    @CacheEvict(value = "cidades", key = "#id")
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
            validarDadosBasicos(request);

            Cidades cidadesExistente = cidadesRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + id));

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            cidadesMapper.updateFromRequest(request, cidadesExistente);

            Cidades cidadesAtualizado = cidadesRepository.save(cidadesExistente);
            log.info("Cidade atualizada com sucesso. ID: {}", cidadesAtualizado.getId());

            return cidadesMapper.toResponse(cidadesAtualizado);
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
    @CacheEvict(value = "cidades", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo cidade. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de cidade");
            throw new BadRequestException("ID da cidade é obrigatório");
        }

        try {
            Cidades cidades = cidadesRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + id));

            if (Boolean.FALSE.equals(cidades.getActive())) {
                log.warn("Tentativa de excluir cidade já inativa. ID: {}", id);
                throw new BadRequestException("Cidade já está inativa");
            }

            cidades.setActive(false);
            cidadesRepository.save(cidades);
            log.info("Cidade excluída (desativada) com sucesso. ID: {}", id);
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

    private void validarDadosBasicos(CidadesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do cidades são obrigatórios");
        }
    }

    // Método removido - agora usa cidadesMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
}
