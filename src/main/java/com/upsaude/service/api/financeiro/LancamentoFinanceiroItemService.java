package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroItemRequest;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LancamentoFinanceiroItemService {

    LancamentoFinanceiroItemResponse criar(UUID lancamentoId, LancamentoFinanceiroItemRequest request);

    LancamentoFinanceiroItemResponse obterPorId(UUID id);

    Page<LancamentoFinanceiroItemResponse> listar(Pageable pageable);

    LancamentoFinanceiroItemResponse atualizar(UUID id, LancamentoFinanceiroItemRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

