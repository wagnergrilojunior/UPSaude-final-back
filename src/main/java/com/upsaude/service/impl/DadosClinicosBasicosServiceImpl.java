package com.upsaude.service.impl;

import com.upsaude.api.request.DadosClinicosBasicosRequest;
import com.upsaude.api.response.DadosClinicosBasicosResponse;
import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DadosClinicosBasicosMapper;
import com.upsaude.repository.DadosClinicosBasicosRepository;
import com.upsaude.service.DadosClinicosBasicosService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosServiceImpl implements DadosClinicosBasicosService {

    private final DadosClinicosBasicosRepository repository;
    private final DadosClinicosBasicosMapper mapper;

    @Override
    @Transactional
    @CacheEvict(value = "dadosclinicosbasicos", allEntries = true)
    public DadosClinicosBasicosResponse criar(DadosClinicosBasicosRequest request) {
        log.debug("Criando dados clínicos básicos. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar dados clínicos básicos com request nulo");
            throw new BadRequestException("Dados clínicos básicos são obrigatórios");
        }
        if (request.getPaciente() == null) {
            log.warn("ID de paciente nulo recebido para criação de dados clínicos básicos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            repository.findByPacienteId(request.getPaciente())
                    .ifPresent(d -> {
                        log.warn("Tentativa de criar dados clínicos básicos duplicados para paciente. Paciente ID: {}", request.getPaciente());
                        throw new ConflictException("Dados clínicos básicos já existem para este paciente");
                    });

            DadosClinicosBasicos entity = mapper.fromRequest(request);
            entity.setActive(true);

            DadosClinicosBasicos saved = repository.save(entity);
            log.info("Dados clínicos básicos criados. ID: {}", saved.getId());

            return mapper.toResponse(saved);
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao criar dados clínicos básicos. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar dados clínicos básicos. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir dados clínicos básicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar dados clínicos básicos. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DadosClinicosBasicosResponse obterPorId(UUID id) {
        log.debug("Buscando dados clínicos básicos por ID: {}", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }

        try {
            DadosClinicosBasicos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

            log.debug("Dados clínicos básicos encontrados. ID: {}", id);
            return mapper.toResponse(entity);
        } catch (NotFoundException e) {
            log.warn("Dados clínicos básicos não encontrados. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar dados clínicos básicos. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar dados clínicos básicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar dados clínicos básicos. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DadosClinicosBasicosResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando dados clínicos básicos por paciente ID: {}", pacienteId);
        
        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para busca de dados clínicos básicos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            DadosClinicosBasicos entity = repository.findByPacienteId(pacienteId)
                    .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados para o paciente: " + pacienteId));

            log.debug("Dados clínicos básicos encontrados para paciente. Paciente ID: {}", pacienteId);
            return mapper.toResponse(entity);
        } catch (NotFoundException e) {
            log.warn("Dados clínicos básicos não encontrados para paciente. Paciente ID: {}", pacienteId);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar dados clínicos básicos por paciente. Paciente ID: {}", pacienteId, e);
            throw new InternalServerErrorException("Erro ao buscar dados clínicos básicos do paciente", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar dados clínicos básicos por paciente. Paciente ID: {}", pacienteId, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosClinicosBasicosResponse> listar(Pageable pageable) {
        log.debug("Listando dados clínicos básicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<DadosClinicosBasicos> entities = repository.findAll(pageable);
            log.debug("Listagem de dados clínicos básicos concluída. Total de elementos: {}", entities.getTotalElements());
            return entities.map(mapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar dados clínicos básicos. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar dados clínicos básicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar dados clínicos básicos. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "dadosclinicosbasicos", key = "#id")
    public DadosClinicosBasicosResponse atualizar(UUID id, DadosClinicosBasicosRequest request) {
        log.debug("Atualizando dados clínicos básicos. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de dados clínicos básicos. ID: {}", id);
            throw new BadRequestException("Dados clínicos básicos são obrigatórios");
        }

        try {
            DadosClinicosBasicos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

            if (request.getPaciente() != null && !request.getPaciente().equals(entity.getPaciente().getId())) {
                repository.findByPacienteId(request.getPaciente())
                        .ifPresent(d -> {
                            if (!d.getId().equals(id)) {
                                log.warn("Tentativa de atualizar dados clínicos básicos com paciente duplicado. ID: {}, Paciente ID: {}", id, request.getPaciente());
                                throw new ConflictException("Dados clínicos básicos já existem para este paciente");
                            }
                        });
            }

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            mapper.updateFromRequest(request, entity);
            
            DadosClinicosBasicos updated = repository.save(entity);
            log.info("Dados clínicos básicos atualizados. ID: {}", updated.getId());

            return mapper.toResponse(updated);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar dados clínicos básicos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao atualizar dados clínicos básicos. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar dados clínicos básicos. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar dados clínicos básicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar dados clínicos básicos. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "dadosclinicosbasicos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo dados clínicos básicos. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }

        try {
            DadosClinicosBasicos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

            if (Boolean.FALSE.equals(entity.getActive())) {
                log.warn("Tentativa de excluir dados clínicos básicos já inativos. ID: {}", id);
                throw new BadRequestException("Dados clínicos básicos já estão inativos");
            }

            entity.setActive(false);
            repository.save(entity);
            log.info("Dados clínicos básicos excluídos. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir dados clínicos básicos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir dados clínicos básicos. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir dados clínicos básicos. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir dados clínicos básicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir dados clínicos básicos. ID: {}", id, e);
            throw e;
        }
    }

    // Método removido - agora usa mapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
}

