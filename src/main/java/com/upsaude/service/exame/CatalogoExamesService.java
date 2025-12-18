package com.upsaude.service.exame;

import com.upsaude.api.request.exame.CatalogoExamesRequest;
import com.upsaude.api.response.exame.CatalogoExamesResponse;
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
