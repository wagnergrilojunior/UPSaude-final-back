package com.upsaude.service;

import com.upsaude.api.request.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.MedicacoesContinuasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a MedicacoesContinuasPaciente.
 *
 * @author UPSaúde
 */
public interface MedicacoesContinuasPacienteService {

    MedicacoesContinuasPacienteResponse criar(MedicacoesContinuasPacienteRequest request);

    MedicacoesContinuasPacienteResponse obterPorId(UUID id);

    Page<MedicacoesContinuasPacienteResponse> listar(Pageable pageable);

    MedicacoesContinuasPacienteResponse atualizar(UUID id, MedicacoesContinuasPacienteRequest request);

    void excluir(UUID id);
}
