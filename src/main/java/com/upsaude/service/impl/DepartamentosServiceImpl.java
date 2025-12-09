package com.upsaude.service.impl;

import com.upsaude.api.request.DepartamentosRequest;
import com.upsaude.api.response.DepartamentosResponse;
import com.upsaude.entity.Departamentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DepartamentosMapper;
import com.upsaude.repository.DepartamentosRepository;
import com.upsaude.service.DepartamentosService;
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
 * Implementação do serviço de gerenciamento de Departamentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DepartamentosServiceImpl implements DepartamentosService {

    private final DepartamentosRepository departamentosRepository;
    private final DepartamentosMapper departamentosMapper;

    @Override
    @Transactional
    @CacheEvict(value = "departamentos", allEntries = true)
    public DepartamentosResponse criar(DepartamentosRequest request) {
        log.debug("Criando novo departamento. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar departamento com request nulo");
            throw new BadRequestException("Dados do departamento são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Departamentos departamentos = departamentosMapper.fromRequest(request);
            departamentos.setActive(true);

            Departamentos departamentosSalvo = departamentosRepository.save(departamentos);
            log.info("Departamento criado com sucesso. ID: {}", departamentosSalvo.getId());

            return departamentosMapper.toResponse(departamentosSalvo);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar departamento. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar departamento. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir departamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar departamento. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "departamentos", key = "#id")
    public DepartamentosResponse obterPorId(UUID id) {
        log.debug("Buscando departamento por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de departamento");
            throw new BadRequestException("ID do departamento é obrigatório");
        }

        try {
            Departamentos departamentos = departamentosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Departamento não encontrado com ID: " + id));

            log.debug("Departamento encontrado. ID: {}", id);
            return departamentosMapper.toResponse(departamentos);
        } catch (NotFoundException e) {
            log.warn("Departamento não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar departamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar departamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar departamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartamentosResponse> listar(Pageable pageable) {
        log.debug("Listando departamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Departamentos> departamentos = departamentosRepository.findAll(pageable);
            log.debug("Listagem de departamentos concluída. Total de elementos: {}", departamentos.getTotalElements());
            return departamentos.map(departamentosMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar departamentos. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar departamentos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar departamentos. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "departamentos", key = "#id")
    public DepartamentosResponse atualizar(UUID id, DepartamentosRequest request) {
        log.debug("Atualizando departamento. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de departamento");
            throw new BadRequestException("ID do departamento é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de departamento. ID: {}", id);
            throw new BadRequestException("Dados do departamento são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Departamentos departamentosExistente = departamentosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Departamento não encontrado com ID: " + id));

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            departamentosMapper.updateFromRequest(request, departamentosExistente);

            Departamentos departamentosAtualizado = departamentosRepository.save(departamentosExistente);
            log.info("Departamento atualizado com sucesso. ID: {}", departamentosAtualizado.getId());

            return departamentosMapper.toResponse(departamentosAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar departamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar departamento. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar departamento. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar departamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar departamento. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "departamentos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo departamento. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de departamento");
            throw new BadRequestException("ID do departamento é obrigatório");
        }

        try {
            Departamentos departamentos = departamentosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Departamento não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(departamentos.getActive())) {
                log.warn("Tentativa de excluir departamento já inativo. ID: {}", id);
                throw new BadRequestException("Departamento já está inativo");
            }

            departamentos.setActive(false);
            departamentosRepository.save(departamentos);
            log.info("Departamento excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir departamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir departamento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir departamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir departamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir departamento. ID: {}", id, e);
            throw e;
        }
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    // Método removido - agora usa departamentosMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
}
