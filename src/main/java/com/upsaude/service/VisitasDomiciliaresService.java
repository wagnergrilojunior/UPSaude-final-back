package com.upsaude.service;

import com.upsaude.api.request.VisitasDomiciliaresRequest;
import com.upsaude.api.response.VisitasDomiciliaresResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VisitasDomiciliaresService {

    VisitasDomiciliaresResponse criar(VisitasDomiciliaresRequest request);

    VisitasDomiciliaresResponse obterPorId(UUID id);

    Page<VisitasDomiciliaresResponse> listar(Pageable pageable);

    VisitasDomiciliaresResponse atualizar(UUID id, VisitasDomiciliaresRequest request);

    void excluir(UUID id);
}
