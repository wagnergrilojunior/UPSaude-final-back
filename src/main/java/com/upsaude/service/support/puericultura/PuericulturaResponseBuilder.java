package com.upsaude.service.support.puericultura;

import com.upsaude.api.response.PuericulturaResponse;
import com.upsaude.entity.Puericultura;
import com.upsaude.mapper.PuericulturaMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PuericulturaResponseBuilder {

    private final PuericulturaMapper mapper;

    public PuericulturaResponse build(Puericultura entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getProfissionalResponsavel());
            Hibernate.initialize(entity.getEquipeSaude());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

