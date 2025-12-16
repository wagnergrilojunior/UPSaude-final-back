package com.upsaude.service;

import com.upsaude.api.request.DeficienciasRequest;
import com.upsaude.api.response.DeficienciasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DeficienciasService {

    DeficienciasResponse criar(DeficienciasRequest request);

    DeficienciasResponse obterPorId(UUID id);

    Page<DeficienciasResponse> listar(Pageable pageable);

    DeficienciasResponse atualizar(UUID id, DeficienciasRequest request);

    void excluir(UUID id);
}
