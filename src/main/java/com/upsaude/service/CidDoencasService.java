package com.upsaude.service;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.api.response.CidDoencasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a CidDoencas.
 *
 * @author UPSaúde
 */
public interface CidDoencasService {

    CidDoencasResponse criar(CidDoencasRequest request);

    CidDoencasResponse obterPorId(UUID id);

    Page<CidDoencasResponse> listar(Pageable pageable);

    CidDoencasResponse atualizar(UUID id, CidDoencasRequest request);

    void excluir(UUID id);
}
