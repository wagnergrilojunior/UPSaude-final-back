package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CompetenciaFinanceiraService {

    CompetenciaFinanceiraResponse criar(CompetenciaFinanceiraRequest request);

    CompetenciaFinanceiraResponse obterPorId(UUID id);

    Page<CompetenciaFinanceiraResponse> listar(Pageable pageable);

    CompetenciaFinanceiraResponse atualizar(UUID id, CompetenciaFinanceiraRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

