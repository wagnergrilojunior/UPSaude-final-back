package com.upsaude.service.support.checkinatendimento;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.atendimento.CheckInAtendimentoResponse;
import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.mapper.clinica.atendimento.CheckInAtendimentoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckInAtendimentoResponseBuilder {

    private final CheckInAtendimentoMapper mapper;

    public CheckInAtendimentoResponse build(CheckInAtendimento entity) {
        return mapper.toResponse(entity);
    }
}
