package com.upsaude.service.support.fabricantesmedicamento;

import com.upsaude.api.response.FabricantesMedicamentoResponse;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.mapper.FabricantesMedicamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FabricantesMedicamentoResponseBuilder {

    private final FabricantesMedicamentoMapper mapper;

    public FabricantesMedicamentoResponse build(FabricantesMedicamento entity) {
        return mapper.toResponse(entity);
    }
}

