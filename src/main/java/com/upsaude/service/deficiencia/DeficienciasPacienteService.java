package com.upsaude.service.deficiencia;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upsaude.api.request.deficiencia.DeficienciasPacienteRequest;
import com.upsaude.api.request.deficiencia.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.response.deficiencia.DeficienciasPacienteResponse;

public interface DeficienciasPacienteService {

    DeficienciasPacienteResponse criar(DeficienciasPacienteRequest request);

    DeficienciasPacienteResponse criarSimplificado(DeficienciasPacienteSimplificadoRequest request);

    DeficienciasPacienteResponse obterPorId(UUID id);

    Page<DeficienciasPacienteResponse> listar(Pageable pageable);

    DeficienciasPacienteResponse atualizar(UUID id, DeficienciasPacienteRequest request);

    void excluir(UUID id);
}
