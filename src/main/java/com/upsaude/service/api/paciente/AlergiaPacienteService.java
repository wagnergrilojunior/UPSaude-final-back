package com.upsaude.service.api.paciente;

import com.upsaude.api.request.AlergiaPacienteRequest;
import com.upsaude.api.response.AlergiaPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AlergiaPacienteService {

    AlergiaPacienteResponse criar(UUID pacienteId, AlergiaPacienteRequest request);

    Page<AlergiaPacienteResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    AlergiaPacienteResponse obterPorId(UUID pacienteId, UUID alergiaId);

    AlergiaPacienteResponse atualizar(UUID pacienteId, UUID alergiaId, AlergiaPacienteRequest request);

    void excluir(UUID pacienteId, UUID alergiaId);
}

