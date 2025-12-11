package com.upsaude.service;

import com.upsaude.api.request.PlanejamentoFamiliarRequest;
import com.upsaude.api.response.PlanejamentoFamiliarResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PlanejamentoFamiliarService {

    PlanejamentoFamiliarResponse criar(PlanejamentoFamiliarRequest request);

    PlanejamentoFamiliarResponse obterPorId(UUID id);

    Page<PlanejamentoFamiliarResponse> listar(Pageable pageable);

    Page<PlanejamentoFamiliarResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    List<PlanejamentoFamiliarResponse> listarPorPaciente(UUID pacienteId);

    Page<PlanejamentoFamiliarResponse> listarAtivos(UUID estabelecimentoId, Pageable pageable);

    PlanejamentoFamiliarResponse atualizar(UUID id, PlanejamentoFamiliarRequest request);

    void excluir(UUID id);
}
