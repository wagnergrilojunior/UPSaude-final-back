package com.upsaude.service;

import com.upsaude.api.request.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.api.response.HistoricoHabilitacaoProfissionalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HistoricoHabilitacaoProfissionalService {

    HistoricoHabilitacaoProfissionalResponse criar(HistoricoHabilitacaoProfissionalRequest request);

    HistoricoHabilitacaoProfissionalResponse obterPorId(UUID id);

    Page<HistoricoHabilitacaoProfissionalResponse> listar(Pageable pageable);

    HistoricoHabilitacaoProfissionalResponse atualizar(UUID id, HistoricoHabilitacaoProfissionalRequest request);

    void excluir(UUID id);
}
