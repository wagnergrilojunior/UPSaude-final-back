package com.upsaude.service.support.controleponto;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.equipe.ControlePontoResponse;
import com.upsaude.entity.profissional.equipe.ControlePonto;
import com.upsaude.mapper.profissional.equipe.ControlePontoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ControlePontoResponseBuilder {

    private final ControlePontoMapper mapper;

    public ControlePontoResponse build(ControlePonto entity) {
        if (entity != null) {
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getMedico() != null) Hibernate.initialize(entity.getMedico());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
