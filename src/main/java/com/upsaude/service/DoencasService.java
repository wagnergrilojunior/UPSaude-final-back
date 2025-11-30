package com.upsaude.service;

import com.upsaude.api.request.DoencasRequest;
import com.upsaude.api.response.DoencasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Doencas.
 *
 * @author UPSaúde
 */
public interface DoencasService {

    DoencasResponse criar(DoencasRequest request);

    DoencasResponse obterPorId(UUID id);

    Page<DoencasResponse> listar(Pageable pageable);

    Page<DoencasResponse> listarPorNome(String nome, Pageable pageable);

    Page<DoencasResponse> listarPorCodigoCid(String codigoCid, Pageable pageable);

    DoencasResponse atualizar(UUID id, DoencasRequest request);

    void excluir(UUID id);
}

