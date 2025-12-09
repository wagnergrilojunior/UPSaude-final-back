package com.upsaude.service.impl;

import com.upsaude.api.request.DadosSociodemograficosRequest;
import com.upsaude.api.response.DadosSociodemograficosResponse;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.entity.DadosSociodemograficos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DadosSociodemograficosMapper;
import com.upsaude.repository.DadosSociodemograficosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.service.DadosSociodemograficosService;
import com.upsaude.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosSociodemograficosServiceImpl implements DadosSociodemograficosService {

    private final DadosSociodemograficosRepository repository;
    private final DadosSociodemograficosMapper mapper;
    private final PacienteRepository pacienteRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    @CacheEvict(value = "dadossociodemograficos", allEntries = true)
    public DadosSociodemograficosResponse criar(DadosSociodemograficosRequest request) {
        log.debug("Criando dados sociodemográficos para paciente: {}", request.getPaciente());

        if (request == null) {
            log.warn("Tentativa de criar dados sociodemográficos com request nulo");
            throw new BadRequestException("Dados sociodemográficos são obrigatórios");
        }
        if (request.getPaciente() == null) {
            log.warn("ID de paciente nulo recebido para criação de dados sociodemográficos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            repository.findByPacienteId(request.getPaciente())
                    .ifPresent(d -> {
                        log.warn("Tentativa de criar dados sociodemográficos duplicados para paciente. Paciente ID: {}", request.getPaciente());
                        throw new ConflictException("Dados sociodemográficos já existem para este paciente");
                    });

            // Carrega o paciente para definir o relacionamento
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));

            DadosSociodemograficos entity = mapper.fromRequest(request);
            entity.setActive(true);
            entity.setPaciente(paciente); // Define o relacionamento manualmente
            
            // Obtém o tenant do usuário autenticado (obrigatório para DadosSociodemograficos que estende BaseEntity)
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar dados sociodemográficos.");
            }
            entity.setTenant(tenant);

            DadosSociodemograficos saved = repository.save(entity);
            log.info("Dados sociodemográficos criados. ID: {}", saved.getId());

            DadosSociodemograficosResponse response = mapper.toResponse(saved);
            // Mapeia apenas o ID do paciente para evitar referência circular
            response.setPaciente(createPacienteResponseMinimal(saved.getPaciente()));
            return response;
        } catch (BadRequestException | ConflictException | NotFoundException e) {
            log.warn("Erro de validação ao criar dados sociodemográficos. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar dados sociodemográficos. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao persistir dados sociodemográficos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar dados sociodemográficos. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DadosSociodemograficosResponse obterPorId(UUID id) {
        log.debug("Buscando dados sociodemográficos por ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de dados sociodemográficos");
            throw new BadRequestException("ID é obrigatório");
        }

        try {
            DadosSociodemograficos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados com ID: " + id));

            log.debug("Dados sociodemográficos encontrados. ID: {}", id);
            DadosSociodemograficosResponse response = mapper.toResponse(entity);
            // Mapeia apenas o ID do paciente para evitar referência circular
            response.setPaciente(createPacienteResponseMinimal(entity.getPaciente()));
            return response;
        } catch (NotFoundException e) {
            log.warn("Dados sociodemográficos não encontrados. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar dados sociodemográficos. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao buscar dados sociodemográficos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar dados sociodemográficos. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DadosSociodemograficosResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando dados sociodemográficos por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para busca de dados sociodemográficos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            DadosSociodemograficos entity = repository.findByPacienteId(pacienteId)
                    .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados para o paciente: " + pacienteId));

            log.debug("Dados sociodemográficos encontrados para paciente. Paciente ID: {}", pacienteId);
            DadosSociodemograficosResponse response = mapper.toResponse(entity);
            // Mapeia apenas o ID do paciente para evitar referência circular
            response.setPaciente(createPacienteResponseMinimal(entity.getPaciente()));
            return response;
        } catch (NotFoundException e) {
            log.warn("Dados sociodemográficos não encontrados para paciente. Paciente ID: {}", pacienteId);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar dados sociodemográficos por paciente. Paciente ID: {}, Exception: {}", pacienteId, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao buscar dados sociodemográficos do paciente", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar dados sociodemográficos por paciente. Paciente ID: {}, Exception: {}", pacienteId, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosSociodemograficosResponse> listar(Pageable pageable) {
        log.debug("Listando dados sociodemográficos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<DadosSociodemograficos> entities = repository.findAll(pageable);
            log.debug("Listagem de dados sociodemográficos concluída. Total de elementos: {}", entities.getTotalElements());
            return entities.map(entity -> {
                DadosSociodemograficosResponse response = mapper.toResponse(entity);
                // Mapeia apenas o ID do paciente para evitar referência circular
                response.setPaciente(createPacienteResponseMinimal(entity.getPaciente()));
                return response;
            });
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar dados sociodemográficos. Pageable: {}, Exception: {}", pageable, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao listar dados sociodemográficos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao listar dados sociodemográficos. Pageable: {}, Exception: {}", pageable, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "dadossociodemograficos", key = "#id")
    public DadosSociodemograficosResponse atualizar(UUID id, DadosSociodemograficosRequest request) {
        log.debug("Atualizando dados sociodemográficos. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de dados sociodemográficos");
            throw new BadRequestException("ID é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de dados sociodemográficos. ID: {}", id);
            throw new BadRequestException("Dados sociodemográficos são obrigatórios");
        }

        try {
            DadosSociodemograficos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados com ID: " + id));

            if (request.getPaciente() != null && !request.getPaciente().equals(entity.getPaciente().getId())) {
                repository.findByPacienteId(request.getPaciente())
                        .ifPresent(d -> {
                            if (!d.getId().equals(id)) {
                                log.warn("Tentativa de atualizar dados sociodemográficos com paciente duplicado. ID: {}, Paciente ID: {}", id, request.getPaciente());
                                throw new ConflictException("Dados sociodemográficos já existem para este paciente");
                            }
                        });
                
                // Carrega o novo paciente para atualizar o relacionamento
                Paciente paciente = pacienteRepository.findById(request.getPaciente())
                        .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
                entity.setPaciente(paciente);
            }

            atualizarDados(entity, request);
            DadosSociodemograficos updated = repository.save(entity);
            log.info("Dados sociodemográficos atualizados. ID: {}", updated.getId());

            DadosSociodemograficosResponse response = mapper.toResponse(updated);
            // Mapeia apenas o ID do paciente para evitar referência circular
            response.setPaciente(createPacienteResponseMinimal(updated.getPaciente()));
            return response;
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar dados sociodemográficos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao atualizar dados sociodemográficos. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar dados sociodemográficos. ID: {}, Request: {}, Exception: {}", id, request, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao atualizar dados sociodemográficos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar dados sociodemográficos. ID: {}, Request: {}, Exception: {}", id, request, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "dadossociodemograficos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo dados sociodemográficos. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de dados sociodemográficos");
            throw new BadRequestException("ID é obrigatório");
        }

        try {
            DadosSociodemograficos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados com ID: " + id));

            if (Boolean.FALSE.equals(entity.getActive())) {
                log.warn("Tentativa de excluir dados sociodemográficos já inativos. ID: {}", id);
                throw new BadRequestException("Dados sociodemográficos já estão inativos");
            }

            entity.setActive(false);
            repository.save(entity);
            log.info("Dados sociodemográficos excluídos. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir dados sociodemográficos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir dados sociodemográficos. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir dados sociodemográficos. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao excluir dados sociodemográficos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao excluir dados sociodemográficos. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw e;
        }
    }

    private void atualizarDados(DadosSociodemograficos entity, DadosSociodemograficosRequest request) {
        DadosSociodemograficos updated = mapper.fromRequest(request);
        
        entity.setRacaCor(updated.getRacaCor());
        entity.setNacionalidade(updated.getNacionalidade());
        entity.setPaisNascimento(updated.getPaisNascimento());
        entity.setNaturalidade(updated.getNaturalidade());
        entity.setMunicipioNascimentoIbge(updated.getMunicipioNascimentoIbge());
        entity.setEscolaridade(updated.getEscolaridade());
        entity.setOcupacaoProfissao(updated.getOcupacaoProfissao());
        entity.setSituacaoRua(updated.getSituacaoRua());
        entity.setTempoSituacaoRua(updated.getTempoSituacaoRua());
        entity.setCondicaoMoradia(updated.getCondicaoMoradia());
        entity.setSituacaoFamiliar(updated.getSituacaoFamiliar());
    }

    /**
     * Cria um PacienteResponse mínimo apenas com o ID para evitar referência circular.
     * 
     * @param paciente Entidade Paciente
     * @return PacienteResponse com apenas o ID preenchido
     */
    private PacienteResponse createPacienteResponseMinimal(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        PacienteResponse response = new PacienteResponse();
        response.setId(paciente.getId());
        return response;
    }
}

