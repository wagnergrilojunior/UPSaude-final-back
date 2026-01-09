package com.upsaude.service.api.convenio;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.api.response.convenio.ConvenioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConvenioService {

    ConvenioResponse criar(ConvenioRequest request);

    ConvenioResponse obterPorId(UUID id);

    Page<ConvenioResponse> listar(Pageable pageable);

    ConvenioResponse atualizar(UUID id, ConvenioRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}
