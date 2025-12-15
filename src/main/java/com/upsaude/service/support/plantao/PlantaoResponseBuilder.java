package com.upsaude.service.support.plantao;

import com.upsaude.api.response.PlantaoResponse;
import com.upsaude.entity.Plantao;
import com.upsaude.mapper.PlantaoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantaoResponseBuilder {

    private final PlantaoMapper mapper;

    public PlantaoResponse build(Plantao entity) {
        if (entity != null) {
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getMedico() != null) Hibernate.initialize(entity.getMedico());
        }
        return mapper.toResponse(entity);
    }
}

