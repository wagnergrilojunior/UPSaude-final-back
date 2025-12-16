package com.upsaude.service.support.agendamento;

import com.upsaude.api.response.AgendamentoResponse;
import com.upsaude.entity.Agendamento;
import com.upsaude.mapper.AgendamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendamentoResponseBuilder {

    private final AgendamentoMapper mapper;

    public AgendamentoResponse build(Agendamento entity) {
        return mapper.toResponse(entity);
    }
}
