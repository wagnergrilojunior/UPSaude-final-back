package com.upsaude.service;

import com.upsaude.api.request.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.MovimentacoesEstoqueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a MovimentacoesEstoque.
 *
 * @author UPSaúde
 */
public interface MovimentacoesEstoqueService {

    MovimentacoesEstoqueResponse criar(MovimentacoesEstoqueRequest request);

    MovimentacoesEstoqueResponse obterPorId(UUID id);

    Page<MovimentacoesEstoqueResponse> listar(Pageable pageable);

    MovimentacoesEstoqueResponse atualizar(UUID id, MovimentacoesEstoqueRequest request);

    void excluir(UUID id);
}
