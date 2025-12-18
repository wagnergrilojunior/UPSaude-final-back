package com.upsaude.service.atendimento;

import com.upsaude.api.request.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.atendimento.CheckInAtendimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CheckInAtendimentoService {

    CheckInAtendimentoResponse criar(CheckInAtendimentoRequest request);

    CheckInAtendimentoResponse obterPorId(UUID id);

    Page<CheckInAtendimentoResponse> listar(Pageable pageable);

    CheckInAtendimentoResponse atualizar(UUID id, CheckInAtendimentoRequest request);

    void excluir(UUID id);
}
