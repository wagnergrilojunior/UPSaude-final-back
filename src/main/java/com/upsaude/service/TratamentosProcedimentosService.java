package com.upsaude.service;

import com.upsaude.api.request.TratamentosProcedimentosRequest;
import com.upsaude.api.response.TratamentosProcedimentosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a TratamentosProcedimentos.
 *
 * @author UPSaúde
 */
public interface TratamentosProcedimentosService {

    TratamentosProcedimentosResponse criar(TratamentosProcedimentosRequest request);

    TratamentosProcedimentosResponse obterPorId(UUID id);

    Page<TratamentosProcedimentosResponse> listar(Pageable pageable);

    TratamentosProcedimentosResponse atualizar(UUID id, TratamentosProcedimentosRequest request);

    void excluir(UUID id);
}
