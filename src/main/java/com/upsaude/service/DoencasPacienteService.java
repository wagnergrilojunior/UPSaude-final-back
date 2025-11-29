package com.upsaude.service;

import com.upsaude.api.request.DoencasPacienteRequest;
import com.upsaude.api.response.DoencasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a DoencasPaciente.
 *
 * @author UPSaúde
 */
public interface DoencasPacienteService {

    DoencasPacienteResponse criar(DoencasPacienteRequest request);

    DoencasPacienteResponse obterPorId(UUID id);

    Page<DoencasPacienteResponse> listar(Pageable pageable);

    Page<DoencasPacienteResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    Page<DoencasPacienteResponse> listarPorDoenca(UUID doencaId, Pageable pageable);

    DoencasPacienteResponse atualizar(UUID id, DoencasPacienteRequest request);

    void excluir(UUID id);
}

