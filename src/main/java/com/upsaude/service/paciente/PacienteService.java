package com.upsaude.service.paciente;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.PacienteSimplificadoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PacienteService {

    PacienteResponse criar(PacienteRequest request);

    PacienteResponse obterPorId(UUID id);

    Page<PacienteResponse> listar(Pageable pageable);

    Page<PacienteSimplificadoResponse> listarSimplificado(Pageable pageable);

    PacienteResponse atualizar(UUID id, PacienteRequest request);

    void excluir(UUID id);

    void inativar(UUID id);

    void deletarPermanentemente(UUID id);
}
