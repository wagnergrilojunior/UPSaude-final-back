package com.upsaude.service;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.api.response.CatalogoExamesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CatalogoExamesService {

    CatalogoExamesResponse criar(CatalogoExamesRequest request);

    CatalogoExamesResponse obterPorId(UUID id);

    Page<CatalogoExamesResponse> listar(Pageable pageable);

    CatalogoExamesResponse atualizar(UUID id, CatalogoExamesRequest request);

    void excluir(UUID id);
}
