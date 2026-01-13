package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.OrcamentoCompetenciaRequest;
import com.upsaude.api.response.financeiro.OrcamentoCompetenciaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrcamentoCompetenciaService {

    OrcamentoCompetenciaResponse criar(OrcamentoCompetenciaRequest request);

    OrcamentoCompetenciaResponse obterPorId(UUID id);

    Page<OrcamentoCompetenciaResponse> listar(Pageable pageable);

    OrcamentoCompetenciaResponse atualizar(UUID id, OrcamentoCompetenciaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

