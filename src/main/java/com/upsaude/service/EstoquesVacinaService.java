package com.upsaude.service;

import com.upsaude.api.request.EstoquesVacinaRequest;
import com.upsaude.api.response.EstoquesVacinaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EstoquesVacinaService {

    EstoquesVacinaResponse criar(EstoquesVacinaRequest request);

    EstoquesVacinaResponse obterPorId(UUID id);

    Page<EstoquesVacinaResponse> listar(Pageable pageable);

    EstoquesVacinaResponse atualizar(UUID id, EstoquesVacinaRequest request);

    void excluir(UUID id);
}
