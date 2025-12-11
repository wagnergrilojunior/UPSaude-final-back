package com.upsaude.service;

import com.upsaude.api.request.FabricantesMedicamentoRequest;
import com.upsaude.api.response.FabricantesMedicamentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FabricantesMedicamentoService {

    FabricantesMedicamentoResponse criar(FabricantesMedicamentoRequest request);

    FabricantesMedicamentoResponse obterPorId(UUID id);

    Page<FabricantesMedicamentoResponse> listar(Pageable pageable);

    FabricantesMedicamentoResponse atualizar(UUID id, FabricantesMedicamentoRequest request);

    void excluir(UUID id);
}
