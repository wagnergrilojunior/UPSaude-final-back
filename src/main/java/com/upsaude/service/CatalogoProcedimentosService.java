package com.upsaude.service;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a CatalogoProcedimentos.
 *
 * @author UPSaúde
 */
public interface CatalogoProcedimentosService {

    CatalogoProcedimentosResponse criar(CatalogoProcedimentosRequest request);

    CatalogoProcedimentosResponse obterPorId(UUID id);

    Page<CatalogoProcedimentosResponse> listar(Pageable pageable);

    CatalogoProcedimentosResponse atualizar(UUID id, CatalogoProcedimentosRequest request);

    void excluir(UUID id);
}

