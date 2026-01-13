package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.MovimentacaoContaRequest;
import com.upsaude.api.response.financeiro.MovimentacaoContaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MovimentacaoContaService {

    MovimentacaoContaResponse criar(MovimentacaoContaRequest request);

    MovimentacaoContaResponse obterPorId(UUID id);

    Page<MovimentacaoContaResponse> listar(Pageable pageable);

    MovimentacaoContaResponse atualizar(UUID id, MovimentacaoContaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

