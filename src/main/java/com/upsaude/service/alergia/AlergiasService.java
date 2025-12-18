package com.upsaude.service.alergia;

import com.upsaude.api.request.alergia.AlergiasRequest;
import com.upsaude.api.response.alergia.AlergiasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AlergiasService {

    AlergiasResponse criar(AlergiasRequest request);

    AlergiasResponse obterPorId(UUID id);

    Page<AlergiasResponse> listar(Pageable pageable);

    AlergiasResponse atualizar(UUID id, AlergiasRequest request);

    void excluir(UUID id);
}
