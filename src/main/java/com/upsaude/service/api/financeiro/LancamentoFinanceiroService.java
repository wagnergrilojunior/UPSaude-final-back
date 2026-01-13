package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroRequest;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LancamentoFinanceiroService {

    LancamentoFinanceiroResponse criar(LancamentoFinanceiroRequest request);

    LancamentoFinanceiroResponse obterPorId(UUID id);

    Page<LancamentoFinanceiroResponse> listar(Pageable pageable);

    LancamentoFinanceiroResponse atualizar(UUID id, LancamentoFinanceiroRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

