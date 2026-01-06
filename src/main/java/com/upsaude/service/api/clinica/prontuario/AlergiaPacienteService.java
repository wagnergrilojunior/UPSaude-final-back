package com.upsaude.service.api.clinica.prontuario;

import com.upsaude.api.request.clinica.prontuario.AlergiaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.AlergiaPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AlergiaPacienteService {

    AlergiaPacienteResponse criar(AlergiaPacienteRequest request);

    AlergiaPacienteResponse obterPorId(UUID id);

    Page<AlergiaPacienteResponse> listar(Pageable pageable);

    Page<AlergiaPacienteResponse> listarPorProntuario(UUID prontuarioId, Pageable pageable);

    AlergiaPacienteResponse atualizar(UUID id, AlergiaPacienteRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

