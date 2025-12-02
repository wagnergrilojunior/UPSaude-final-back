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
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AgendamentoMapper;
import com.upsaude.repository.AgendamentoRepository;
import com.upsaude.repository.ConvenioRepository;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.AgendamentoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Agendamento.
 *
 * @author UPSaúde
 */
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
        log.debug("Criando novo agendamento");

        validarDadosBasicos(request);
        verificarConflitosHorario(request);

        Agendamento agendamento = agendamentoMapper.fromRequest(request);

        // Carrega e define relacionamentos obrigatórios
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        agendamento.setPaciente(paciente);

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalId())
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + request.getProfissionalId()));
        agendamento.setProfissional(profissional);

        // Define estabelecimento do profissional (paciente não tem estabelecimento)
        if (profissional.getEstabelecimento() != null) {
            agendamento.setEstabelecimento(profissional.getEstabelecimento());
        }
        // Define tenant do profissional (paciente não tem tenant)
        if (profissional.getTenant() != null) {
            agendamento.setTenant(profissional.getTenant());
        }

        // Carrega relacionamentos opcionais
        if (request.getMedicoId() != null) {
            Medicos medico = medicosRepository.findById(request.getMedicoId())
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedicoId()));
            agendamento.setMedico(medico);
        }

        if (request.getEspecialidadeId() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidadeId())
                    .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidadeId()));
            agendamento.setEspecialidade(especialidade);
        }

        if (request.getConvenioId() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenioId())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenioId()));
            agendamento.setConvenio(convenio);
        }

        if (request.getAgendamentoOriginalId() != null) {
            Agendamento agendamentoOriginal = agendamentoRepository.findById(request.getAgendamentoOriginalId())
                    .orElseThrow(() -> new NotFoundException("Agendamento original não encontrado com ID: " + request.getAgendamentoOriginalId()));
            agendamento.setAgendamentoOriginal(agendamentoOriginal);
        }

        agendamento.setActive(true);
        agendamento.setStatus(StatusAgendamentoEnum.AGENDADO);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        log.info("Agendamento criado com sucesso. ID: {}", agendamentoSalvo.getId());

        return agendamentoMapper.toResponse(agendamentoSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "agendamento", key = "#id")
    public AgendamentoResponse obterPorId(UUID id) {
        log.debug("Buscando agendamento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

        return agendamentoMapper.toResponse(agendamento);
    }

    @Override
    public Page<AgendamentoResponse> listar(Pageable pageable) {
        log.debug("Listando agendamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Agendamento> agendamentos = agendamentoRepository.findAll(pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    public Page<AgendamentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando agendamentos do paciente: {}. Página: {}, Tamanho: {}",
                pacienteId, pageable.getPageNumber(), pageable.getPageSize());

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Page<Agendamento> agendamentos = agendamentoRepository.findByPacienteIdOrderByDataHoraDesc(pacienteId, pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    public Page<AgendamentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando agendamentos do profissional: {}. Página: {}, Tamanho: {}",
                profissionalId, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        Page<Agendamento> agendamentos = agendamentoRepository.findByProfissionalIdOrderByDataHoraAsc(profissionalId, pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    public Page<AgendamentoResponse> listarPorMedico(UUID medicoId, Pageable pageable) {
        log.debug("Listando agendamentos do médico: {}. Página: {}, Tamanho: {}",
                medicoId, pageable.getPageNumber(), pageable.getPageSize());

        if (medicoId == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }

        Page<Agendamento> agendamentos = agendamentoRepository.findByMedicoIdOrderByDataHoraAsc(medicoId, pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    public Page<AgendamentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando agendamentos do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<Agendamento> agendamentos = agendamentoRepository.findByEstabelecimentoIdOrderByDataHoraAsc(estabelecimentoId, pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    public Page<AgendamentoResponse> listarPorStatus(StatusAgendamentoEnum status, Pageable pageable) {
        log.debug("Listando agendamentos por status: {}. Página: {}, Tamanho: {}",
                status, pageable.getPageNumber(), pageable.getPageSize());

        if (status == null) {
            throw new BadRequestException("Status é obrigatório");
        }

        Page<Agendamento> agendamentos = agendamentoRepository.findByStatusOrderByDataHoraAsc(status, pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    public Page<AgendamentoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable) {
        log.debug("Listando agendamentos do profissional: {} no período: {} a {}. Página: {}, Tamanho: {}",
                profissionalId, dataInicio, dataFim, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }

        Page<Agendamento> agendamentos = agendamentoRepository.findByProfissionalIdAndDataHoraBetweenOrderByDataHoraAsc(
                profissionalId, dataInicio, dataFim, pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    public Page<AgendamentoResponse> listarPorEstabelecimentoEPeriodo(UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable) {
        log.debug("Listando agendamentos do estabelecimento: {} no período: {} a {}. Página: {}, Tamanho: {}",
                estabelecimentoId, dataInicio, dataFim, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }

        Page<Agendamento> agendamentos = agendamentoRepository.findByEstabelecimentoIdAndDataHoraBetweenOrderByDataHoraAsc(
                estabelecimentoId, dataInicio, dataFim, pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    public Page<AgendamentoResponse> listarPorProfissionalEStatus(UUID profissionalId, StatusAgendamentoEnum status, Pageable pageable) {
        log.debug("Listando agendamentos do profissional: {} com status: {}. Página: {}, Tamanho: {}",
                profissionalId, status, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (status == null) {
            throw new BadRequestException("Status é obrigatório");
        }

        Page<Agendamento> agendamentos = agendamentoRepository.findByProfissionalIdAndStatusOrderByDataHoraAsc(
                profissionalId, status, pageable);
        return agendamentos.map(agendamentoMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "agendamento", key = "#id")
    public AgendamentoResponse atualizar(UUID id, AgendamentoRequest request) {
        log.debug("Atualizando agendamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        validarDadosBasicos(request);

        Agendamento agendamentoExistente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

        atualizarDadosAgendamento(agendamentoExistente, request);

        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamentoExistente);
        log.info("Agendamento atualizado com sucesso. ID: {}", agendamentoAtualizado.getId());

        return agendamentoMapper.toResponse(agendamentoAtualizado);
    }

    @Override
    @Transactional
    public AgendamentoResponse cancelar(UUID id, String motivoCancelamento) {
        log.debug("Cancelando agendamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

        if (agendamento.getStatus() == StatusAgendamentoEnum.CANCELADO) {
            throw new BadRequestException("Agendamento já está cancelado");
        }

        agendamento.setStatus(StatusAgendamentoEnum.CANCELADO);
        agendamento.setDataCancelamento(OffsetDateTime.now());
        agendamento.setMotivoCancelamento(motivoCancelamento);
        // TODO: Obter ID do usuário logado
        // agendamento.setCanceladoPor(obterUsuarioLogadoId());

        Agendamento agendamentoCancelado = agendamentoRepository.save(agendamento);
        log.info("Agendamento cancelado com sucesso. ID: {}", agendamentoCancelado.getId());

        return agendamentoMapper.toResponse(agendamentoCancelado);
    }

    @Override
    @Transactional
    public AgendamentoResponse confirmar(UUID id) {
        log.debug("Confirmando agendamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

        agendamento.setStatus(StatusAgendamentoEnum.CONFIRMADO);
        agendamento.setDataConfirmacao(OffsetDateTime.now());
        // TODO: Obter ID do usuário logado
        // agendamento.setConfirmadoPor(obterUsuarioLogadoId());

        Agendamento agendamentoConfirmado = agendamentoRepository.save(agendamento);
        log.info("Agendamento confirmado com sucesso. ID: {}", agendamentoConfirmado.getId());

        return agendamentoMapper.toResponse(agendamentoConfirmado);
    }

    @Override
    @Transactional
    public AgendamentoResponse reagendar(UUID id, AgendamentoRequest novoAgendamentoRequest, String motivoReagendamento) {
        log.debug("Reagendando agendamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        Agendamento agendamentoOriginal = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

        // Cancela o agendamento original
        agendamentoOriginal.setStatus(StatusAgendamentoEnum.REAGENDADO);
        agendamentoOriginal.setDataReagendamento(OffsetDateTime.now());
        agendamentoOriginal.setMotivoReagendamento(motivoReagendamento);
        agendamentoRepository.save(agendamentoOriginal);

        // Cria novo agendamento
        novoAgendamentoRequest.setAgendamentoOriginalId(id);
        novoAgendamentoRequest.setStatus(StatusAgendamentoEnum.AGENDADO);
        AgendamentoResponse novoAgendamento = criar(novoAgendamentoRequest);

        log.info("Agendamento reagendado com sucesso. ID original: {}, Novo ID: {}", id, novoAgendamento.getId());

        return novoAgendamento;
    }

    @Override
    @Transactional
    @CacheEvict(value = "agendamento", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo agendamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(agendamento.getActive())) {
            throw new BadRequestException("Agendamento já está inativo");
        }

        agendamento.setActive(false);
        agendamentoRepository.save(agendamento);
        log.info("Agendamento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(AgendamentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do agendamento são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getProfissionalId() == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora do agendamento são obrigatórias");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status do agendamento é obrigatório");
        }
    }

    private void verificarConflitosHorario(AgendamentoRequest request) {
        if (request.getProfissionalId() != null && request.getDataHora() != null && request.getDataHoraFim() != null) {
            // Verifica se há conflito de horário
            java.util.List<Agendamento> conflitos = agendamentoRepository.findByProfissionalIdAndDataHoraBetween(
                    request.getProfissionalId(), request.getDataHora(), request.getDataHoraFim());

            if (!conflitos.isEmpty()) {
                log.warn("Conflito de horário detectado para o profissional: {}", request.getProfissionalId());
                // Apenas registra o conflito - a lógica de permitir ou não pode ser feita em outra camada
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

        // Atualiza relacionamentos se fornecidos
        if (request.getMedicoId() != null) {
            Medicos medico = medicosRepository.findById(request.getMedicoId())
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedicoId()));
            agendamento.setMedico(medico);
        }

        if (request.getEspecialidadeId() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidadeId())
                    .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidadeId()));
            agendamento.setEspecialidade(especialidade);
        }

        if (request.getConvenioId() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenioId())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenioId()));
            agendamento.setConvenio(convenio);
        }

        agendamento.setDataUltimaAlteracao(OffsetDateTime.now());
        // TODO: Obter ID do usuário logado
        // agendamento.setAlteradoPor(obterUsuarioLogadoId());
    }
}

