package com.upsaude.service.api.support.atendimento;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.atendimento.AtendimentoResponse;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtendimentoResponseBuilder {

    private final AtendimentoMapper mapper;

    public AtendimentoResponse build(Atendimento entity) {
        return mapper.toResponse(entity);
    }
}
