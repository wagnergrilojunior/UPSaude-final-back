package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.TituloReceberRequest;
import com.upsaude.api.response.financeiro.TituloReceberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TituloReceberService {

    TituloReceberResponse criar(TituloReceberRequest request);

    TituloReceberResponse obterPorId(UUID id);

    Page<TituloReceberResponse> listar(Pageable pageable);

    TituloReceberResponse atualizar(UUID id, TituloReceberRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

