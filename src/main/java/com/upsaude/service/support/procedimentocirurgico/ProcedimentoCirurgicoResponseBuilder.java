package com.upsaude.service.support.procedimentocirurgico;

import com.upsaude.api.response.ProcedimentoCirurgicoResponse;
import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.mapper.ProcedimentoCirurgicoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcedimentoCirurgicoResponseBuilder {

    private final ProcedimentoCirurgicoMapper mapper;

    public ProcedimentoCirurgicoResponse build(ProcedimentoCirurgico entity) {
        if (entity != null) {
            if (entity.getCirurgia() != null) Hibernate.initialize(entity.getCirurgia());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

