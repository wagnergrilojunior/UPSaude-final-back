package com.upsaude.service.profissional;

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.api.response.profissional.HistoricoHabilitacaoProfissionalResponse;
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
