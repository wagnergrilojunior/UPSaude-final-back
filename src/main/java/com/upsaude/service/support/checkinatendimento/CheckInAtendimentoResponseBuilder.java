package com.upsaude.service.support.checkinatendimento;

import com.upsaude.api.response.atendimento.CheckInAtendimentoResponse;
import com.upsaude.entity.atendimento.CheckInAtendimento;
import com.upsaude.mapper.CheckInAtendimentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckInAtendimentoResponseBuilder {

    private final CheckInAtendimentoMapper mapper;

    public CheckInAtendimentoResponse build(CheckInAtendimento entity) {
        return mapper.toResponse(entity);
    }
}
