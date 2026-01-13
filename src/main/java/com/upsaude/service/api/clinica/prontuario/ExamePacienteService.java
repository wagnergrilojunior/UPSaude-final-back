package com.upsaude.service.api.clinica.prontuario;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upsaude.api.request.clinica.prontuario.ExamePacienteRequest;
import com.upsaude.api.response.clinica.prontuario.ExamePacienteResponse;

public interface ExamePacienteService {

    ExamePacienteResponse criar(ExamePacienteRequest request);

    ExamePacienteResponse atualizar(UUID id, ExamePacienteRequest request);

    ExamePacienteResponse obterPorId(UUID id);

    Page<ExamePacienteResponse> listarPorProntuario(UUID prontuarioId, Pageable pageable);

    void excluir(UUID id);
}
