package com.upsaude.service.geografico;

import com.upsaude.api.request.geografico.EstadosRequest;
import com.upsaude.api.response.geografico.EstadosResponse;
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
