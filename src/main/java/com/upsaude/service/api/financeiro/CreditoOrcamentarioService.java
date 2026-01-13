package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.CreditoOrcamentarioRequest;
import com.upsaude.api.response.financeiro.CreditoOrcamentarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CreditoOrcamentarioService {

    CreditoOrcamentarioResponse criar(CreditoOrcamentarioRequest request);

    CreditoOrcamentarioResponse obterPorId(UUID id);

    Page<CreditoOrcamentarioResponse> listar(Pageable pageable);

    CreditoOrcamentarioResponse atualizar(UUID id, CreditoOrcamentarioRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

