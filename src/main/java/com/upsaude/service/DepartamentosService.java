package com.upsaude.service;

import com.upsaude.api.request.DepartamentosRequest;
import com.upsaude.api.response.DepartamentosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Departamentos.
 *
 * @author UPSaúde
 */
public interface DepartamentosService {

    DepartamentosResponse criar(DepartamentosRequest request);

    DepartamentosResponse obterPorId(UUID id);

    Page<DepartamentosResponse> listar(Pageable pageable);

    DepartamentosResponse atualizar(UUID id, DepartamentosRequest request);

    void excluir(UUID id);
}
