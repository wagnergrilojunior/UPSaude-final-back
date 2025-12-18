package com.upsaude.service.doencas;

import com.upsaude.api.request.doencas.DoencasPacienteRequest;
import com.upsaude.api.request.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.response.doencas.DoencasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

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
