package com.upsaude.service.api.support.financeiro.orcamento;

import com.upsaude.api.response.financeiro.OrcamentoCompetenciaResponse;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.mapper.financeiro.OrcamentoCompetenciaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrcamentoCompetenciaResponseBuilder {

    private final OrcamentoCompetenciaMapper mapper;

    public OrcamentoCompetenciaResponse build(OrcamentoCompetencia entity) {
        return mapper.toResponse(entity);
    }
}

