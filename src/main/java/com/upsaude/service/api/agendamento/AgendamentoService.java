package com.upsaude.service.api.agendamento;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AgendamentoService {

    AgendamentoResponse criar(AgendamentoRequest request);

    AgendamentoResponse obterPorId(UUID id);

    Page<AgendamentoResponse> listar(Pageable pageable);

    AgendamentoResponse atualizar(UUID id, AgendamentoRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

