package com.upsaude.service.support.consultaprenatal;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.atendimento.ConsultaPreNatalResponse;
import com.upsaude.entity.clinica.atendimento.ConsultaPreNatal;
import com.upsaude.mapper.clinica.atendimento.ConsultaPreNatalMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaPreNatalResponseBuilder {

    private final ConsultaPreNatalMapper mapper;

    public ConsultaPreNatalResponse build(ConsultaPreNatal entity) {
        if (entity != null) {
            if (entity.getPreNatal() != null) Hibernate.initialize(entity.getPreNatal());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

