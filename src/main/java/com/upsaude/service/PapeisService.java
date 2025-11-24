package com.upsaude.service;

import com.upsaude.api.request.PapeisRequest;
import com.upsaude.api.response.PapeisResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Papeis.
 *
 * @author UPSaúde
 */
public interface PapeisService {

    PapeisResponse criar(PapeisRequest request);

    PapeisResponse obterPorId(UUID id);

    Page<PapeisResponse> listar(Pageable pageable);

    PapeisResponse atualizar(UUID id, PapeisRequest request);

    void excluir(UUID id);
}
