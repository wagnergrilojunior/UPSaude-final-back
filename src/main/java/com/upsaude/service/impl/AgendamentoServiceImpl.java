package com.upsaude.service.impl;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.api.response.AgendamentoResponse;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AgendamentoMapper;
import com.upsaude.repository.AgendamentoRepository;
import com.upsaude.repository.ConvenioRepository;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final AgendamentoMapper agendamentoMapper;
    private final PacienteRepository pacienteRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final MedicosRepository medicosRepository;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final ConvenioRepository convenioRepository;

    @Override
    @Transactional
    @CacheEvict(value = "agendamento", allEntries = true)
    public AgendamentoResponse criar(AgendamentoRequest request) {
        log.debug("Criando novo agendamento. Request: {}", request);

        if (request == null) {
            log.warn("Tentativa de criar agendamento com request nulo");
            throw new BadRequestException("Dados do agendamento são obrigatórios");
        }

        try {

            verificarConflitosHorario(request);

            Agendamento agendamento = agendamentoMapper.fromRequest(request);

            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            agendamento.setPaciente(paciente);

            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                    .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + request.getProfissional()));
            agendamento.setProfissional(profissional);

            if (profissional.getEstabelecimento() != null) {
                agendamento.setEstabelecimento(profissional.getEstabelecimento());
            }

            if (profissional.getTenant() != null) {
                agendamento.setTenant(profissional.getTenant());
            }

            if (request.getMedico() != null) {
                Medicos medico = medicosRepository.findById(request.getMedico())
                        .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
                agendamento.setMedico(medico);
            }

            if (request.getEspecialidade() != null) {
                EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidade())
                        .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidade()));
                agendamento.setEspecialidade(especialidade);
            }

            if (request.getConvenio() != null) {
                Convenio convenio = convenioRepository.findById(request.getConvenio())
                        .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
                agendamento.setConvenio(convenio);
            }

            if (request.getAgendamentoOriginal() != null) {
                Agendamento agendamentoOriginal = agendamentoRepository.findById(request.getAgendamentoOriginal())
                        .orElseThrow(() -> new NotFoundException("Agendamento original não encontrado com ID: " + request.getAgendamentoOriginal()));
                agendamento.setAgendamentoOriginal(agendamentoOriginal);
            }

            agendamento.setActive(true);
            agendamento.setStatus(StatusAgendamentoEnum.AGENDADO);

            Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
            log.info("Agendamento criado com sucesso. ID: {}", agendamentoSalvo.getId());

            return agendamentoMapper.toResponse(agendamentoSalvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar agendamento. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar agendamento. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar agendamento. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "agendamento", key = "#id")
    public AgendamentoResponse obterPorId(UUID id) {
        log.debug("Buscando agendamento por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de agendamento");
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        try {
            Agendamento agendamento = agendamentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

            log.debug("Agendamento encontrado. ID: {}", id);
            return agendamentoMapper.toResponse(agendamento);
        } catch (NotFoundException e) {
            log.warn("Agendamento não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar agendamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listar(Pageable pageable) {
        log.debug("Listando agendamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findAll(pageable);
            log.debug("Listagem de agendamentos concluída. Total de elementos: {}", agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando agendamentos do paciente: {}. Página: {}, Tamanho: {}",
                pacienteId, pageable.getPageNumber(), pageable.getPageSize());

        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para listagem de agendamentos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findByPacienteIdOrderByDataHoraDesc(pacienteId, pageable);
            log.debug("Listagem de agendamentos do paciente concluída. Paciente ID: {}, Total: {}", pacienteId, agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos por paciente. Paciente ID: {}, Pageable: {}", pacienteId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos do paciente", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos por paciente. Paciente ID: {}, Pageable: {}", pacienteId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando agendamentos do profissional: {}. Página: {}, Tamanho: {}",
                profissionalId, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de agendamentos");
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findByProfissionalIdOrderByDataHoraAsc(profissionalId, pageable);
            log.debug("Listagem de agendamentos do profissional concluída. Profissional ID: {}, Total: {}", profissionalId, agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos por profissional. Profissional ID: {}, Pageable: {}", profissionalId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos do profissional", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos por profissional. Profissional ID: {}, Pageable: {}", profissionalId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorMedico(UUID medicoId, Pageable pageable) {
        log.debug("Listando agendamentos do médico: {}. Página: {}, Tamanho: {}",
                medicoId, pageable.getPageNumber(), pageable.getPageSize());

        if (medicoId == null) {
            log.warn("ID de médico nulo recebido para listagem de agendamentos");
            throw new BadRequestException("ID do médico é obrigatório");
        }

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findByMedicoIdOrderByDataHoraAsc(medicoId, pageable);
            log.debug("Listagem de agendamentos do médico concluída. Médico ID: {}, Total: {}", medicoId, agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos por médico. Médico ID: {}, Pageable: {}", medicoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos do médico", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos por médico. Médico ID: {}, Pageable: {}", medicoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando agendamentos do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de agendamentos");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findByEstabelecimentoIdOrderByDataHoraAsc(estabelecimentoId, pageable);
            log.debug("Listagem de agendamentos do estabelecimento concluída. Estabelecimento ID: {}, Total: {}", estabelecimentoId, agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos do estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorStatus(StatusAgendamentoEnum status, Pageable pageable) {
        log.debug("Listando agendamentos por status: {}. Página: {}, Tamanho: {}",
                status, pageable.getPageNumber(), pageable.getPageSize());

        if (status == null) {
            log.warn("Status nulo recebido para listagem de agendamentos");
            throw new BadRequestException("Status é obrigatório");
        }

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findByStatusOrderByDataHoraAsc(status, pageable);
            log.debug("Listagem de agendamentos por status concluída. Status: {}, Total: {}", status, agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos por status. Status: {}, Pageable: {}", status, pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos por status", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos por status. Status: {}, Pageable: {}", status, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable) {
        log.debug("Listando agendamentos do profissional: {} no período: {} a {}. Página: {}, Tamanho: {}",
                profissionalId, dataInicio, dataFim, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de agendamentos por período");
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            log.warn("Data de início ou fim nula recebida para listagem de agendamentos por período");
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findByProfissionalIdAndDataHoraBetweenOrderByDataHoraAsc(
                    profissionalId, dataInicio, dataFim, pageable);
            log.debug("Listagem de agendamentos do profissional por período concluída. Profissional ID: {}, Total: {}", profissionalId, agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos por profissional e período. Profissional ID: {}, Data início: {}, Data fim: {}, Pageable: {}", profissionalId, dataInicio, dataFim, pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos do profissional por período", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos por profissional e período. Profissional ID: {}, Data início: {}, Data fim: {}, Pageable: {}", profissionalId, dataInicio, dataFim, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorEstabelecimentoEPeriodo(UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable) {
        log.debug("Listando agendamentos do estabelecimento: {} no período: {} a {}. Página: {}, Tamanho: {}",
                estabelecimentoId, dataInicio, dataFim, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de agendamentos por período");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            log.warn("Data de início ou fim nula recebida para listagem de agendamentos por período");
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findByEstabelecimentoIdAndDataHoraBetweenOrderByDataHoraAsc(
                    estabelecimentoId, dataInicio, dataFim, pageable);
            log.debug("Listagem de agendamentos do estabelecimento por período concluída. Estabelecimento ID: {}, Total: {}", estabelecimentoId, agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos por estabelecimento e período. Estabelecimento ID: {}, Data início: {}, Data fim: {}, Pageable: {}", estabelecimentoId, dataInicio, dataFim, pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos do estabelecimento por período", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos por estabelecimento e período. Estabelecimento ID: {}, Data início: {}, Data fim: {}, Pageable: {}", estabelecimentoId, dataInicio, dataFim, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorProfissionalEStatus(UUID profissionalId, StatusAgendamentoEnum status, Pageable pageable) {
        log.debug("Listando agendamentos do profissional: {} com status: {}. Página: {}, Tamanho: {}",
                profissionalId, status, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de agendamentos por status");
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (status == null) {
            log.warn("Status nulo recebido para listagem de agendamentos por profissional e status");
            throw new BadRequestException("Status é obrigatório");
        }

        try {
            Page<Agendamento> agendamentos = agendamentoRepository.findByProfissionalIdAndStatusOrderByDataHoraAsc(
                    profissionalId, status, pageable);
            log.debug("Listagem de agendamentos do profissional por status concluída. Profissional ID: {}, Status: {}, Total: {}", profissionalId, status, agendamentos.getTotalElements());
            return agendamentos.map(agendamentoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar agendamentos por profissional e status. Profissional ID: {}, Status: {}, Pageable: {}", profissionalId, status, pageable, e);
            throw new InternalServerErrorException("Erro ao listar agendamentos do profissional por status", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar agendamentos por profissional e status. Profissional ID: {}, Status: {}, Pageable: {}", profissionalId, status, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "agendamento", key = "#id")
    public AgendamentoResponse atualizar(UUID id, AgendamentoRequest request) {
        log.debug("Atualizando agendamento. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de agendamento");
            throw new BadRequestException("ID do agendamento é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de agendamento. ID: {}", id);
            throw new BadRequestException("Dados do agendamento são obrigatórios");
        }

        try {

            Agendamento agendamentoExistente = agendamentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

            atualizarDadosAgendamento(agendamentoExistente, request);

            Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamentoExistente);
            log.info("Agendamento atualizado com sucesso. ID: {}", agendamentoAtualizado.getId());

            return agendamentoMapper.toResponse(agendamentoAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar agendamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar agendamento. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar agendamento. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar agendamento. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public AgendamentoResponse cancelar(UUID id, String motivoCancelamento) {
        log.debug("Cancelando agendamento. ID: {}, Motivo: {}", id, motivoCancelamento);

        if (id == null) {
            log.warn("ID nulo recebido para cancelamento de agendamento");
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        try {
            Agendamento agendamento = agendamentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

            if (agendamento.getStatus() == StatusAgendamentoEnum.CANCELADO) {
                log.warn("Tentativa de cancelar agendamento já cancelado. ID: {}", id);
                throw new BadRequestException("Agendamento já está cancelado");
            }

            agendamento.setStatus(StatusAgendamentoEnum.CANCELADO);
            agendamento.setDataCancelamento(OffsetDateTime.now());
            agendamento.setMotivoCancelamento(motivoCancelamento);

            Agendamento agendamentoCancelado = agendamentoRepository.save(agendamento);
            log.info("Agendamento cancelado com sucesso. ID: {}", agendamentoCancelado.getId());

            return agendamentoMapper.toResponse(agendamentoCancelado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de cancelar agendamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao cancelar agendamento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao cancelar agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao cancelar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao cancelar agendamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public AgendamentoResponse confirmar(UUID id) {
        log.debug("Confirmando agendamento. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para confirmação de agendamento");
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        try {
            Agendamento agendamento = agendamentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

            agendamento.setStatus(StatusAgendamentoEnum.CONFIRMADO);
            agendamento.setDataConfirmacao(OffsetDateTime.now());

            Agendamento agendamentoConfirmado = agendamentoRepository.save(agendamento);
            log.info("Agendamento confirmado com sucesso. ID: {}", agendamentoConfirmado.getId());

            return agendamentoMapper.toResponse(agendamentoConfirmado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de confirmar agendamento não existente. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao confirmar agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao confirmar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao confirmar agendamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public AgendamentoResponse reagendar(UUID id, AgendamentoRequest novoAgendamentoRequest, String motivoReagendamento) {
        log.debug("Reagendando agendamento. ID: {}, Motivo: {}", id, motivoReagendamento);

        if (id == null) {
            log.warn("ID nulo recebido para reagendamento");
            throw new BadRequestException("ID do agendamento é obrigatório");
        }
        if (novoAgendamentoRequest == null) {
            log.warn("Request nulo recebido para reagendamento. ID: {}", id);
            throw new BadRequestException("Dados do novo agendamento são obrigatórios");
        }

        try {
            Agendamento agendamentoOriginal = agendamentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

            agendamentoOriginal.setStatus(StatusAgendamentoEnum.REAGENDADO);
            agendamentoOriginal.setDataReagendamento(OffsetDateTime.now());
            agendamentoOriginal.setMotivoReagendamento(motivoReagendamento);
            agendamentoRepository.save(agendamentoOriginal);

            novoAgendamentoRequest.setAgendamentoOriginal(id);
            novoAgendamentoRequest.setStatus(StatusAgendamentoEnum.AGENDADO);
            AgendamentoResponse novoAgendamento = criar(novoAgendamentoRequest);

            log.info("Agendamento reagendado com sucesso. ID original: {}, Novo ID: {}", id, novoAgendamento.getId());

            return novoAgendamento;
        } catch (NotFoundException e) {
            log.warn("Tentativa de reagendar agendamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao reagendar agendamento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao reagendar agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao reagendar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao reagendar agendamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "agendamento", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo agendamento. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de agendamento");
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        try {
            Agendamento agendamento = agendamentoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(agendamento.getActive())) {
                log.warn("Tentativa de excluir agendamento já inativo. ID: {}", id);
                throw new BadRequestException("Agendamento já está inativo");
            }

            agendamento.setActive(false);
            agendamentoRepository.save(agendamento);
            log.info("Agendamento excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir agendamento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir agendamento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir agendamento. ID: {}", id, e);
            throw e;
        }
    }

    private void verificarConflitosHorario(AgendamentoRequest request) {
        if (request.getProfissional() != null && request.getDataHora() != null && request.getDataHoraFim() != null) {

            java.util.List<Agendamento> conflitos = agendamentoRepository.findByProfissionalIdAndDataHoraBetween(
                    request.getProfissional(), request.getDataHora(), request.getDataHoraFim());

            if (!conflitos.isEmpty()) {
                log.warn("Conflito de horário detectado para o profissional: {}", request.getProfissional());

            }
        }
    }

    private void atualizarDadosAgendamento(Agendamento agendamento, AgendamentoRequest request) {
        if (request.getDataHora() != null) {
            agendamento.setDataHora(request.getDataHora());
        }
        if (request.getDataHoraFim() != null) {
            agendamento.setDataHoraFim(request.getDataHoraFim());
        }
        if (request.getDuracaoPrevistaMinutos() != null) {
            agendamento.setDuracaoPrevistaMinutos(request.getDuracaoPrevistaMinutos());
        }
        if (request.getStatus() != null) {
            agendamento.setStatus(request.getStatus());
        }
        if (request.getPrioridade() != null) {
            agendamento.setPrioridade(request.getPrioridade());
        }
        if (request.getEhEncaixe() != null) {
            agendamento.setEhEncaixe(request.getEhEncaixe());
        }
        if (request.getEhRetorno() != null) {
            agendamento.setEhRetorno(request.getEhRetorno());
        }
        if (request.getMotivoConsulta() != null) {
            agendamento.setMotivoConsulta(request.getMotivoConsulta());
        }
        if (request.getObservacoesAgendamento() != null) {
            agendamento.setObservacoesAgendamento(request.getObservacoesAgendamento());
        }
        if (request.getObservacoesInternas() != null) {
            agendamento.setObservacoesInternas(request.getObservacoesInternas());
        }

        if (request.getMedico() != null) {
            Medicos medico = medicosRepository.findById(request.getMedico())
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
            agendamento.setMedico(medico);
        }

        if (request.getEspecialidade() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidade())
                    .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidade()));
            agendamento.setEspecialidade(especialidade);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenio())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            agendamento.setConvenio(convenio);
        }

        agendamento.setDataUltimaAlteracao(OffsetDateTime.now());

    }
}
