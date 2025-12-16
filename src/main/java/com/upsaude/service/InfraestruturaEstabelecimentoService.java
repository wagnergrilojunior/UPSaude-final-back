package com.upsaude.service;

import com.upsaude.api.request.InfraestruturaEstabelecimentoRequest;
import com.upsaude.api.response.InfraestruturaEstabelecimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InfraestruturaEstabelecimentoService {

    InfraestruturaEstabelecimentoResponse criar(InfraestruturaEstabelecimentoRequest request);

    InfraestruturaEstabelecimentoResponse obterPorId(UUID id);

    Page<InfraestruturaEstabelecimentoResponse> listar(Pageable pageable);

    InfraestruturaEstabelecimentoResponse atualizar(UUID id, InfraestruturaEstabelecimentoRequest request);

    void excluir(UUID id);
}
