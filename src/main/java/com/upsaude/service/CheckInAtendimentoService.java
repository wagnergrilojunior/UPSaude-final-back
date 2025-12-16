package com.upsaude.service;

import com.upsaude.api.request.CheckInAtendimentoRequest;
import com.upsaude.api.response.CheckInAtendimentoResponse;
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
