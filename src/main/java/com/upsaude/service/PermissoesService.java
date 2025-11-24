package com.upsaude.service;

import com.upsaude.api.request.PermissoesRequest;
import com.upsaude.api.response.PermissoesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Permissoes.
 *
 * @author UPSaúde
 */
public interface PermissoesService {

    PermissoesResponse criar(PermissoesRequest request);

    PermissoesResponse obterPorId(UUID id);

    Page<PermissoesResponse> listar(Pageable pageable);

    PermissoesResponse atualizar(UUID id, PermissoesRequest request);

    void excluir(UUID id);
}
