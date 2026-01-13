package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.EstornoFinanceiroRequest;
import com.upsaude.api.response.financeiro.EstornoFinanceiroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EstornoFinanceiroService {

    EstornoFinanceiroResponse criar(EstornoFinanceiroRequest request);

    EstornoFinanceiroResponse obterPorId(UUID id);

    Page<EstornoFinanceiroResponse> listar(Pageable pageable);

    EstornoFinanceiroResponse atualizar(UUID id, EstornoFinanceiroRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

