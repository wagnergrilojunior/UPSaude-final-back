package com.upsaude.service.api.support.agendamento;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.mapper.agendamento.AgendamentoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoResponseBuilder {

    private final AgendamentoMapper mapper;

    public AgendamentoResponse build(Agendamento entity) {
        return mapper.toResponse(entity);
    }
}

