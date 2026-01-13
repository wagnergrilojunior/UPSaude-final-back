package com.upsaude.service.api.support.financeiro.guia;

import com.upsaude.api.response.financeiro.GuiaAtendimentoAmbulatorialResponse;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.mapper.financeiro.GuiaAtendimentoAmbulatorialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuiaAtendimentoAmbulatorialResponseBuilder {

    private final GuiaAtendimentoAmbulatorialMapper mapper;

    public GuiaAtendimentoAmbulatorialResponse build(GuiaAtendimentoAmbulatorial entity) {
        return mapper.toResponse(entity);
    }
}

