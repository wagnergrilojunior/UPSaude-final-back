package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.RenegociacaoReceberRequest;
import com.upsaude.api.response.financeiro.RenegociacaoReceberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RenegociacaoReceberService {

    RenegociacaoReceberResponse criar(RenegociacaoReceberRequest request);

    RenegociacaoReceberResponse obterPorId(UUID id);

    Page<RenegociacaoReceberResponse> listar(Pageable pageable);

    RenegociacaoReceberResponse atualizar(UUID id, RenegociacaoReceberRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

