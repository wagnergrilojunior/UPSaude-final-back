package com.upsaude.service;

import com.upsaude.api.request.MedicamentosRequest;
import com.upsaude.api.response.MedicamentosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Medicamentos.
 *
 * @author UPSaúde
 */
public interface MedicamentosService {

    MedicamentosResponse criar(MedicamentosRequest request);

    MedicamentosResponse obterPorId(UUID id);

    Page<MedicamentosResponse> listar(Pageable pageable);

    MedicamentosResponse atualizar(UUID id, MedicamentosRequest request);

    void excluir(UUID id);
}
