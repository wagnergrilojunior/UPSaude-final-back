package com.upsaude.service;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ExamesService {

    ExamesResponse criar(ExamesRequest request);

    ExamesResponse obterPorId(UUID id);

    Page<ExamesResponse> listar(Pageable pageable);

    Page<ExamesResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ExamesResponse atualizar(UUID id, ExamesRequest request);

    void excluir(UUID id);
}
