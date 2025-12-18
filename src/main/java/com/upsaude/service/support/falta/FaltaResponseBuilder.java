package com.upsaude.service.support.falta;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.equipe.FaltaResponse;
import com.upsaude.entity.profissional.equipe.Falta;
import com.upsaude.mapper.profissional.equipe.FaltaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaltaResponseBuilder {

    private final FaltaMapper mapper;

    public FaltaResponse build(Falta entity) {
        if (entity != null) {
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getMedico() != null) Hibernate.initialize(entity.getMedico());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
