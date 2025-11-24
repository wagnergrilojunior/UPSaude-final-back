package com.upsaude.service;

import com.upsaude.api.request.AlergiasRequest;
import com.upsaude.api.response.AlergiasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Alergias.
 *
 * @author UPSaúde
 */
public interface AlergiasService {

    AlergiasResponse criar(AlergiasRequest request);

    AlergiasResponse obterPorId(UUID id);

    Page<AlergiasResponse> listar(Pageable pageable);

    AlergiasResponse atualizar(UUID id, AlergiasRequest request);

    void excluir(UUID id);
}
