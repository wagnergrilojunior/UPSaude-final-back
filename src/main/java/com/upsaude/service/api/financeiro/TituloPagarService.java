package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.TituloPagarRequest;
import com.upsaude.api.response.financeiro.TituloPagarResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TituloPagarService {

    TituloPagarResponse criar(TituloPagarRequest request);

    TituloPagarResponse obterPorId(UUID id);

    Page<TituloPagarResponse> listar(Pageable pageable);

    TituloPagarResponse atualizar(UUID id, TituloPagarRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

