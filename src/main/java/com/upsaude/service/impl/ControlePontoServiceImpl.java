package com.upsaude.service.impl;

import com.upsaude.api.request.ControlePontoRequest;
import com.upsaude.api.response.ControlePontoResponse;
import com.upsaude.entity.ControlePonto;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ControlePontoMapper;
import com.upsaude.repository.ControlePontoRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.ControlePontoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de ControlePonto.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ControlePontoServiceImpl implements ControlePontoService {

    private final ControlePontoRepository controlePontoRepository;
    private final ControlePontoMapper controlePontoMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final MedicosRepository medicosRepository;

    @Override
    @Transactional
    @CacheEvict(value = "controleponto", allEntries = true)
    public ControlePontoResponse criar(ControlePontoRequest request) {
        log.debug("Criando novo registro de ponto. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar registro de ponto com request nulo");
            throw new BadRequestException("Dados do registro de ponto são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            ControlePonto controlePonto = controlePontoMapper.fromRequest(request);

            // Carrega e define o profissional (obrigatório se médico não for fornecido)
            if (request.getProfissional() != null) {
                ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                        .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissional()));
                controlePonto.setProfissional(profissional);
            }

            // Carrega e define o médico (opcional)
            if (request.getMedico() != null) {
                Medicos medico = medicosRepository.findById(request.getMedico())
                        .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
                controlePonto.setMedico(medico);
            }

            controlePonto.setActive(true);

            ControlePonto controlePontoSalvo = controlePontoRepository.save(controlePonto);
            log.info("Registro de ponto criado com sucesso. ID: {}", controlePontoSalvo.getId());

            return controlePontoMapper.toResponse(controlePontoSalvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar registro de ponto. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar registro de ponto. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir registro de ponto", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar registro de ponto. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "controleponto", key = "#id")
    public ControlePontoResponse obterPorId(UUID id) {
        log.debug("Buscando registro de ponto por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de registro de ponto");
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }

        try {
            ControlePonto controlePonto = controlePontoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Registro de ponto não encontrado com ID: " + id));

            log.debug("Registro de ponto encontrado. ID: {}", id);
            return controlePontoMapper.toResponse(controlePonto);
        } catch (NotFoundException e) {
            log.warn("Registro de ponto não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar registro de ponto. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar registro de ponto", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar registro de ponto. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listar(Pageable pageable) {
        log.debug("Listando registros de ponto paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<ControlePonto> registros = controlePontoRepository.findAll(pageable);
            log.debug("Listagem de registros de ponto concluída. Total de elementos: {}", registros.getTotalElements());
            return registros.map(controlePontoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar registros de ponto. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar registros de ponto", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar registros de ponto. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando registros de ponto do profissional: {}. Página: {}, Tamanho: {}",
                profissionalId, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de registros de ponto");
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        try {
            Page<ControlePonto> registros = controlePontoRepository.findByProfissionalIdOrderByDataHoraDesc(profissionalId, pageable);
            log.debug("Listagem de registros de ponto do profissional concluída. Profissional ID: {}, Total: {}", profissionalId, registros.getTotalElements());
            return registros.map(controlePontoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar registros de ponto por profissional. Profissional ID: {}, Pageable: {}", profissionalId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar registros de ponto do profissional", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar registros de ponto por profissional. Profissional ID: {}, Pageable: {}", profissionalId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorMedico(UUID medicoId, Pageable pageable) {
        log.debug("Listando registros de ponto do médico: {}. Página: {}, Tamanho: {}",
                medicoId, pageable.getPageNumber(), pageable.getPageSize());

        if (medicoId == null) {
            log.warn("ID de médico nulo recebido para listagem de registros de ponto");
            throw new BadRequestException("ID do médico é obrigatório");
        }

        try {
            Page<ControlePonto> registros = controlePontoRepository.findByMedicoIdOrderByDataHoraDesc(medicoId, pageable);
            log.debug("Listagem de registros de ponto do médico concluída. Médico ID: {}, Total: {}", medicoId, registros.getTotalElements());
            return registros.map(controlePontoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar registros de ponto por médico. Médico ID: {}, Pageable: {}", medicoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar registros de ponto do médico", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar registros de ponto por médico. Médico ID: {}, Pageable: {}", medicoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando registros de ponto do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de registros de ponto");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Page<ControlePonto> registros = controlePontoRepository.findByEstabelecimentoIdOrderByDataHoraDesc(estabelecimentoId, pageable);
            log.debug("Listagem de registros de ponto do estabelecimento concluída. Estabelecimento ID: {}, Total: {}", estabelecimentoId, registros.getTotalElements());
            return registros.map(controlePontoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar registros de ponto por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar registros de ponto do estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar registros de ponto por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorProfissionalEData(UUID profissionalId, LocalDate data, Pageable pageable) {
        log.debug("Listando registros de ponto do profissional: {} na data: {}. Página: {}, Tamanho: {}",
                profissionalId, data, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de registros de ponto por data");
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (data == null) {
            log.warn("Data nula recebida para listagem de registros de ponto por profissional e data");
            throw new BadRequestException("Data é obrigatória");
        }

        try {
            Page<ControlePonto> registros = controlePontoRepository.findByProfissionalIdAndDataPontoOrderByDataHoraAsc(profissionalId, data, pageable);
            log.debug("Listagem de registros de ponto do profissional por data concluída. Profissional ID: {}, Data: {}, Total: {}", profissionalId, data, registros.getTotalElements());
            return registros.map(controlePontoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar registros de ponto por profissional e data. Profissional ID: {}, Data: {}, Pageable: {}", profissionalId, data, pageable, e);
            throw new InternalServerErrorException("Erro ao listar registros de ponto do profissional por data", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar registros de ponto por profissional e data. Profissional ID: {}, Data: {}, Pageable: {}", profissionalId, data, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ControlePontoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        log.debug("Listando registros de ponto do profissional: {} no período: {} a {}. Página: {}, Tamanho: {}",
                profissionalId, dataInicio, dataFim, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de registros de ponto por período");
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            log.warn("Data de início ou fim nula recebida para listagem de registros de ponto por período");
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }

        try {
            Page<ControlePonto> registros = controlePontoRepository.findByProfissionalIdAndDataPontoBetweenOrderByDataHoraAsc(profissionalId, dataInicio, dataFim, pageable);
            log.debug("Listagem de registros de ponto do profissional por período concluída. Profissional ID: {}, Total: {}", profissionalId, registros.getTotalElements());
            return registros.map(controlePontoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar registros de ponto por profissional e período. Profissional ID: {}, Data início: {}, Data fim: {}, Pageable: {}", profissionalId, dataInicio, dataFim, pageable, e);
            throw new InternalServerErrorException("Erro ao listar registros de ponto do profissional por período", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar registros de ponto por profissional e período. Profissional ID: {}, Data início: {}, Data fim: {}, Pageable: {}", profissionalId, dataInicio, dataFim, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "controleponto", key = "#id")
    public ControlePontoResponse atualizar(UUID id, ControlePontoRequest request) {
        log.debug("Atualizando registro de ponto. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de registro de ponto");
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de registro de ponto. ID: {}", id);
            throw new BadRequestException("Dados do registro de ponto são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            ControlePonto controlePontoExistente = controlePontoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Registro de ponto não encontrado com ID: " + id));

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            controlePontoMapper.updateFromRequest(request, controlePontoExistente);
            
            // Atualiza relacionamentos se fornecidos
            if (request.getProfissional() != null) {
                ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                        .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissional()));
                controlePontoExistente.setProfissional(profissional);
            }

            if (request.getMedico() != null) {
                Medicos medico = medicosRepository.findById(request.getMedico())
                        .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
                controlePontoExistente.setMedico(medico);
            }

            ControlePonto controlePontoAtualizado = controlePontoRepository.save(controlePontoExistente);
            log.info("Registro de ponto atualizado com sucesso. ID: {}", controlePontoAtualizado.getId());

            return controlePontoMapper.toResponse(controlePontoAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar registro de ponto não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar registro de ponto. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar registro de ponto. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar registro de ponto", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar registro de ponto. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "controleponto", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo registro de ponto. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de registro de ponto");
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }

        try {
            ControlePonto controlePonto = controlePontoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Registro de ponto não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(controlePonto.getActive())) {
                log.warn("Tentativa de excluir registro de ponto já inativo. ID: {}", id);
                throw new BadRequestException("Registro de ponto já está inativo");
            }

            controlePonto.setActive(false);
            controlePontoRepository.save(controlePonto);
            log.info("Registro de ponto excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir registro de ponto não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir registro de ponto. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir registro de ponto. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir registro de ponto", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir registro de ponto. ID: {}", id, e);
            throw e;
        }
    }

    private void validarDadosBasicos(ControlePontoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do registro de ponto são obrigatórios");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora do ponto são obrigatórias");
        }
        if (request.getDataPonto() == null) {
            throw new BadRequestException("Data do ponto é obrigatória");
        }
        if (request.getTipoPonto() == null) {
            throw new BadRequestException("Tipo de ponto é obrigatório");
        }
        if (request.getProfissional() == null && request.getMedico() == null) {
            throw new BadRequestException("ID do profissional ou médico é obrigatório");
        }
    }

    // Método removido - agora usa controlePontoMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
    // A lógica de atualização de relacionamentos foi movida para o método atualizar
}

