package com.upsaude.service.support.acaopromocaoprevencao;

import com.upsaude.api.response.educacao.AcaoPromocaoPrevencaoResponse;
import com.upsaude.entity.educacao.AcaoPromocaoPrevencao;
import com.upsaude.mapper.AcaoPromocaoPrevencaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcaoPromocaoPrevencaoResponseBuilder {

    private final AcaoPromocaoPrevencaoMapper mapper;

    public AcaoPromocaoPrevencaoResponse build(AcaoPromocaoPrevencao entity) {
        return mapper.toResponse(entity);
    }
}
