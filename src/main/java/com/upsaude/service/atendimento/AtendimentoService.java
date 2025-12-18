package com.upsaude.service.atendimento;

import com.upsaude.api.request.atendimento.AtendimentoRequest;
import com.upsaude.api.response.atendimento.AtendimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AtendimentoService {

    AtendimentoResponse criar(AtendimentoRequest request);

    AtendimentoResponse obterPorId(UUID id);

    Page<AtendimentoResponse> listar(Pageable pageable);

    Page<AtendimentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    Page<AtendimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable);

    Page<AtendimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    AtendimentoResponse atualizar(UUID id, AtendimentoRequest request);

    void excluir(UUID id);
}
