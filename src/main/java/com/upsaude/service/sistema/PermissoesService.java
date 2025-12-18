package com.upsaude.service.sistema;

import com.upsaude.api.request.sistema.PermissoesRequest;
import com.upsaude.api.response.sistema.PermissoesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PermissoesService {

    PermissoesResponse criar(PermissoesRequest request);

    PermissoesResponse obterPorId(UUID id);

    Page<PermissoesResponse> listar(Pageable pageable);

    Page<PermissoesResponse> listar(Pageable pageable, UUID estabelecimentoId, String modulo, String nome);

    PermissoesResponse atualizar(UUID id, PermissoesRequest request);

    void excluir(UUID id);
}
