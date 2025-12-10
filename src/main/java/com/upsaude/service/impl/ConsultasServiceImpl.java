package com.upsaude.service.impl;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.api.response.ConsultasResponse;
import com.upsaude.entity.Consultas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ConsultasMapper;
import com.upsaude.repository.ConsultasRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.service.ConsultasService;
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
 * Implementação do serviço de gerenciamento de Consultas (Agendamentos).
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasServiceImpl implements ConsultasService {

    private final ConsultasRepository consultasRepository;
    private final ConsultasMapper consultasMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    @Transactional
    @CacheEvict(value = "consultas", allEntries = true)
    public ConsultasResponse criar(ConsultasRequest request) {
        log.debug("Criando nova consulta. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar consulta com request nulo");
            throw new BadRequestException("Dados da consulta são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Consultas consulta = consultasMapper.fromRequest(request);
            consulta.setActive(true);

            Consultas consultaSalva = consultasRepository.save(consulta);
            log.info("Consulta criada com sucesso. ID: {}", consultaSalva.getId());

            return consultasMapper.toResponse(consultaSalva);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar consulta. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar consulta. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir consulta", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar consulta. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "consultas", key = "#id")
    public ConsultasResponse obterPorId(UUID id) {
        log.debug("Buscando consulta por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de consulta");
            throw new BadRequestException("ID da consulta é obrigatório");
        }

        try {
            Consultas consulta = consultasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Consulta não encontrada com ID: " + id));

            log.debug("Consulta encontrada. ID: {}", id);
            return consultasMapper.toResponse(consulta);
        } catch (NotFoundException e) {
            log.warn("Consulta não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar consulta. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar consulta", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar consulta. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultasResponse> listar(Pageable pageable) {
        log.debug("Listando consultas paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Consultas> consultas = consultasRepository.findAll(pageable);
            log.debug("Listagem de consultas concluída. Total de elementos: {}", consultas.getTotalElements());
            return consultas.map(consultasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar consultas. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar consultas", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar consultas. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando consultas do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de consultas");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Page<Consultas> consultas = consultasRepository.findByEstabelecimentoIdOrderByInformacoesDataConsultaDesc(estabelecimentoId, pageable);
            log.debug("Listagem de consultas do estabelecimento concluída. Estabelecimento ID: {}, Total: {}", estabelecimentoId, consultas.getTotalElements());
            return consultas.map(consultasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar consultas por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar consultas do estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar consultas por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "consultas", key = "#id")
    public ConsultasResponse atualizar(UUID id, ConsultasRequest request) {
        log.debug("Atualizando consulta. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de consulta");
            throw new BadRequestException("ID da consulta é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de consulta. ID: {}", id);
            throw new BadRequestException("Dados da consulta são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Consultas consultaExistente = consultasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Consulta não encontrada com ID: " + id));

            atualizarDadosConsulta(consultaExistente, request);

            Consultas consultaAtualizada = consultasRepository.save(consultaExistente);
            log.info("Consulta atualizada com sucesso. ID: {}", consultaAtualizada.getId());

            return consultasMapper.toResponse(consultaAtualizada);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar consulta não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar consulta. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar consulta. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar consulta", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar consulta. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "consultas", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo consulta. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de consulta");
            throw new BadRequestException("ID da consulta é obrigatório");
        }

        try {
            Consultas consulta = consultasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Consulta não encontrada com ID: " + id));

            if (Boolean.FALSE.equals(consulta.getActive())) {
                log.warn("Tentativa de excluir consulta já inativa. ID: {}", id);
                throw new BadRequestException("Consulta já está inativa");
            }

            consulta.setActive(false);
            consultasRepository.save(consulta);
            log.info("Consulta excluída (desativada) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir consulta não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir consulta. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir consulta. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir consulta", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir consulta. ID: {}", id, e);
            throw e;
        }
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.
    // O campo 'paciente' já tem @NotNull e 'informacoes' já tem @Valid no Request.

    private void atualizarDadosConsulta(Consultas consulta, ConsultasRequest request) {
        // Usar mapper para atualizar campos básicos
        consultasMapper.updateFromRequest(request, consulta);
        
        // Processar relacionamentos que são ignorados pelo mapper
        // (relacionamentos são tratados manualmente quando necessário)
    }
}

