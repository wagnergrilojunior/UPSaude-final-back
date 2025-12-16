package com.upsaude.service;

import com.upsaude.api.request.AcaoPromocaoPrevencaoRequest;
import com.upsaude.api.response.AcaoPromocaoPrevencaoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AcaoPromocaoPrevencaoService {

    AcaoPromocaoPrevencaoResponse criar(AcaoPromocaoPrevencaoRequest request);

    AcaoPromocaoPrevencaoResponse obterPorId(UUID id);

    Page<AcaoPromocaoPrevencaoResponse> listar(Pageable pageable);

    Page<AcaoPromocaoPrevencaoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencaoResponse> listarPorProfissionalResponsavel(UUID profissionalId, Pageable pageable);

    Page<AcaoPromocaoPrevencaoResponse> listarPorStatus(String status, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencaoResponse> listarContinuas(UUID estabelecimentoId, Pageable pageable);

    AcaoPromocaoPrevencaoResponse atualizar(UUID id, AcaoPromocaoPrevencaoRequest request);

    void excluir(UUID id);
}
