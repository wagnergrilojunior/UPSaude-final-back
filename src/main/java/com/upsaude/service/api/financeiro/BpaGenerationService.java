package com.upsaude.service.api.financeiro;

import java.util.List;
import java.util.UUID;

public interface BpaGenerationService {

    String gerarBpa(UUID competenciaId, UUID tenantId);

    List<com.upsaude.api.response.financeiro.BpaConsolidadoDto> consolidarDadosCompetencia(UUID competenciaId, UUID tenantId);
}
