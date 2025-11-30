package com.upsaude.service;

import com.upsaude.api.request.PreNatalRequest;
import com.upsaude.api.response.PreNatalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interface de serviço para operações relacionadas a Pré-Natal.
 *
 * @author UPSaúde
 */
public interface PreNatalService {

    PreNatalResponse criar(PreNatalRequest request);

    PreNatalResponse obterPorId(UUID id);

    Page<PreNatalResponse> listar(Pageable pageable);

    Page<PreNatalResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    List<PreNatalResponse> listarPorPaciente(UUID pacienteId);

    Page<PreNatalResponse> listarEmAcompanhamento(UUID estabelecimentoId, Pageable pageable);

    PreNatalResponse atualizar(UUID id, PreNatalRequest request);

    void excluir(UUID id);
}

