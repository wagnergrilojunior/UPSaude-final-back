package com.upsaude.service;

import com.upsaude.api.request.ControlePontoRequest;
import com.upsaude.api.response.ControlePontoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface ControlePontoService {

    ControlePontoResponse criar(ControlePontoRequest request);

    ControlePontoResponse obterPorId(UUID id);

    Page<ControlePontoResponse> listar(Pageable pageable);

    Page<ControlePontoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable);

    Page<ControlePontoResponse> listarPorMedico(UUID medicoId, Pageable pageable);

    Page<ControlePontoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    Page<ControlePontoResponse> listarPorProfissionalEData(UUID profissionalId, LocalDate data, Pageable pageable);

    Page<ControlePontoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    ControlePontoResponse atualizar(UUID id, ControlePontoRequest request);

    void excluir(UUID id);
}
