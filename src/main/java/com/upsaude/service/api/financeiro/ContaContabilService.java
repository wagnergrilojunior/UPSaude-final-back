package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.ContaContabilRequest;
import com.upsaude.api.response.financeiro.ContaContabilResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ContaContabilService {

    ContaContabilResponse criar(ContaContabilRequest request);

    ContaContabilResponse obterPorId(UUID id);

    Page<ContaContabilResponse> listar(Pageable pageable);

    ContaContabilResponse atualizar(UUID id, ContaContabilRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

