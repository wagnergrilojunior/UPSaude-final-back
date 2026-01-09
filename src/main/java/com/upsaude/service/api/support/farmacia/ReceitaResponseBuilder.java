package com.upsaude.service.api.support.farmacia;

import com.upsaude.api.response.farmacia.ReceitaResponse;
import com.upsaude.entity.farmacia.Receita;
import com.upsaude.mapper.farmacia.ReceitaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceitaResponseBuilder {

    private final ReceitaMapper receitaMapper;

    public ReceitaResponse build(Receita receita) {
        return receitaMapper.toResponseCompleto(receita);
    }
}
