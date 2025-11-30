package com.upsaude.service;

import com.upsaude.api.request.EducacaoSaudeRequest;
import com.upsaude.api.response.EducacaoSaudeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações relacionadas a Educação em Saúde.
 *
 * @author UPSaúde
 */
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

