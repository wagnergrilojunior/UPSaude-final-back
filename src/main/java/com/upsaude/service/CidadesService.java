package com.upsaude.service;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Cidades.
 *
 * @author UPSaúde
 */
public interface CidadesService {

    CidadesResponse criar(CidadesRequest request);

    CidadesResponse obterPorId(UUID id);

    Page<CidadesResponse> listar(Pageable pageable);

    CidadesResponse atualizar(UUID id, CidadesRequest request);

    void excluir(UUID id);
}
