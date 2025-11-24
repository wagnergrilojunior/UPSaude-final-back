package com.upsaude.service;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.api.response.ProcedimentosOdontologicosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a ProcedimentosOdontologicos.
 *
 * @author UPSaúde
 */
public interface ProcedimentosOdontologicosService {

    ProcedimentosOdontologicosResponse criar(ProcedimentosOdontologicosRequest request);

    ProcedimentosOdontologicosResponse obterPorId(UUID id);

    Page<ProcedimentosOdontologicosResponse> listar(Pageable pageable);

    ProcedimentosOdontologicosResponse atualizar(UUID id, ProcedimentosOdontologicosRequest request);

    void excluir(UUID id);
}
