package com.upsaude.service.support.falta;

import com.upsaude.api.response.equipe.FaltaResponse;
import com.upsaude.entity.equipe.Falta;
import com.upsaude.mapper.FaltaMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaltaResponseBuilder {

    private final FaltaMapper mapper;

    public FaltaResponse build(Falta entity) {
        if (entity != null) {
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getMedico() != null) Hibernate.initialize(entity.getMedico());
        }
        return mapper.toResponse(entity);
    }
}

