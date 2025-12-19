package com.upsaude.service.support.cirurgia;

import com.upsaude.api.response.clinica.cirurgia.CirurgiaResponse;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.mapper.clinica.cirurgia.CirurgiaMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CirurgiaResponseBuilder {

    private final CirurgiaMapper mapper;

    public CirurgiaResponse build(Cirurgia entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getCirurgiaoPrincipal() != null) Hibernate.initialize(entity.getCirurgiaoPrincipal());
            if (entity.getMedicoCirurgiao() != null) Hibernate.initialize(entity.getMedicoCirurgiao());
            if (entity.getConvenio() != null) Hibernate.initialize(entity.getConvenio());
            if (entity.getEquipe() != null) Hibernate.initialize(entity.getEquipe());
        }
        return mapper.toResponse(entity);
    }
}

