package com.upsaude.service.api.farmacia;

import com.upsaude.api.request.farmacia.FarmaciaRequest;
import com.upsaude.api.response.farmacia.FarmaciaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FarmaciaService {

    FarmaciaResponse criar(FarmaciaRequest request);

    FarmaciaResponse obterPorId(UUID id);

    Page<FarmaciaResponse> listar(Pageable pageable);

    FarmaciaResponse atualizar(UUID id, FarmaciaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

