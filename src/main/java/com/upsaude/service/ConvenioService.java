package com.upsaude.service;

import com.upsaude.api.request.ConvenioRequest;
import com.upsaude.api.response.ConvenioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Convenio.
 *
 * @author UPSaúde
 */
public interface ConvenioService {

    ConvenioResponse criar(ConvenioRequest request);

    ConvenioResponse obterPorId(UUID id);

    Page<ConvenioResponse> listar(Pageable pageable);

    ConvenioResponse atualizar(UUID id, ConvenioRequest request);

    void excluir(UUID id);
}
