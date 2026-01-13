package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.ContaFinanceiraRequest;
import com.upsaude.api.response.financeiro.ContaFinanceiraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ContaFinanceiraService {

    ContaFinanceiraResponse criar(ContaFinanceiraRequest request);

    ContaFinanceiraResponse obterPorId(UUID id);

    Page<ContaFinanceiraResponse> listar(Pageable pageable);

    ContaFinanceiraResponse atualizar(UUID id, ContaFinanceiraRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

