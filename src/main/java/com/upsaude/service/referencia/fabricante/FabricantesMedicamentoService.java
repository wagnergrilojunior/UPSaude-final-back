package com.upsaude.service.referencia.fabricante;

import com.upsaude.api.request.referencia.fabricante.FabricantesMedicamentoRequest;
import com.upsaude.api.response.referencia.fabricante.FabricantesMedicamentoResponse;
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
