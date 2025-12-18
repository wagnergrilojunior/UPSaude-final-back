package com.upsaude.service.support.fabricantesmedicamento;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.referencia.fabricante.FabricantesMedicamentoResponse;
import com.upsaude.entity.referencia.fabricante.FabricantesMedicamento;
import com.upsaude.mapper.referencia.fabricante.FabricantesMedicamentoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FabricantesMedicamentoResponseBuilder {

    private final FabricantesMedicamentoMapper mapper;

    public FabricantesMedicamentoResponse build(FabricantesMedicamento entity) {
        return mapper.toResponse(entity);
    }
}

