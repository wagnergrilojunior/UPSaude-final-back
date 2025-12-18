package com.upsaude.service.support.controleponto;

import com.upsaude.api.response.equipe.ControlePontoResponse;
import com.upsaude.entity.equipe.ControlePonto;
import com.upsaude.mapper.ControlePontoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ControlePontoResponseBuilder {

    private final ControlePontoMapper mapper;

    public ControlePontoResponse build(ControlePonto entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getProfissional());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
