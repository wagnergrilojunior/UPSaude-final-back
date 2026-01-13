package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.ConciliacaoItemRequest;
import com.upsaude.api.response.financeiro.ConciliacaoItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConciliacaoItemService {

    ConciliacaoItemResponse criar(ConciliacaoItemRequest request);

    ConciliacaoItemResponse obterPorId(UUID id);

    Page<ConciliacaoItemResponse> listar(Pageable pageable);

    ConciliacaoItemResponse atualizar(UUID id, ConciliacaoItemRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

