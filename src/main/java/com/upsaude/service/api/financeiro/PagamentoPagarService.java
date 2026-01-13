package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.PagamentoPagarRequest;
import com.upsaude.api.response.financeiro.PagamentoPagarResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PagamentoPagarService {

    PagamentoPagarResponse criar(PagamentoPagarRequest request);

    PagamentoPagarResponse obterPorId(UUID id);

    Page<PagamentoPagarResponse> listar(Pageable pageable);

    PagamentoPagarResponse atualizar(UUID id, PagamentoPagarRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

