package com.upsaude.service.referencia.geografico;

import com.upsaude.api.request.referencia.geografico.EstadosRequest;
import com.upsaude.api.response.referencia.geografico.EstadosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EstadosService {

    EstadosResponse criar(EstadosRequest request);

    EstadosResponse obterPorId(UUID id);

    Page<EstadosResponse> listar(Pageable pageable);

    EstadosResponse atualizar(UUID id, EstadosRequest request);

    void excluir(UUID id);
}
