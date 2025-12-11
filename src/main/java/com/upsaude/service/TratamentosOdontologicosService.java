package com.upsaude.service;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.api.response.TratamentosOdontologicosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TratamentosOdontologicosService {

    TratamentosOdontologicosResponse criar(TratamentosOdontologicosRequest request);

    TratamentosOdontologicosResponse obterPorId(UUID id);

    Page<TratamentosOdontologicosResponse> listar(Pageable pageable);

    TratamentosOdontologicosResponse atualizar(UUID id, TratamentosOdontologicosRequest request);

    void excluir(UUID id);
}
