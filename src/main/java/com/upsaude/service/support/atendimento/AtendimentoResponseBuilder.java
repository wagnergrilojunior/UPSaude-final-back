package com.upsaude.service.support.atendimento;

import com.upsaude.api.response.atendimento.AtendimentoResponse;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.mapper.AtendimentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtendimentoResponseBuilder {

    private final AtendimentoMapper mapper;

    public AtendimentoResponse build(Atendimento entity) {
        return mapper.toResponse(entity);
    }
}
