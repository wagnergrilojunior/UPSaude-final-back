package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.CentroCustoRequest;
import com.upsaude.api.response.financeiro.CentroCustoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CentroCustoService {

    CentroCustoResponse criar(CentroCustoRequest request);

    CentroCustoResponse obterPorId(UUID id);

    Page<CentroCustoResponse> listar(Pageable pageable);

    CentroCustoResponse atualizar(UUID id, CentroCustoRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

