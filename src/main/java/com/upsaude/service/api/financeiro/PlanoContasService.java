package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.PlanoContasRequest;
import com.upsaude.api.response.financeiro.PlanoContasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PlanoContasService {

    PlanoContasResponse criar(PlanoContasRequest request);

    PlanoContasResponse obterPorId(UUID id);

    Page<PlanoContasResponse> listar(Pageable pageable);

    PlanoContasResponse atualizar(UUID id, PlanoContasRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

