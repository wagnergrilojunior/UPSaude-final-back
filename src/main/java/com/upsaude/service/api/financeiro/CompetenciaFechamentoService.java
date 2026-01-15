package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFechamentoRequest;
import com.upsaude.api.response.financeiro.CompetenciaFechamentoResponse;

import java.util.UUID;

public interface CompetenciaFechamentoService {

    CompetenciaFechamentoResponse fecharCompetencia(
            UUID competenciaId,
            UUID tenantId,
            CompetenciaFechamentoRequest request);
}
