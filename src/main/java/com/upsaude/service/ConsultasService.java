package com.upsaude.service;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.api.response.ConsultasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConsultasService {

    ConsultasResponse criar(ConsultasRequest request);

    ConsultasResponse obterPorId(UUID id);

    Page<ConsultasResponse> listar(Pageable pageable);

    Page<ConsultasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ConsultasResponse atualizar(UUID id, ConsultasRequest request);

    void excluir(UUID id);
}
