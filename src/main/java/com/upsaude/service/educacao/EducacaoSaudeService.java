package com.upsaude.service.educacao;

import com.upsaude.api.request.educacao.EducacaoSaudeRequest;
import com.upsaude.api.response.educacao.EducacaoSaudeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EducacaoSaudeService {

    EducacaoSaudeResponse criar(EducacaoSaudeRequest request);

    EducacaoSaudeResponse obterPorId(UUID id);

    Page<EducacaoSaudeResponse> listar(Pageable pageable);

    Page<EducacaoSaudeResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    Page<EducacaoSaudeResponse> listarPorProfissionalResponsavel(UUID profissionalId, Pageable pageable);

    Page<EducacaoSaudeResponse> listarRealizadas(UUID estabelecimentoId, Pageable pageable);

    EducacaoSaudeResponse atualizar(UUID id, EducacaoSaudeRequest request);

    void excluir(UUID id);
}
