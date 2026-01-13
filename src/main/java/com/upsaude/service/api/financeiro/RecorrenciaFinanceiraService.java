package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.RecorrenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.RecorrenciaFinanceiraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RecorrenciaFinanceiraService {

    RecorrenciaFinanceiraResponse criar(RecorrenciaFinanceiraRequest request);

    RecorrenciaFinanceiraResponse obterPorId(UUID id);

    Page<RecorrenciaFinanceiraResponse> listar(Pageable pageable);

    RecorrenciaFinanceiraResponse atualizar(UUID id, RecorrenciaFinanceiraRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

