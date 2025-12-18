package com.upsaude.service.estoque;

import com.upsaude.api.request.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.estoque.MovimentacoesEstoqueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MovimentacoesEstoqueService {

    MovimentacoesEstoqueResponse criar(MovimentacoesEstoqueRequest request);

    MovimentacoesEstoqueResponse obterPorId(UUID id);

    Page<MovimentacoesEstoqueResponse> listar(Pageable pageable);

    MovimentacoesEstoqueResponse atualizar(UUID id, MovimentacoesEstoqueRequest request);

    void excluir(UUID id);
}
