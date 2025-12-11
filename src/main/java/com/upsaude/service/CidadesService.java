package com.upsaude.service;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CidadesService {

    CidadesResponse criar(CidadesRequest request);

    CidadesResponse obterPorId(UUID id);

    Page<CidadesResponse> listar(Pageable pageable);

    Page<CidadesResponse> listarPorEstado(UUID estadoId, Pageable pageable);

    CidadesResponse atualizar(UUID id, CidadesRequest request);

    void excluir(UUID id);
}
