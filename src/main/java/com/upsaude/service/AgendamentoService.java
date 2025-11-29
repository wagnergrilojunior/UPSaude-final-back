package com.upsaude.service;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.api.response.AgendamentoResponse;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.enums.StatusAgendamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Agendamento.
 *
 * @author UPSaúde
 */
public interface AgendamentoService {

    AgendamentoResponse criar(AgendamentoRequest request);

    AgendamentoResponse obterPorId(UUID id);

    Page<AgendamentoResponse> listar(Pageable pageable);

    Page<AgendamentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    Page<AgendamentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable);

    Page<AgendamentoResponse> listarPorMedico(UUID medicoId, Pageable pageable);

    Page<AgendamentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    Page<AgendamentoResponse> listarPorStatus(StatusAgendamentoEnum status, Pageable pageable);

    Page<AgendamentoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<AgendamentoResponse> listarPorEstabelecimentoEPeriodo(UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<AgendamentoResponse> listarPorProfissionalEStatus(UUID profissionalId, StatusAgendamentoEnum status, Pageable pageable);

    AgendamentoResponse atualizar(UUID id, AgendamentoRequest request);

    AgendamentoResponse cancelar(UUID id, String motivoCancelamento);

    AgendamentoResponse confirmar(UUID id);

    AgendamentoResponse reagendar(UUID id, AgendamentoRequest novoAgendamentoRequest, String motivoReagendamento);

    void excluir(UUID id);
}

