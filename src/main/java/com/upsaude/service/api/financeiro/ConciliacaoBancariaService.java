package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.ConciliacaoBancariaRequest;
import com.upsaude.api.response.financeiro.ConciliacaoBancariaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConciliacaoBancariaService {

    ConciliacaoBancariaResponse criar(ConciliacaoBancariaRequest request);

    ConciliacaoBancariaResponse obterPorId(UUID id);

    Page<ConciliacaoBancariaResponse> listar(Pageable pageable);

    ConciliacaoBancariaResponse atualizar(UUID id, ConciliacaoBancariaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

