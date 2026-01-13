package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.ReservaOrcamentariaAssistencialRequest;
import com.upsaude.api.response.financeiro.ReservaOrcamentariaAssistencialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReservaOrcamentariaAssistencialService {

    ReservaOrcamentariaAssistencialResponse criar(ReservaOrcamentariaAssistencialRequest request);

    ReservaOrcamentariaAssistencialResponse obterPorId(UUID id);

    Page<ReservaOrcamentariaAssistencialResponse> listar(Pageable pageable);

    ReservaOrcamentariaAssistencialResponse atualizar(UUID id, ReservaOrcamentariaAssistencialRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

