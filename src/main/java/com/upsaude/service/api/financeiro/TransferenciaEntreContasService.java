package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.TransferenciaEntreContasRequest;
import com.upsaude.api.response.financeiro.TransferenciaEntreContasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransferenciaEntreContasService {

    TransferenciaEntreContasResponse criar(TransferenciaEntreContasRequest request);

    TransferenciaEntreContasResponse obterPorId(UUID id);

    Page<TransferenciaEntreContasResponse> listar(Pageable pageable);

    TransferenciaEntreContasResponse atualizar(UUID id, TransferenciaEntreContasRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

