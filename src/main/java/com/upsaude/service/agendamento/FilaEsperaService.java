package com.upsaude.service.agendamento;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.api.response.agendamento.FilaEsperaResponse;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface FilaEsperaService {

    FilaEsperaResponse criar(FilaEsperaRequest request);

    FilaEsperaResponse obterPorId(UUID id);

    Page<FilaEsperaResponse> listar(Pageable pageable,
                                   UUID pacienteId,
                                   UUID profissionalId,
                                  UUID estabelecimentoId,
                                  PrioridadeAtendimentoEnum prioridade,
                                   OffsetDateTime dataInicio,
                                   OffsetDateTime dataFim);

    List<FilaEsperaResponse> listarPendentesSemAgendamento(UUID estabelecimentoId);

    FilaEsperaResponse atualizar(UUID id, FilaEsperaRequest request);

    void excluir(UUID id);
}

