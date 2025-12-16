package com.upsaude.service;

import com.upsaude.api.request.HistoricoClinicoRequest;
import com.upsaude.api.response.HistoricoClinicoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HistoricoClinicoService {

    HistoricoClinicoResponse criar(HistoricoClinicoRequest request);

    HistoricoClinicoResponse obterPorId(UUID id);

    Page<HistoricoClinicoResponse> listar(Pageable pageable);

    HistoricoClinicoResponse atualizar(UUID id, HistoricoClinicoRequest request);

    void excluir(UUID id);
}
