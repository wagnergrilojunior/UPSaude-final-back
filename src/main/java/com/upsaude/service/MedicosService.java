package com.upsaude.service;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Medicos.
 *
 * @author UPSaúde
 */
public interface MedicosService {

    MedicosResponse criar(MedicosRequest request);

    MedicosResponse obterPorId(UUID id);

    Page<MedicosResponse> listar(Pageable pageable);

    MedicosResponse atualizar(UUID id, MedicosRequest request);

    void excluir(UUID id);
}
