package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.ParteFinanceiraRequest;
import com.upsaude.api.response.financeiro.ParteFinanceiraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ParteFinanceiraService {

    ParteFinanceiraResponse criar(ParteFinanceiraRequest request);

    ParteFinanceiraResponse obterPorId(UUID id);

    Page<ParteFinanceiraResponse> listar(Pageable pageable);

    ParteFinanceiraResponse atualizar(UUID id, ParteFinanceiraRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

