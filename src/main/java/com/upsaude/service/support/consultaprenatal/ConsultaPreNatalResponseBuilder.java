package com.upsaude.service.support.consultaprenatal;

import com.upsaude.api.response.atendimento.ConsultaPreNatalResponse;
import com.upsaude.entity.atendimento.ConsultaPreNatal;
import com.upsaude.mapper.ConsultaPreNatalMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

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

