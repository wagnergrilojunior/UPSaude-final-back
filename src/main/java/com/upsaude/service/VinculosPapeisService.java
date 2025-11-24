package com.upsaude.service;

import com.upsaude.api.request.VinculosPapeisRequest;
import com.upsaude.api.response.VinculosPapeisResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a VinculosPapeis.
 *
 * @author UPSaúde
 */
public interface VinculosPapeisService {

    VinculosPapeisResponse criar(VinculosPapeisRequest request);

    VinculosPapeisResponse obterPorId(UUID id);

    Page<VinculosPapeisResponse> listar(Pageable pageable);

    VinculosPapeisResponse atualizar(UUID id, VinculosPapeisRequest request);

    void excluir(UUID id);
}
