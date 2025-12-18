package com.upsaude.service.departamento;

import com.upsaude.api.request.departamento.DepartamentosRequest;
import com.upsaude.api.response.departamento.DepartamentosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DepartamentosService {

    DepartamentosResponse criar(DepartamentosRequest request);

    DepartamentosResponse obterPorId(UUID id);

    Page<DepartamentosResponse> listar(Pageable pageable);

    DepartamentosResponse atualizar(UUID id, DepartamentosRequest request);

    void excluir(UUID id);
}
