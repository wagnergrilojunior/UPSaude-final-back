package com.upsaude.service.clinica.doencas;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upsaude.api.request.clinica.doencas.DoencasPacienteRequest;
import com.upsaude.api.request.clinica.doencas.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.response.clinica.doencas.DoencasPacienteResponse;

public interface DoencasPacienteService {

    DoencasPacienteResponse criar(DoencasPacienteRequest request);

    DoencasPacienteResponse criarSimplificado(DoencasPacienteSimplificadoRequest request);

    DoencasPacienteResponse obterPorId(UUID id);

    Page<DoencasPacienteResponse> listar(Pageable pageable);

    Page<DoencasPacienteResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    Page<DoencasPacienteResponse> listarPorDoenca(UUID doencaId, Pageable pageable);

    DoencasPacienteResponse atualizar(UUID id, DoencasPacienteRequest request);

    void excluir(UUID id);
}
