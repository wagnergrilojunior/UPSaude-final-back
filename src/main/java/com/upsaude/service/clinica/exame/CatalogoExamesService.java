package com.upsaude.service.clinica.exame;

import com.upsaude.api.request.clinica.exame.CatalogoExamesRequest;
import com.upsaude.api.response.clinica.exame.CatalogoExamesResponse;
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
