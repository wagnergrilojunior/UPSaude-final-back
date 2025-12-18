package com.upsaude.service.saude_publica.planejamento;

import com.upsaude.api.request.saude_publica.planejamento.PreNatalRequest;
import com.upsaude.api.response.saude_publica.planejamento.PreNatalResponse;
import com.upsaude.enums.StatusPreNatalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PreNatalService {

    PreNatalResponse criar(PreNatalRequest request);

    PreNatalResponse obterPorId(UUID id);

    Page<PreNatalResponse> listar(Pageable pageable);

    Page<PreNatalResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    List<PreNatalResponse> listarPorPaciente(UUID pacienteId);

    Page<PreNatalResponse> listarEmAcompanhamento(UUID estabelecimentoId, Pageable pageable);

    Page<PreNatalResponse> listarPorStatus(UUID estabelecimentoId, StatusPreNatalEnum status, Pageable pageable);

    PreNatalResponse atualizar(UUID id, PreNatalRequest request);

    void excluir(UUID id);
}
