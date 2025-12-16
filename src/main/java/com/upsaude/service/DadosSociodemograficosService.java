package com.upsaude.service;

import com.upsaude.api.request.DadosSociodemograficosRequest;
import com.upsaude.api.response.DadosSociodemograficosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DadosSociodemograficosService {

    DadosSociodemograficosResponse criar(DadosSociodemograficosRequest request);

    DadosSociodemograficosResponse obterPorId(UUID id);

    DadosSociodemograficosResponse obterPorPacienteId(UUID pacienteId);

    Page<DadosSociodemograficosResponse> listar(Pageable pageable);

    DadosSociodemograficosResponse atualizar(UUID id, DadosSociodemograficosRequest request);

    void excluir(UUID id);
}
