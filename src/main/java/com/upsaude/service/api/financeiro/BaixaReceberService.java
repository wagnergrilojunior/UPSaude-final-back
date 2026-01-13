package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.BaixaReceberRequest;
import com.upsaude.api.response.financeiro.BaixaReceberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BaixaReceberService {

    BaixaReceberResponse criar(BaixaReceberRequest request);

    BaixaReceberResponse obterPorId(UUID id);

    Page<BaixaReceberResponse> listar(Pageable pageable);

    BaixaReceberResponse atualizar(UUID id, BaixaReceberRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

