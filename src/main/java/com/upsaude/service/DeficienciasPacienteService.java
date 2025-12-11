package com.upsaude.service;

import com.upsaude.api.request.DeficienciasPacienteRequest;
import com.upsaude.api.request.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.response.DeficienciasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DeficienciasPacienteService {

    DeficienciasPacienteResponse criar(DeficienciasPacienteRequest request);

    DeficienciasPacienteResponse criarSimplificado(DeficienciasPacienteSimplificadoRequest request);

    DeficienciasPacienteResponse obterPorId(UUID id);

    Page<DeficienciasPacienteResponse> listar(Pageable pageable);

    DeficienciasPacienteResponse atualizar(UUID id, DeficienciasPacienteRequest request);

    void excluir(UUID id);
}
