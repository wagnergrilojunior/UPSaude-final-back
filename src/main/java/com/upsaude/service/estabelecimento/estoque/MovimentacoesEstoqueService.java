package com.upsaude.service.estabelecimento.estoque;

import com.upsaude.api.request.estabelecimento.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.estabelecimento.estoque.MovimentacoesEstoqueResponse;
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
