package com.upsaude.service.api.support.farmacia;

import com.upsaude.api.response.farmacia.DispensacaoResponse;
import com.upsaude.entity.farmacia.Dispensacao;
import com.upsaude.mapper.farmacia.DispensacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispensacaoResponseBuilder {

    private final DispensacaoMapper dispensacaoMapper;

    public DispensacaoResponse build(Dispensacao dispensacao) {
        return dispensacaoMapper.toResponseCompleto(dispensacao);
    }
}

