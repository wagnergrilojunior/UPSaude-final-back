package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.LogFinanceiroRequest;
import com.upsaude.api.response.financeiro.LogFinanceiroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LogFinanceiroService {

    LogFinanceiroResponse criar(LogFinanceiroRequest request);

    LogFinanceiroResponse obterPorId(UUID id);

    Page<LogFinanceiroResponse> listar(Pageable pageable);

    LogFinanceiroResponse atualizar(UUID id, LogFinanceiroRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

